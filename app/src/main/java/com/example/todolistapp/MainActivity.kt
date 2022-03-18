package com.example.todolistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    lateinit var adapter: TaskItemAdapter
    var listOfTasks = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Let's detect when the user clicks on the add button
        findViewById<Button>(R.id.button).setOnClickListener{
            //code in here is going ot be executed when user clicks on button
            Log.i("Stephane", "User clicked on button")
        }

        //define SingleClickListener
        val SingleClickListiner = object: TaskItemAdapter.OnSingleClickListener{
            override fun onSingleClick(position: Int) {
                //get text from list
                val task = listOfTasks.get(position)
                // put in add_task field to edit it
                findViewById<EditText>(R.id.add_task_field).setText(task)

                findViewById<Button>(R.id.Update_Btn).setOnClickListener {
                    val updated_task = findViewById<EditText>(R.id.add_task_field).text.toString()
                    listOfTasks.set(position, updated_task)
                    adapter.notifyDataSetChanged()
                    findViewById<EditText>(R.id.add_task_field).setText("")
                    saveItems()
                }
            }
        }

        //define LongClickListener
        val LongClickListener = object: TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                //remove item from list
                listOfTasks.removeAt(position)
                //notify adapter that our data has changed
                adapter.notifyDataSetChanged()
                saveItems()
            }
        }

        loadItems()

        ///look up recycler view in the XML layout file
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = TaskItemAdapter(listOfTasks, LongClickListener,SingleClickListiner)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputTextField = findViewById<EditText>(R.id.add_task_field)
        //set up the button and input field, so that the user can enter a task
        findViewById<Button>(R.id.button).setOnClickListener{
            //1. grab the text the user has inputted into @id/add_task_fieldW
            val userInputTask = findViewById<EditText>(R.id.add_task_field).text.toString()
            //2. Add the string to our list of tasks: listOfTasks
            listOfTasks.add(userInputTask)
            //Notify the adapter that our dataset has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)
            //3. Reset text field
            inputTextField.setText("")
            saveItems()
        }


        findViewById<RecyclerView>(R.id.recyclerView).setOnClickListener{

        }
    }

    //save the data that user has inputted
    //save data by writing and reading from a file

    //Get the file we need
    fun getDataFile(): File {

        //EVery line is going to represent atask in our list of tasks
        return File(filesDir, "TodoItems.txt")
    }

    //load the items by reading every line in the data file
    fun loadItems(){
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }

    //create method that saves our items
    fun saveItems() {
      try {
          FileUtils.writeLines(getDataFile(), listOfTasks)
      } catch (ioException: IOException){
          ioException.printStackTrace()
      }
    }

}
package com.example.todolistapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskItemAdapter(val listOfItems: List<String>,
                      val LongClickListener: OnLongClickListener,
                      val SingleClickListener: OnSingleClickListener): RecyclerView.Adapter<TaskItemAdapter.ViewHolder>(){
    // functions as a bridge between recycler view and information
    //will determine how to display information in the recycler view


    //interface for removing items
    interface OnLongClickListener {
        fun onItemLongClicked(position: Int)
    }

    //editing items
    interface OnSingleClickListener {
        fun onSingleClick(position: Int)
    }



    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //get task from listof items
        val item = listOfItems.get(position)
        holder.textview.text = item
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //store references to elements in our layout
        val textview: TextView

        init {
           textview = itemView.findViewById(android.R.id.text1)

            itemView.setOnLongClickListener {
                LongClickListener.onItemLongClicked(adapterPosition)
                true
            }

            itemView.setOnClickListener {
                SingleClickListener.onSingleClick(adapterPosition)
                true
            }
        }

    }
}
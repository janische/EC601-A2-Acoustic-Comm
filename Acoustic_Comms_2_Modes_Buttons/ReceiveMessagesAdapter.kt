package com.example.lowcostacousticcomm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReceiveMessagesAdapter(private val messages: ArrayList<String>) :
    RecyclerView.Adapter<ReceiveMessagesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ReceiveMessagesAdapter.MyViewHolder {
        // create a new view
        val message = LayoutInflater.from(parent.context)
            .inflate(R.layout.receive_message_layout, parent, false) as TextView
        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(message)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.msgText.text = messages[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() : Int {
        return messages.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val msgText = view.findViewById<TextView>(R.id.msg) as TextView
    }
}
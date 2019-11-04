package com.example.lowcostacousticcomm

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class MessageButtonsAdapter(private val buttons: ArrayList<String>) :
    RecyclerView.Adapter<MessageButtonsAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    class MyViewHolder(val button: Button) : RecyclerView.ViewHolder(button)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MessageButtonsAdapter.MyViewHolder {
        // create a new view
        val button = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_button_layout, parent, false) as Button
        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(button)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.button.text = buttons[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = buttons.size
}
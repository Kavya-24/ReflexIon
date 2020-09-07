package com.example.reflexion.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reflexion.R
import com.example.reflexion.models.Task

class TasksAdapter(val itemClick: OnItemClickListener) :
    RecyclerView.Adapter<TasksAdapter.MyViewHolder>() {


    //Initialize an empty list of the dataclass T
    var lst: List<Task> = listOf()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val DESC = itemView.findViewById<TextView>(R.id.tv_description)
        val STARS = itemView.findViewById<TextView>(R.id.tv_stars)
        val DATE = itemView.findViewById<TextView>(R.id.tv_date_item)

        //Bind a single item
        fun bindPost(_listItem: Task, itemClick: OnItemClickListener) {
            with(_listItem) {

                DESC.text = _listItem.description
                STARS.text = _listItem.numStars.toString()
                DATE.text = _listItem.date.toString()

                itemView.setOnClickListener {
                    itemClick.clickThisItem(_listItem)
                }


            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_task, parent, false)
        return TasksAdapter.MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return lst.size

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bindPost(lst[position], itemClick)

    }

}


interface OnItemClickListener {
    fun clickThisItem(_listItem: Task)
}

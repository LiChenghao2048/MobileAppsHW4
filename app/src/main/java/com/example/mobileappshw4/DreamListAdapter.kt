package com.example.mobileappshw4

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class DreamListAdapter : ListAdapter<Dream, DreamListAdapter.DreamViewHolder>(DreamComparator()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DreamViewHolder {
        return DreamViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DreamViewHolder, position: Int) {
        val current = getItem(position)
        holder.bindText(current.title, holder.dreamTitleTextView)
        holder.bindText(current.id.toString(), holder.dreamIdTextView)

        holder.constraintLayout.setOnClickListener {
            val intent = Intent(it.context, DreamActivity::class.java)
            intent.putExtra("id", holder.dreamIdTextView.text.toString())
            it.context.startActivity(intent)
        }
    }

    class DreamViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val dreamIdTextView: TextView = itemView.findViewById(R.id.textView_id)
        val dreamTitleTextView: TextView = itemView.findViewById(R.id.textView_title)
        val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.constraintLayout)

        // write a helper function that takes a string and a text view
        // assign the text to the text view

        fun bindText (text: String?, textView: TextView) {
            textView.text = text
        }

        companion object{
            fun create (parent: ViewGroup) : DreamViewHolder{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dream, parent, false)
                return DreamViewHolder(view)
            }
        }
    }

    class DreamComparator : DiffUtil.ItemCallback<Dream>(){
        override fun areContentsTheSame(oldItem: Dream, newItem: Dream): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: Dream, newItem: Dream): Boolean {
            return oldItem === newItem
        }
    }

}
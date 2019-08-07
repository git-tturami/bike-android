package com.gitturami.bike.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gitturami.bike.R
import com.gitturami.bike.data.recyclerItem
import kotlinx.android.synthetic.main.list_item.view.*

class ViewHolder(val context: Context, parent: ViewGroup?, val listenerFunc: ((Int) -> Unit)?)
    : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)) {

    val textViewTitle by lazy {
        itemView.findViewById(R.id.title) as TextView
    }

    val textViewContent by lazy {
        itemView.findViewById(R.id.content) as TextView
    }


    fun bindItems(data : recyclerItem){

        textViewTitle.text = data.title
        textViewContent.text = data.content

        itemView.setOnClickListener {
            listenerFunc?.invoke(position)
        }
    }
}
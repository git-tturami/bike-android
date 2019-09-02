package com.gitturami.bike.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gitturami.bike.R
import com.gitturami.bike.data.recyclerItem

class TitleViewHolder(val context: Context, parent: ViewGroup?, val listenerFunc: ((Int, TextView) -> Unit)?)
    : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_bottomsheet, parent, false)) {

    val titletextView by lazy {
        itemView.findViewById(R.id.bottomSheetTitle) as TextView
    }

    val subtitletextView by lazy {
        itemView.findViewById(R.id.bottomSheetSubTitle) as TextView
    }

    fun onBind(data : recyclerItem,position: Int) {
        titletextView.text = data.title
        subtitletextView.text = data.content
        itemView.setOnClickListener {
            listenerFunc?.invoke(position,subtitletextView)
        }
    }
}
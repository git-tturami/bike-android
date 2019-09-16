package com.gitturami.bike.adapter.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gitturami.bike.R
import com.gitturami.bike.data.RecyclerItem
import kotlinx.android.synthetic.main.rv_item.view.*

class RecommendViewHolder(context: Context, parent: ViewGroup?, private val clickListenerFunc: ((Int) -> Unit)?)
    : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_item, parent, false)) {

    private val titleTextView by lazy {
        itemView.recommend_itemTitle as TextView
    }

    private val subtitleTextView by lazy {
        itemView.recommend_itemSubTitle as TextView
    }

    fun onBind(data : RecyclerItem, position: Int) {
        titleTextView.text = data.title
        subtitleTextView.text = data.content

        itemView.setOnClickListener {
            clickListenerFunc?.invoke(position)
        }
    }
}
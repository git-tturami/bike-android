package com.gitturami.bike.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gitturami.bike.adapter.contact.WayPointAdapterContact
import com.gitturami.bike.adapter.viewholder.RecommendViewHolder
import com.gitturami.bike.data.RecyclerItem

class WayPointAdapter(private val context: Context) : WayPointAdapterContact.View, RecyclerView.Adapter<RecommendViewHolder>(), WayPointAdapterContact.Model {
    override var onClickFunc: ((Int) -> Unit)? = null
    private var itemList: List<RecyclerItem> = arrayListOf()

    override fun onBindViewHolder(holderRecommend: RecommendViewHolder, position: Int) {
        holderRecommend.onBind(itemList[position], position)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun clearItem() {
        itemList = arrayListOf()
    }

    override fun getItem(position: Int): RecyclerItem = itemList[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder
            = RecommendViewHolder(context, parent, onClickFunc)

    override fun notifyAdapter() {
        notifyDataSetChanged()
    }

    override fun addItems(recyclerList: List<RecyclerItem>) {
        this.itemList = recyclerList
    }

}
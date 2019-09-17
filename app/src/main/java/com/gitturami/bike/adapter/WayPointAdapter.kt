package com.gitturami.bike.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gitturami.bike.adapter.contact.WayPointAdapterContact
import com.gitturami.bike.adapter.viewholder.WayPointViewHolder
import com.gitturami.bike.data.RecyclerItem

class WayPointAdapter(private val context: Context) : WayPointAdapterContact.View, RecyclerView.Adapter<WayPointViewHolder>(), WayPointAdapterContact.Model {
    override var onClickFunc: ((Int) -> Unit)? = null
    private var itemList: MutableList<RecyclerItem> = arrayListOf()

    override fun onBindViewHolder(holderWayPoint: WayPointViewHolder, position: Int) {
        holderWayPoint.onBind(itemList[position], position)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun clearItem() {
        itemList = arrayListOf()
    }

    override fun getItem(position: Int): RecyclerItem = itemList[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WayPointViewHolder
            = WayPointViewHolder(context, parent, onClickFunc)

    override fun notifyAdapter() {
        notifyDataSetChanged()
    }

    override fun addItem(item: RecyclerItem) {
        itemList.add(item)
    }

    override fun addItems(recyclerList: MutableList<RecyclerItem>) {
        this.itemList = recyclerList
    }

    override fun getSize(): Int = itemList.size

}
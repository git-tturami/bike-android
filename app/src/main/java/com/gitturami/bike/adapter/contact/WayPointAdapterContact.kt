package com.gitturami.bike.adapter.contact

import com.gitturami.bike.data.RecyclerItem

interface WayPointAdapterContact {
    interface View{
        var onClickFunc: ((Int)->Unit)?
        fun notifyAdapter()
    }
    interface Model {
        fun addItem(item: RecyclerItem)
        fun addItems(recyclerList: MutableList<RecyclerItem>)
        fun getItem(position: Int): RecyclerItem
        fun clearItem()
        fun getSize(): Int
    }
}
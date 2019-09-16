package com.gitturami.bike.adapter.contact

import com.gitturami.bike.data.RecyclerItem

interface WayPointAdapterContact {
    interface View{
        var onClickFunc: ((Int)->Unit)?
        fun notifyAdapter()
    }
    interface Model {
        fun addItems(recyclerList: List<RecyclerItem>)
        fun getItem(position: Int): RecyclerItem
        fun clearItem()
    }
}
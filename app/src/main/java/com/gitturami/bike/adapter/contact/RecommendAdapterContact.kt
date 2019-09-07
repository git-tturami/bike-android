package com.gitturami.bike.adapter.contact

import com.gitturami.bike.data.RecyclerItem

interface RecommendAdapterContact {
    interface View{
        var onClickFunc: ((Int)->Unit)?
        fun notifyAdapter()
    }
    interface Model {
        fun addItems(recyclerList: ArrayList<RecyclerItem>)
        fun getItem(position: Int): RecyclerItem
        fun clearItem()
    }
}
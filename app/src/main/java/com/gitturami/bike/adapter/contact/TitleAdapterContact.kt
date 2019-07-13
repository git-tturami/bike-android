package com.gitturami.bike.adapter.contact

import android.widget.TextView
import com.gitturami.bike.data.recyclerItem


interface TitleAdapterContact {

    interface  View{
        var onClickFunc: ((Int,TextView)->Unit)?
        fun notifyAdapter()
    }
    interface Model {
        fun addItems(recyclerList: ArrayList<recyclerItem>)
        fun getItem(position: Int) :recyclerItem
    }
}
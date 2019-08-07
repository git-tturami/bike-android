package com.gitturami.bike.adapter.contract

import com.gitturami.bike.data.recyclerItem

interface RecyclerAdapterContract{

    interface View {

        var onClickFunc : ((Int) -> Unit)?

        fun notifyAdapter()

    }

    interface Model {

        fun addItems(recyclerList: ArrayList<recyclerItem>)
    }

}
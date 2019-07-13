package com.gitturami.bike.adapter.contact

import android.widget.TextView



interface TitleAdapterContact {

    interface  View{
        var onClickFunc: ((Int,TextView)->Unit)?
        fun notifyAdapter()
    }

    interface Model
}
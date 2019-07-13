package com.gitturami.bike.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gitturami.bike.R

class TitleViewHolder(val context: Context, parent: ViewGroup?, val listenerFunc: ((Int, TextView) -> Unit)?)
    : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_bottomsheet, parent, false)) {

    val titletextView by lazy {
        itemView.findViewById(R.id.bottom_sheet_title) as TextView
    }

    val subtitletextView by lazy {
        itemView.findViewById(R.id.address) as TextView
    }
    //parameter에 상단바 '출발지' 객체를 받아 올 수 있게 추가 해야함
    fun onBind(position: Int) {

//        titletextView.text = 상단 출발지.title

        itemView.setOnClickListener {
            listenerFunc?.invoke(position,subtitletextView)
        }
    }
}
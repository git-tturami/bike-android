package com.gitturami.bike.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gitturami.bike.adapter.contact.TitleAdapterContact


class TitleAdapter(private val context: Context) : TitleAdapterContact.View, RecyclerView.Adapter<TitleViewHolder>(), TitleAdapterContact.Model {
    override var onClickFunc: ((Int,TextView) -> Unit)? = null
    private lateinit var tempList: ArrayList<recyclerItem>
    override fun onBindViewHolder(holderTitle: TitleViewHolder, position: Int) {
        //(미추가)->인자를 추가해 지도에서 받아온  title을 표시하게한다.
        holderTitle.onBind(position)

    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder
            = TitleViewHolder(context, parent, onClickFunc)




    override fun notifyAdapter() {
        notifyDataSetChanged()
    }


}
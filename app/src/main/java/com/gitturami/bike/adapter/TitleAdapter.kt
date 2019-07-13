package com.gitturami.bike.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gitturami.bike.adapter.contact.TitleAdapterContact
import com.gitturami.bike.data.recyclerItem


class TitleAdapter(private val context: Context) : TitleAdapterContact.View, RecyclerView.Adapter<TitleViewHolder>(), TitleAdapterContact.Model {
    override var onClickFunc: ((Int,TextView) -> Unit)? = null
    private lateinit var tempList: ArrayList<recyclerItem>

    override fun onBindViewHolder(holderTitle: TitleViewHolder, position: Int) {
        //(미추가)->인자를 추가해 지도에서 받아온  title을 표시하게한다.
        holderTitle.onBind(tempList[position],position)

    }

    override fun getItemCount(): Int {
        return tempList.size
    }
    override fun getItem(position: Int): recyclerItem = tempList[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder
            = TitleViewHolder(context, parent, onClickFunc)

    override fun notifyAdapter() {
        notifyDataSetChanged()
    }

    override fun addItems(recyclerList: ArrayList<recyclerItem>) {
        this.tempList = recyclerList
    }

}
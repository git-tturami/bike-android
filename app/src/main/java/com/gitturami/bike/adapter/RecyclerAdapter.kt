package com.gitturami.bike.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.gitturami.bike.R
import com.gitturami.bike.adapter.contract.RecyclerAdapterContract
import com.gitturami.bike.data.recyclerItem
import kotlinx.android.synthetic.main.list_item.view.*



class RecyclerAdapter(val context: Context) : RecyclerAdapterContract.View, RecyclerAdapterContract.Model, RecyclerView.Adapter<ViewHolder>() {

    private lateinit var tempList: ArrayList<recyclerItem>

    override var onClickFunc: ((Int) -> Unit)? = null

    override fun getItemCount(): Int {
        return tempList.size
    }

    override fun notifyAdapter() {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(context, parent, onClickFunc)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(tempList[position])
    }

    override fun addItems(recyclerList: ArrayList<recyclerItem>) {
        this.tempList = recyclerList
    }
}
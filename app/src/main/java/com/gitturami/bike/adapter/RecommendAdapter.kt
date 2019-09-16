package com.gitturami.bike.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gitturami.bike.adapter.contact.RecommendAdapterContact
import com.gitturami.bike.adapter.viewholder.RecommendViewHolder
import com.gitturami.bike.data.RecyclerItem
import com.gitturami.bike.view.main.BottomSheetDialog

class RecommendAdapter(private val context: Context) : RecommendAdapterContact.View, RecyclerView.Adapter<RecommendViewHolder>(), RecommendAdapterContact.Model {
    override var onClickFunc: ((Int) -> Unit)? = null
    private var itemList: ArrayList<RecyclerItem> = arrayListOf()

    override fun onBindViewHolder(holderRecommend: RecommendViewHolder, position: Int) {
        holderRecommend.onBind(itemList[position], position)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun clearItem() {
        itemList.clear()
    }

    override fun getItem(position: Int): RecyclerItem = itemList[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder
            = RecommendViewHolder(context, parent, onClickFunc)

    override fun notifyAdapter() {
        notifyDataSetChanged()
    }

    override fun addItems(recyclerList: ArrayList<RecyclerItem>) {
        this.itemList = recyclerList
    }

}
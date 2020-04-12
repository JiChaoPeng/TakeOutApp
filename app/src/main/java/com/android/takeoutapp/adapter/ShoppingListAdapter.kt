package com.android.takeoutapp.adapter

import android.view.ViewGroup
import com.android.frameworktool.recycler.BaseRecyclerAdapter
import com.android.frameworktool.recycler.BaseRecyclerViewHolder
import com.android.takeoutapp.holder.ShoppingListHolder
import com.android.takeoutapp.model.RoomDetailModel

class ShoppingListAdapter : BaseRecyclerAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        return ShoppingListHolder.creator(parent).apply {
            bindViewClickEvent(viewHolderConfig)
        }
    }

    init {
        typeList.apply {
            add(RoomDetailModel::class.java)
        }
    }
}
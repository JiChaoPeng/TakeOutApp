package com.android.takeoutapp.adapter

import android.view.ViewGroup
import com.android.frameworktool.recycler.BaseRecyclerAdapter
import com.android.frameworktool.recycler.BaseRecyclerViewHolder
import com.android.takeoutapp.holder.RoomListHolder
import com.android.takeoutapp.model.RoomDetailModel

class RoomListAdapter : BaseRecyclerAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        return RoomListHolder.creator(parent).apply {
            bindViewClickEvent(viewHolderConfig)
        }
    }

    init {
        typeList.apply {
            add(RoomDetailModel::class.java)
        }
    }
}
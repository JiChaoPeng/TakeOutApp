package com.android.takeoutapp.adapter

import android.view.ViewGroup
import com.android.frameworktool.recycler.BaseRecyclerAdapter
import com.android.frameworktool.recycler.BaseRecyclerViewHolder
import com.android.takeoutapp.holder.RoomDetailHeaderHolder
import com.android.takeoutapp.holder.RoomDetailHolder
import com.android.takeoutapp.model.FoodDetailModel
import com.android.takeoutapp.model.RoomDetailModel

class RoomDetailAdapter : BaseRecyclerAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        return when (viewType) {
            0 -> RoomDetailHeaderHolder.creator(parent)
            1 -> RoomDetailHolder.creator(parent)
            else -> RoomDetailHolder.creator(parent)
        }.apply {
            bindViewClickEvent(viewHolderConfig)
        }


    }

    init {
        typeList.apply {
            add(RoomDetailModel::class.java)
            add(FoodDetailModel::class.java)
        }
    }
}
package com.android.takeoutapp.adapter

import android.view.ViewGroup
import com.android.frameworktool.recycler.BaseRecyclerAdapter
import com.android.frameworktool.recycler.BaseRecyclerViewHolder
import com.android.takeoutapp.holder.ManagerFoodHolder
import com.android.takeoutapp.holder.RoomDetailHeaderHolder
import com.android.takeoutapp.holder.RoomDetailHolder
import com.android.takeoutapp.model.FoodDetailModel
import com.android.takeoutapp.model.RoomDetailModel

class ManagerFoodAdapter : BaseRecyclerAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        return ManagerFoodHolder.creator(parent).apply {
            bindViewClickEvent(viewHolderConfig)
        }
    }

    init {
        typeList.apply {
            add(FoodDetailModel::class.java)
        }
    }
}
package com.android.takeoutapp.adapter

import android.view.ViewGroup
import com.android.frameworktool.recycler.BaseRecyclerAdapter
import com.android.frameworktool.recycler.BaseRecyclerViewHolder
import com.android.takeoutapp.bean.NumBea
import com.android.takeoutapp.holder.ShoppingFooterHolder
import com.android.takeoutapp.holder.ShoppingHeaderHolder
import com.android.takeoutapp.holder.ShoppingItemHolder
import com.android.takeoutapp.model.FoodDetailModel
import com.android.takeoutapp.model.RoomDetailModel

class ShoppingItemAdapter : BaseRecyclerAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        return when (viewType) {
            0 -> {
                ShoppingHeaderHolder.creator(parent)
            }
            1 -> {
                ShoppingItemHolder.creator(parent)
            }
            else -> {
                ShoppingFooterHolder.creator(parent)
            }
        }.apply {
            bindViewClickEvent(viewHolderConfig)
        }
    }

    init {
        typeList.apply {
            add(RoomDetailModel::class.java)
            add(FoodDetailModel::class.java)
            add(NumBea::class.java)
        }
    }
}
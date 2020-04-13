package com.android.takeoutapp.adapter

import android.view.ViewGroup
import com.android.frameworktool.recycler.BaseRecyclerAdapter
import com.android.frameworktool.recycler.BaseRecyclerViewHolder
import com.android.frameworktool.util.appInflate
import com.android.takeoutapp.R
import com.android.takeoutapp.holder.OrderFormHolder
import com.android.takeoutapp.model.FoodDetailModel

class OrderFormAdapter : BaseRecyclerAdapter() {
    init {
        modelList.apply {
            add(FoodDetailModel::class.java)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewHolder {
        val creator =
            OrderFormHolder(parent.appInflate(R.layout.layout_holder_order_form, false))
        creator.bindViewClickEvent(viewHolderConfig)
        return creator
    }
}
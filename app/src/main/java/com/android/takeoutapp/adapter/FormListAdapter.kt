package com.android.takeoutapp.adapter

import android.view.ViewGroup
import com.android.frameworktool.recycler.BaseRecyclerAdapter
import com.android.frameworktool.recycler.BaseRecyclerViewHolder
import com.android.takeoutapp.bean.FormBean
import com.android.takeoutapp.holder.FormListHolder
import com.android.takeoutapp.holder.RoomListHolder
import com.android.takeoutapp.model.RoomDetailModel

class FormListAdapter : BaseRecyclerAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        return FormListHolder.creator(parent).apply {
            bindViewClickEvent(viewHolderConfig)
        }
    }

    init {
        typeList.apply {
            add(FormBean::class.java)
        }
    }
}
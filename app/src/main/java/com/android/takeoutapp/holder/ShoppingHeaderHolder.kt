package com.android.takeoutapp.holder

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import com.android.frameworktool.recycler.BaseRecyclerViewHolder
import com.android.frameworktool.util.appInflate
import com.android.frameworktool.util.onSingleClick
import com.android.takeoutapp.R
import com.android.takeoutapp.activity.RoomDetailActivity
import com.android.takeoutapp.model.RoomDetailModel
import com.android.takeoutapp.util.SqlUtil.Companion.shoppingChecked
import kotlinx.android.synthetic.main.recycler_item_shopping_item_header.view.*

class ShoppingHeaderHolder(itemView: View) : BaseRecyclerViewHolder(itemView) {

    override fun config(model: Any?) {
        super.config(model)
        if (model is RoomDetailModel) {
            itemView.checkBox.isChecked = model.isChecked
            itemView.roomName.text = "${model.roomname}   >"
            itemView.checkBox.setOnCheckedChangeListener { _, isChecked ->
                shoppingChecked(model.rId,isChecked)
            }
            itemView.roomName.onSingleClick {
                RoomDetailActivity.newInstance(itemView.context,model.rId)
            }
        }
    }

    companion object {
        fun creator(parent: ViewGroup): ShoppingHeaderHolder {
            return ShoppingHeaderHolder(parent.appInflate(R.layout.recycler_item_shopping_item_header))
        }
    }
}
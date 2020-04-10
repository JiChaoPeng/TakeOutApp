package com.android.takeoutapp.holder

import android.view.View
import android.view.ViewGroup
import com.android.frameworktool.recycler.BaseRecyclerViewHolder
import com.android.frameworktool.util.appInflate
import com.android.takeoutapp.R
import com.android.takeoutapp.model.FoodDetailModel
import kotlinx.android.synthetic.main.recycler_item_room_detail.view.*

class RoomDetailHolder(itemView: View) : BaseRecyclerViewHolder(itemView) {

    override fun config(model: Any?) {
        super.config(model)
        if (model is FoodDetailModel) {
            itemView.image.setImageResource(model.image)
            itemView.name.text = model.foodname
            itemView.price.text = "￥ ${model.price}"
        }
    }

    companion object {
        fun creator(parent: ViewGroup): RoomDetailHolder {
            return RoomDetailHolder(parent.appInflate(R.layout.recycler_item_room_detail))
        }
    }
}
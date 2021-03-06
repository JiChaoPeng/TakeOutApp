package com.android.takeoutapp.holder

import android.view.View
import android.view.ViewGroup
import com.android.frameworktool.recycler.BaseRecyclerViewHolder
import com.android.frameworktool.util.appInflate
import com.android.frameworktool.util.loadImage
import com.android.takeoutapp.R
import com.android.takeoutapp.model.FoodDetailModel
import kotlinx.android.synthetic.main.recycler_item_room_detail.view.*

class RoomDetailHolder(itemView: View) : BaseRecyclerViewHolder(itemView) {
    private var num = 0
    override fun config(model: Any?) {
        super.config(model)
        if (model is FoodDetailModel) {
            if (model.imagePath != null) {
                loadImage(itemView.image, model.imagePath!!)
            } else {
                itemView.image.setImageResource(model.image)
            }
            itemView.name.text = model.foodname
            itemView.content.text = model.explanation
            itemView.price.text = "￥ ${model.price}"
            itemView.foodNum.text = "${model.num}"
        }
    }

    companion object {
        fun creator(parent: ViewGroup): RoomDetailHolder {
            return RoomDetailHolder(parent.appInflate(R.layout.recycler_item_room_detail))
        }
    }
}
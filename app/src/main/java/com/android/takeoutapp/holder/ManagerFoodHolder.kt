package com.android.takeoutapp.holder

import android.view.View
import android.view.ViewGroup
import com.android.frameworktool.recycler.BaseRecyclerViewHolder
import com.android.frameworktool.util.appInflate
import com.android.frameworktool.util.loadImage
import com.android.takeoutapp.R
import com.android.takeoutapp.model.FoodDetailModel
import kotlinx.android.synthetic.main.recycler_item_food_manager.view.*

class ManagerFoodHolder(itemView: View) : BaseRecyclerViewHolder(itemView) {
    override fun config(model: Any?) {
        super.config(model)
        if (model is FoodDetailModel) {
            if (model.imagePath != null) {
                loadImage(itemView.image, model.imagePath!!)
            } else {
                itemView.image.setImageResource(model.image)
            }
            itemView.name.text = model.foodname
            itemView.price.text = "￥ ${model.price}"
        }
    }

    companion object {
        fun creator(parent: ViewGroup): ManagerFoodHolder {
            return ManagerFoodHolder(parent.appInflate(R.layout.recycler_item_food_manager))
        }
    }
}
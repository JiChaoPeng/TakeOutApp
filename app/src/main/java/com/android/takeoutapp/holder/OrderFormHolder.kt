package com.android.takeoutapp.holder

import android.view.View
import com.android.frameworktool.recycler.BaseRecyclerViewHolder
import com.android.frameworktool.util.loadImage
import com.android.takeoutapp.model.FoodDetailModel
import kotlinx.android.synthetic.main.layout_holder_order_form.view.*

class OrderFormHolder(itemView: View) : BaseRecyclerViewHolder(itemView) {
    override fun config(model: Any?) {
        super.config(model)
        if (model is FoodDetailModel){
            itemView.foodName.text = model.foodname
            if (model.imagePath != null) {
                loadImage(itemView.image, model.imagePath!!)
            } else {
                itemView.image.setImageResource(model.image)
            }

            val num: Int = model.num
            itemView.foodNum.text = "x️$num"
            itemView.foodPrice.text = "￥ " + num * model.price
        }
    }
}
package com.android.takeoutapp.holder

import android.view.View
import android.view.ViewGroup
import com.android.frameworktool.recycler.BaseRecyclerViewHolder
import com.android.frameworktool.util.appInflate
import com.android.takeoutapp.R
import com.android.takeoutapp.model.FoodDetailModel
import com.android.takeoutapp.util.SqlUtil.Companion.shoppingChecked
import kotlinx.android.synthetic.main.recycler_item_shopping_item.view.*

class ShoppingItemHolder(itemView: View) : BaseRecyclerViewHolder(itemView) {

    override fun config(model: Any?) {
        super.config(model)
        if (model is FoodDetailModel) {
            itemView.image.setImageResource(model.image)
            itemView.name.text = model.foodname
            itemView.checkBox.isChecked = model.isChecked
            itemView.price.text = "x${model.num}   ￥ ${model.num * model.price}"
            itemView.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                shoppingChecked(model.roomId, model.fId, isChecked)
            }
        }
    }

    companion object {
        fun creator(parent: ViewGroup): ShoppingItemHolder {
            return ShoppingItemHolder(parent.appInflate(R.layout.recycler_item_shopping_item))
        }
    }
}
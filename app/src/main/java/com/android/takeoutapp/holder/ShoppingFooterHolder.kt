package com.android.takeoutapp.holder

import android.view.View
import android.view.ViewGroup
import com.android.frameworktool.recycler.BaseRecyclerViewHolder
import com.android.frameworktool.util.appInflate
import com.android.frameworktool.util.onSingleClick
import com.android.takeoutapp.R
import com.android.takeoutapp.activity.AddOrderFormActivity
import com.android.takeoutapp.bean.NumBea
import com.android.takeoutapp.util.SqlUtil.Companion.getShoppingPrice
import kotlinx.android.synthetic.main.recycler_item_shopping_item_footer.view.*

class ShoppingFooterHolder(itemView: View) : BaseRecyclerViewHolder(itemView) {

    override fun config(model: Any?) {
        super.config(model)
        if (model is NumBea) {
            itemView.commit.onSingleClick {
                AddOrderFormActivity.newInstance(itemView.context, model)
            }
            itemView.price.text = "￥ ${getShoppingPrice(model.roomId)}"
        }
    }

    companion object {
        fun creator(parent: ViewGroup): ShoppingFooterHolder {
            return ShoppingFooterHolder(parent.appInflate(R.layout.recycler_item_shopping_item_footer))
        }
    }
}
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
import com.android.takeoutapp.util.ToastUtils
import kotlinx.android.synthetic.main.recycler_item_shopping_item_footer.view.*

class ShoppingFooterHolder(itemView: View) : BaseRecyclerViewHolder(itemView) {

    override fun config(model: Any?) {
        super.config(model)
        if (model is NumBea) {
            val price =getShoppingPrice(model.roomId)
            itemView.commit.onSingleClick {
                if (price>0){
                    AddOrderFormActivity.newInstance(itemView.context, model)

                }else{
                    ToastUtils.showToast(itemView.context,"请先选择餐品")
                }
            }
            itemView.price.text = "￥ $price"
        }
    }

    companion object {
        fun creator(parent: ViewGroup): ShoppingFooterHolder {
            return ShoppingFooterHolder(parent.appInflate(R.layout.recycler_item_shopping_item_footer))
        }
    }
}
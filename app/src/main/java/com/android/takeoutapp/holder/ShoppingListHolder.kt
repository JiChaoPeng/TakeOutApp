package com.android.takeoutapp.holder

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.frameworktool.recycler.BaseRecyclerViewHolder
import com.android.frameworktool.util.appInflate
import com.android.takeoutapp.R
import com.android.takeoutapp.adapter.ShoppingItemAdapter
import com.android.takeoutapp.bean.NumBea
import com.android.takeoutapp.model.RoomDetailModel
import com.android.takeoutapp.util.DataBeanUtil
import kotlinx.android.synthetic.main.recycler_item_shopping_list.view.*

class ShoppingListHolder(itemView: View) : BaseRecyclerViewHolder(itemView) {

    override fun config(model: Any?) {
        super.config(model)
        if (model is RoomDetailModel) {
            itemView.recycler.layoutManager = LinearLayoutManager(itemView.context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            val adapter = ShoppingItemAdapter()
            itemView.recycler.adapter = adapter
            adapter.modelList.clear()
            adapter.modelList.add(model)
            model.list.forEach {
                if (it.num > 0) {
                    adapter.modelList.add(it)
                }
            }
            adapter.modelList.add(NumBea(model.rId))
            adapter.notifyDataSetChanged()
        }
    }

    companion object {
        fun creator(parent: ViewGroup): ShoppingListHolder {
            return ShoppingListHolder(parent.appInflate(R.layout.recycler_item_shopping_list))
        }
    }
}
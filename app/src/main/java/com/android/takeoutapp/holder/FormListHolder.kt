package com.android.takeoutapp.holder

import android.view.View
import android.view.ViewGroup
import com.android.frameworktool.recycler.BaseRecyclerViewHolder
import com.android.frameworktool.util.appInflate
import com.android.takeoutapp.R
import com.android.takeoutapp.bean.FormBean
import kotlinx.android.synthetic.main.recycler_item_form_list.view.*
import java.text.SimpleDateFormat
import java.util.*

class FormListHolder(itemView: View) : BaseRecyclerViewHolder(itemView) {

    override fun config(model: Any?) {
        super.config(model)
        if (model is FormBean) {
            itemView.ownerName.text = model.username
            itemView.name.text = model.roomName
            itemView.price.text = "￥ ${model.price}"
            val currentDateTimeString: String =
                SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA).format(Date(model.time))
            itemView.time.text = "下单时间：$currentDateTimeString"

        }
    }

    companion object {
        fun creator(parent: ViewGroup): FormListHolder {
            return FormListHolder(parent.appInflate(R.layout.recycler_item_form_list))
        }
    }
}
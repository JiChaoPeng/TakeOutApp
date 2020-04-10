package com.android.takeoutapp.holder

import android.view.View
import android.view.ViewGroup
import com.android.frameworktool.recycler.BaseRecyclerViewHolder
import com.android.frameworktool.util.appInflate
import com.android.frameworktool.util.onSingleClick
import com.android.takeoutapp.R
import com.android.takeoutapp.activity.MapActivity
import com.android.takeoutapp.model.RoomDetailModel
import kotlinx.android.synthetic.main.recycler_item_room_detail.view.image
import kotlinx.android.synthetic.main.recycler_item_room_detail.view.name
import kotlinx.android.synthetic.main.recycler_item_room_header.view.*

class RoomDetailHeaderHolder(itemView: View) : BaseRecyclerViewHolder(itemView) {

    override fun config(model: Any?) {
        super.config(model)
        if (model is RoomDetailModel) {
            itemView.image.setImageResource(model.image)
            itemView.name.text = model.roomname
            itemView.address.text = model.address
            itemView.address.onSingleClick {
                MapActivity.newInstance(itemView.context, model.roomname, model.address)
            }
            itemView.content.text = model.explanation
        }
    }

    companion object {
        fun creator(parent: ViewGroup): RoomDetailHeaderHolder {
            return RoomDetailHeaderHolder(parent.appInflate(R.layout.recycler_item_room_header))
        }
    }
}
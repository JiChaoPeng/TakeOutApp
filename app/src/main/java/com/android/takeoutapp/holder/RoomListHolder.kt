package com.android.takeoutapp.holder

import android.view.View
import android.view.ViewGroup
import com.android.frameworktool.recycler.BaseRecyclerViewHolder
import com.android.frameworktool.util.appInflate
import com.android.takeoutapp.R
import com.android.takeoutapp.model.RoomDetailModel
import kotlinx.android.synthetic.main.recycler_item_room_list.view.*

class RoomListHolder(itemView: View) : BaseRecyclerViewHolder(itemView) {

    override fun config(model: Any?) {
        super.config(model)
        if (model is RoomDetailModel) {
            itemView.image.setImageResource(model.image)
            itemView.name.text = model.roomname
            itemView.address.text = model.adress
        }
    }

    companion object {
        fun creator(parent: ViewGroup): RoomListHolder {
            return RoomListHolder(parent.appInflate(R.layout.recycler_item_room_list))
        }
    }
}
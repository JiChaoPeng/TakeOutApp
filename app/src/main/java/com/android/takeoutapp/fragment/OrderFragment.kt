package com.android.takeoutapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.takeoutapp.R
import com.android.takeoutapp.activity.AddRoomActivity
import com.android.takeoutapp.activity.RoomDetailActivity
import com.android.takeoutapp.adapter.RoomListAdapter
import com.android.takeoutapp.model.RoomDetailModel
import com.android.takeoutapp.util.DataBeanUtil
import kotlinx.android.synthetic.main.fragment_order.*

/**
 * 2020/3/31
 */
class OrderFragment : Fragment() {
    private var adapter: RoomListAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
    }

    private fun initClick() {
        titleBar.rightOptionEvent = {
            startActivity(Intent(activity, AddRoomActivity::class.java))
        }
    }

    private fun initView() {
        titleBar.setTitle("点餐")
        context?.let {
            titleBar.setTitleTextColor(ContextCompat.getColor(it, R.color.textColor))
        }
        titleBar.setRightOptionText("添加餐厅")
        context?.let {
            titleBar.setBackGroundColor(ContextCompat.getColor(it, R.color.theme))
        }
        orderRecycler.layoutManager = LinearLayoutManager(activity).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        adapter = RoomListAdapter()
        orderRecycler.adapter = adapter
        adapter?.modelList?.clear()
        DataBeanUtil.roomListBean?.let {
            adapter?.modelList?.addAll(it.list)
        }
        adapter?.viewHolderConfig?.itemClickListener = {
            if (it is RoomDetailModel) {
                RoomDetailActivity.newInstance(context, it.rId)
            }
        }
    }
}
package com.android.takeoutapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.takeoutapp.R
import com.android.takeoutapp.activity.AddOrderFormActivity
import com.android.takeoutapp.adapter.ShoppingListAdapter
import com.android.takeoutapp.event.RefreshEvent
import com.android.takeoutapp.model.RoomDetailModel
import com.android.takeoutapp.util.DataBeanUtil
import kotlinx.android.synthetic.main.fragment_shopping.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ShoppingFragment : Fragment() {
    private var adapter: ShoppingListAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    override fun onResume() {
        super.onResume()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        initData()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGetMessage(message: RefreshEvent?) {
        initData()
    }

    override fun onPause() {
        super.onPause()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    private fun initData() {
        val bean = DataBeanUtil.roomListBean ?: return
        adapter?.modelList?.clear()
        bean.list.forEach { room ->
            room.list.forEachIndexed { index, foodDetailModel ->
                if (foodDetailModel.num > 0) {
                    adapter?.modelList?.add(room)
                    return@forEach
                }
            }
        }
        adapter?.notifyDataSetChanged()
    }

    private fun initView() {
        titleBar.setTitle("购物车")
        context?.let {
            titleBar.setTitleTextColor(ContextCompat.getColor(it, R.color.textColor))
        }
//        titleBar.setRightOptionText("添加餐厅")
        context?.let {
            titleBar.setBackGroundColor(ContextCompat.getColor(it, R.color.theme))
        }
        recyclerView.layoutManager = LinearLayoutManager(activity).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        adapter = ShoppingListAdapter()
        recyclerView.adapter = adapter

    }
}
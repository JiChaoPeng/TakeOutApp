package com.android.takeoutapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.frameworktool.base.BaseActivity
import com.android.frameworktool.util.onSingleClick
import com.android.takeoutapp.R
import com.android.takeoutapp.adapter.RoomDetailAdapter
import com.android.takeoutapp.model.RoomDetailModel
import com.android.takeoutapp.util.DataBeanUtil.Companion.getLocalBeanById
import kotlinx.android.synthetic.main.activity_room_detail.*

class RoomDetailActivity : BaseActivity() {
    private var bean: RoomDetailModel? = null
    private var adapter = RoomDetailAdapter()

    companion object {
        private const val ROOM_ID = "ROOM_ID"
        fun newInstance(context: Context?, roomId: Int?) {
            if (roomId == null) return
            val intent = Intent(context, RoomDetailActivity::class.java).apply {
                putExtra(ROOM_ID, roomId)
            }
            context?.startActivity(intent)
        }
    }

    override fun getContentView(): Int {
        return R.layout.activity_room_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bean = getLocalBeanById(intent.getIntExtra(ROOM_ID, 0))
        initView()
        initData()
    }

    private fun initData() {
        bean?.list?.let {
            adapter.modelList.clear()
            adapter.modelList.add(bean!!)
            adapter.modelList.addAll(it)
            adapter.notifyDataSetChanged()
        }

    }

    private fun initView() {
        recycler.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recycler.layoutManager = linearLayoutManager
        titleBar.setTitle(bean?.roomname)
        titleBar.setTitleTextColor(ContextCompat.getColor(this, R.color.textColor))
        titleBar.setLeftOptionImageVisible(true)
        titleBar.leftOptionEvent = {
            finish()
        }
        titleBar.setBackGroundColor(ContextCompat.getColor(this, R.color.theme))
    }
}

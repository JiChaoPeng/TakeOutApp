package com.android.takeoutapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.android.frameworktool.base.BaseActivity
import com.android.frameworktool.util.onSingleClick
import com.android.takeoutapp.R
import com.android.takeoutapp.model.RoomDetailModel
import com.android.takeoutapp.util.DataBeanUtil.Companion.getLocalBeanById
import kotlinx.android.synthetic.main.activity_room_detail.*

class RoomDetailActivity : BaseActivity() {
    private var bean: RoomDetailModel? = null

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
        initView()
    }

    private fun initView() {
        bean = getLocalBeanById(intent.getIntExtra(ROOM_ID, 0))
        titleBar.setTitle(bean?.roomname)
        titleBar.setLeftOptionImageVisible(true)
        titleBar.leftOptionEvent={
            finish()
        }
        titleBar.setBackGroundColor(ContextCompat.getColor(this, R.color.theme))
        map.onSingleClick {
            startActivity(Intent(this,MapActivity::class.java))
        }
    }
}

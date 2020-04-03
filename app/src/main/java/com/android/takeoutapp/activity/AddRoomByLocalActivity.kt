package com.android.takeoutapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.frameworktool.base.BaseActivity
import com.android.takeoutapp.R

class AddRoomByLocalActivity : BaseActivity() {
    override fun getContentView(): Int {
        return R.layout.activity_add_room_by_local
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

package com.android.takeoutapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.frameworktool.base.BaseActivity
import com.android.takeoutapp.R

class LoginActivity : BaseActivity() {
    override fun getContentView(): Int {
        return R.layout.activity_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}

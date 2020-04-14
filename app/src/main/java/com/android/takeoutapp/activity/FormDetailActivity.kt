package com.android.takeoutapp.activity

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.android.frameworktool.base.BaseActivity
import com.android.takeoutapp.R
import kotlinx.android.synthetic.main.activity_form_detail.*

class FormDetailActivity : BaseActivity() {
    override fun getContentView(): Int {
        return R.layout.activity_form_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        titleBar.setTitle("订单详情")
        titleBar.setTitleTextColor(ContextCompat.getColor(this, R.color.textColor))
        titleBar.setBackGroundColor(ContextCompat.getColor(this, R.color.theme))
        titleBar.setLeftOptionImageVisible(true)
        titleBar.leftOptionEvent = {
            finish()
        }

    }

}

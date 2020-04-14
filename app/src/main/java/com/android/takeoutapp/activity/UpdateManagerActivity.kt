package com.android.takeoutapp.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.android.frameworktool.base.BaseActivity
import com.android.frameworktool.util.onSingleClick
import com.android.takeoutapp.R
import com.android.takeoutapp.util.DataBeanUtil.Companion.roomListBean
import com.android.takeoutapp.util.SqlUtil.Companion.getUser
import com.android.takeoutapp.util.SqlUtil.Companion.setUser
import com.android.takeoutapp.util.ToastUtils
import kotlinx.android.synthetic.main.activity_update_manager.*


class UpdateManagerActivity : BaseActivity() {
    override fun getContentView(): Int {
        return R.layout.activity_update_manager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        titleBar.setTitle("选择要管理的店铺")
        titleBar.setTitleTextColor(ContextCompat.getColor(this, R.color.textColor))
        titleBar.setBackGroundColor(ContextCompat.getColor(this, R.color.theme))
        titleBar.setLeftOptionImageVisible(true)
        titleBar.leftOptionEvent = {
            finish()
        }
        val array = arrayListOf<String>()
        val list = roomListBean?.list
        if (list == null) finish()
        list?.forEach {
            array.add(it.roomname)
        }
        val adapter =
            ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item, array
            )
        //设置样式
        //设置样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //加载适配器
        //加载适配器
        spinner.adapter = adapter
        commit.onSingleClick {
            when {
                edit.text.isNullOrEmpty() -> {
                    ToastUtils.showToast(this,"请输入店铺管理员密码")
                }
                edit.text.toString()!= list!![spinner.selectedItemPosition].roomPassword -> {
                    ToastUtils.showToast(this,"店铺管理员密码错误")
                }
                else -> {
                    val user = getUser()
                    user?.roomId=list[spinner.selectedItemPosition].rId
                    user?.roomName=list[spinner.selectedItemPosition].roomname
                    setUser(user)
                    ToastUtils.showToast(this,"升级管理员成功！")
                    finish()
                }
            }
        }
    }

}

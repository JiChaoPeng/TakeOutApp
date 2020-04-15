package com.android.takeoutapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android.frameworktool.base.BaseActivity
import com.android.takeoutapp.activity.EnterActivity
import com.android.takeoutapp.activity.ManagerActivity
import com.android.takeoutapp.fragment.MineFragment
import com.android.takeoutapp.fragment.OrderFragment
import com.android.takeoutapp.fragment.ShoppingFragment
import com.android.takeoutapp.util.DataBeanUtil
import com.android.takeoutapp.util.SqlUtil.Companion.getUser
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {


    private var pagerList: List<Fragment>? = null
    private var pagerIndex: Int = -1
    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    override fun onResume() {
        super.onResume()
        val user = getUser()
        if (user == null) {
            startActivity(Intent(this, EnterActivity::class.java))
        }else if (user.roomId!=null){
            val intent = Intent(this, ManagerActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    private fun initData() {
    }

    private fun initView() {
        pagerList = listOf(
            OrderFragment(),
            ShoppingFragment(),
            MineFragment()
        )
        order.setOnClickListener {
            switchPage(0)
        }
        shopping.setOnClickListener {
            switchPage(1)
        }
        mine.setOnClickListener {
            switchPage(2)
        }
        switchPage(0)
    }

    private fun switchPage(index: Int) {
        if (pagerList == null || pagerIndex == index) return
        supportFragmentManager.beginTransaction().replace(R.id.content, pagerList!![index])
            .commit()
        pagerIndex = index
    }
}

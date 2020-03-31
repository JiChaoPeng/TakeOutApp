package com.android.takeoutapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.takeoutapp.fragment.MineFragment
import com.android.takeoutapp.fragment.OrderFragment
import com.android.takeoutapp.fragment.ShoppingFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var pagerList: List<Fragment>? = null
    private var pagerIndex: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
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
    }

    private fun switchPage(index: Int) {
        if (pagerList == null || pagerIndex == index) return
        supportFragmentManager.beginTransaction().replace(R.id.content, pagerList!![index])
            .commit()
        pagerIndex = index
    }
}

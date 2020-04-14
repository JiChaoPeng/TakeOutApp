package com.android.takeoutapp.activity

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.frameworktool.base.BaseActivity
import com.android.takeoutapp.R
import com.android.takeoutapp.adapter.ManagerFoodAdapter
import com.android.takeoutapp.model.FoodDetailModel
import com.android.takeoutapp.util.DataBeanUtil.Companion.roomListBean
import com.android.takeoutapp.util.SqlUtil.Companion.getUser
import kotlinx.android.synthetic.main.activity_manager_food.*

class ManagerFoodActivity : BaseActivity() {
    private var adapter = ManagerFoodAdapter()
    override fun getContentView(): Int {
        return R.layout.activity_manager_food
    }

    override fun onResume() {
        super.onResume()
        roomListBean?.list?.forEach {
            if (it.rId == getUser()?.roomId) {
                adapter.modelList.clear()
                adapter.modelList.addAll(it.list)
                adapter.notifyDataSetChanged()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        titleBar.setTitleTextColor(ContextCompat.getColor(this, R.color.textColor))
        titleBar.setBackGroundColor(ContextCompat.getColor(this, R.color.theme))
        titleBar.setTitle("管理菜单")
        titleBar.setLeftOptionImageVisible(true)
        titleBar.leftOptionEvent = {
            finish()
        }
        titleBar.setRightOptionText("添加餐品")
        titleBar.rightOptionEvent = {
            AddFoodActivity.newInstance(this)
        }
        recyclerView.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager

        adapter.viewHolderConfig.itemClickListener = {
            if (it is FoodDetailModel) {
                AddFoodActivity.newInstance(this, it)
            }
        }
    }
}

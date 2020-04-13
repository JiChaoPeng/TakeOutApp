package com.android.takeoutapp.activity

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.frameworktool.base.BaseActivity
import com.android.takeoutapp.R
import com.android.takeoutapp.adapter.FormListAdapter
import com.android.takeoutapp.util.DataBeanUtil.Companion.formBeanList
import kotlinx.android.synthetic.main.activity_form_list.*

class FormListActivity : BaseActivity() {
    private val adapter: FormListAdapter = FormListAdapter()
    override fun getContentView(): Int {
        return R.layout.activity_form_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        titleBar.setTitle("订单")
        titleBar.setTitleTextColor(ContextCompat.getColor(this, R.color.textColor))
        titleBar.setBackGroundColor(ContextCompat.getColor(this, R.color.theme))
        titleBar.setLeftOptionImageVisible(true)
        titleBar.leftOptionEvent = {
            finish()
        }
        recyclerView.adapter = adapter
        val layout = LinearLayoutManager(this)
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView!!.layoutManager = layout
        if (formBeanList?.list != null && formBeanList!!.list.isNotEmpty()) {
            adapter.modelList.clear()
            adapter.modelList.addAll(formBeanList!!.list)
            adapter.notifyDataSetChanged()
        }
        adapter.viewHolderConfig.itemClickListener={

        }
    }
}

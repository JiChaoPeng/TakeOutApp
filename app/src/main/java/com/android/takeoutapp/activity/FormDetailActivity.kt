package com.android.takeoutapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.frameworktool.base.BaseActivity
import com.android.takeoutapp.R
import com.android.takeoutapp.adapter.FormDetailAdapter
import com.android.takeoutapp.bean.FormBean
import com.android.takeoutapp.model.FoodDetailModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_form_detail.*

class FormDetailActivity : BaseActivity() {
    private val adapter = FormDetailAdapter()

    companion object {
        private const val FormBeanKey = "FormBeanKey"
        fun newInstance(context: Context?, form: FormBean) {
            val intent = Intent(context, FormDetailActivity::class.java).apply {
                putExtra(FormBeanKey, form)
            }
            context?.startActivity(intent)
        }
    }

    override fun getContentView(): Int {
        return R.layout.activity_form_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        val formBean = intent.getSerializableExtra(FormBeanKey) as? FormBean
        titleBar.setTitle("订单详情")
        titleBar.setTitleTextColor(ContextCompat.getColor(this, R.color.textColor))
        titleBar.setBackGroundColor(ContextCompat.getColor(this, R.color.theme))
        titleBar.setLeftOptionImageVisible(true)
        titleBar.leftOptionEvent = {
            finish()
        }
        if (formBean==null){
            finish()
        }
        recyclerView.adapter = adapter
        val layout = LinearLayoutManager(this)
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layout
        adapter.modelList.clear()
        adapter.modelList.addAll(formBean!!.foodList)
    }
}

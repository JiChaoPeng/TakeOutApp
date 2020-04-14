package com.android.takeoutapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.frameworktool.base.BaseActivity
import com.android.takeoutapp.R
import com.android.takeoutapp.adapter.FormListAdapter
import com.android.takeoutapp.bean.FormBean
import com.android.takeoutapp.util.DataBeanUtil.Companion.cacheBean
import com.android.takeoutapp.util.DataBeanUtil.Companion.formBeanList
import kotlinx.android.synthetic.main.activity_form_list.*

class FormListActivity : BaseActivity() {
    private val adapter: FormListAdapter = FormListAdapter()
    override fun getContentView(): Int {
        return R.layout.activity_form_list
    }

    companion object {
        private const val IS_MANAGER = "IS_MANAGER"
        fun newInstance(context: Context?, isManager: Boolean = false) {
            val intent = Intent(context, FormListActivity::class.java).apply {
                putExtra(IS_MANAGER, isManager)
            }
            context?.startActivity(intent)

        }
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

        if (intent.getBooleanExtra(IS_MANAGER, false)) {
            val arrayListOf = arrayListOf<FormBean>()
            cacheBean?.cache?.forEach {
                if (it.formList != null && it.formList!!.list.size > 0) {
                    arrayListOf.addAll(it.formList!!.list)
                }
            }
            adapter.modelList.clear()
            adapter.modelList.addAll(arrayListOf)
            adapter.notifyDataSetChanged()
        } else {
            if (formBeanList?.list != null && formBeanList!!.list.isNotEmpty()) {
                adapter.modelList.clear()
                adapter.modelList.addAll(formBeanList!!.list)
                adapter.notifyDataSetChanged()
            }
        }
        if (adapter.modelList.isNotEmpty()) {
            empty.visibility = View.GONE
        }
        adapter.viewHolderConfig.itemClickListener = {

        }
    }
}

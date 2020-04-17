package com.android.takeoutapp.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.android.frameworktool.base.BaseActivity
import com.android.frameworktool.util.onSingleClick
import com.android.takeoutapp.R
import com.android.takeoutapp.bean.FormBean
import kotlinx.android.synthetic.main.activity_form_commit.*
import kotlinx.android.synthetic.main.activity_form_commit.titleBar
import kotlinx.android.synthetic.main.activity_form_list.*

class FormCommitActivity : BaseActivity() {
    companion object{
        const val FORM_MODEL="FORM_MODEL"
        fun newInstance(context: Context?,bean:FormBean){
            val intent = Intent(context, FormCommitActivity::class.java)
            intent.putExtra(FORM_MODEL,bean)
            context?.startActivity(intent)
        }
    }
    override fun getContentView(): Int {
        return R.layout.activity_form_commit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val model = intent.getSerializableExtra(FORM_MODEL) as? FormBean
        address.text = model?.address
        titleBar.setTitle("下单成功")
        titleBar.setTitleTextColor(ContextCompat.getColor(this, R.color.textColor))
        titleBar.setBackGroundColor(ContextCompat.getColor(this, R.color.theme))
        titleBar.setLeftOptionImageVisible(true)
        titleBar.leftOptionEvent = {
            finish()
        }
        location.onSingleClick {
            MapActivity.newInstance(this,model?.roomName,model?.address)
        }
        form.onSingleClick {
            startActivity(Intent(this,FormListActivity::class.java))
        }
        if (model?.isTakeout==true){
            textView.text = "下单成功，订单将由商家配送，请保持手机畅通"
            address.text = "配送地址 ： ${model.userAddress}"
        }
    }
}

package com.android.takeoutapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.newsapp.util.AlertCallBack
import com.android.takeoutapp.util.AlertUtil
import com.android.takeoutapp.R
import com.android.takeoutapp.activity.EnterActivity
import com.android.takeoutapp.bean.UserBean
import com.android.takeoutapp.util.SqlUtil.Companion.getUser
import com.android.takeoutapp.util.SqlUtil.Companion.setUser
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : Fragment() {
    private var bean: UserBean? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logOut.setOnClickListener {
            AlertUtil.showAlert(activity, "退出", "确定退出当前账号？", object : AlertCallBack {
                override fun neutralButton() {
                    setUser(null)
                    name!!.text = ""
                    initData()
                    startActivity(Intent(activity, EnterActivity::class.java))
                }

                override fun negativeButton() {}
            })
        }
        form.setOnClickListener { v: View? ->
            //            startActivity(
//                Intent(activity, ::class.java)
//            )
        }
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    private fun initData() {
        bean = getUser()
        if (bean != null) { //本地已经存在用户缓存
            mineLayout!!.visibility = View.VISIBLE
            initView()
        } else {
            mineLayout!!.visibility = View.GONE
        }
    }

    private fun initView() {
        context?.let {
            val mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
            if (bean?.image == null) {
                Glide.with(it).load(R.mipmap.waimai).apply(mRequestOptions).into(avatar)
            } else {
                Glide.with(it).load(bean!!.image).apply(mRequestOptions)
                    .into(avatar)
            }
            name.text = bean?.username
        }
    }
}
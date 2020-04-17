package com.android.takeoutapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.android.frameworktool.base.BaseActivity
import com.android.frameworktool.util.onSingleClick
import com.android.newsapp.util.AlertCallBack
import com.android.takeoutapp.R
import com.android.takeoutapp.bean.UserBean
import com.android.takeoutapp.model.CacheBean
import com.android.takeoutapp.model.CacheListBean
import com.android.takeoutapp.util.AlertUtil
import com.android.takeoutapp.util.DataBeanUtil
import com.android.takeoutapp.util.SqlUtil.Companion.getUser
import com.android.takeoutapp.util.SqlUtil.Companion.setUser
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_mine.*

class ManagerActivity : BaseActivity() {
    private var bean: UserBean? = null
    override fun getContentView(): Int {
        return R.layout.activity_manager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        titleBar.setTitleTextColor(ContextCompat.getColor(this, R.color.textColor))
        titleBar.setBackGroundColor(ContextCompat.getColor(this, R.color.theme))
        titleBar.setTitle("管理员界面")
        titleBar.setLeftOptionImageVisible(true)
        titleBar.leftOptionEvent = {
            finish()
        }
        logOut.setOnClickListener {
            AlertUtil.showAlert(this, "退出", "确定退出当前账号？", object : AlertCallBack {
                override fun neutralButton() {
                    cache()
                    DataBeanUtil.roomListBean = null
                    DataBeanUtil.formBeanList = null
                    setUser(null)
                    bean = null
                    startActivity(Intent(this@ManagerActivity,SplashActivityActivity::class.java))
                }

                override fun negativeButton() {}
            })
        }
    }

    private fun cache() {
        if (DataBeanUtil.roomListBean != null && bean != null) {
            var cacheBean1 = DataBeanUtil.cacheBean
            if (cacheBean1 == null) {
                cacheBean1 = CacheListBean(
                    arrayListOf(
                        CacheBean(
                            DataBeanUtil.roomListBean!!,
                            bean?.username,
                            bean,
                            DataBeanUtil.formBeanList
                        )
                    )
                )
                DataBeanUtil.cacheBean = cacheBean1
            } else {
                cacheBean1.cache.forEachIndexed { index, model ->
                    if (model.name == bean!!.username) {
                        cacheBean1.cache[index].user = bean
                        cacheBean1.cache[index].listModel = DataBeanUtil.roomListBean!!
                        DataBeanUtil.cacheBean = cacheBean1
                        return
                    }
                }
                cacheBean1.cache.add(
                    CacheBean(
                        DataBeanUtil.roomListBean!!, bean!!.username,
                        bean,
                        DataBeanUtil.formBeanList
                    )
                )
                DataBeanUtil.cacheBean = cacheBean1
                return
            }
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
        val mRequestOptions = RequestOptions.circleCropTransform()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
        if (bean?.image == null) {
            Glide.with(this).load(R.mipmap.waimai).apply(mRequestOptions).into(avatar)
        } else {
            Glide.with(this).load(bean!!.image).apply(mRequestOptions)
                .into(avatar)
        }
        name.text = bean?.username

        if (bean?.roomId == null) {
            manager.visibility = View.GONE
            name.setTextColor(ContextCompat.getColor(this, R.color.text_black))
            managerLayout.visibility = View.GONE
            roomName.visibility = View.GONE
            allForm.visibility = View.GONE
            update.visibility = View.VISIBLE
            form.visibility = View.VISIBLE
        } else {
            update.visibility = View.GONE
            form.visibility = View.GONE
            manager.visibility = View.VISIBLE
            allForm.visibility = View.VISIBLE
            name.setTextColor(ContextCompat.getColor(this, R.color.white))
            managerLayout.visibility = View.VISIBLE
            roomName.visibility = View.VISIBLE
            roomName.text = "${bean?.roomName} 管理员"
        }
        allForm.onSingleClick {
            FormListActivity.newInstance(this, true)
        }
        manager.onSingleClick {
            startActivity(Intent(this, ManagerFoodActivity::class.java))
        }

    }
}

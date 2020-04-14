package com.android.takeoutapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android.frameworktool.util.onSingleClick
import com.android.newsapp.util.AlertCallBack
import com.android.takeoutapp.util.AlertUtil
import com.android.takeoutapp.R
import com.android.takeoutapp.activity.EnterActivity
import com.android.takeoutapp.activity.FormListActivity
import com.android.takeoutapp.activity.ManagerFoodActivity
import com.android.takeoutapp.activity.UpdateManagerActivity
import com.android.takeoutapp.bean.UserBean
import com.android.takeoutapp.model.CacheBean
import com.android.takeoutapp.model.CacheListBean
import com.android.takeoutapp.util.DataBeanUtil
import com.android.takeoutapp.util.DataBeanUtil.Companion.cacheBean
import com.android.takeoutapp.util.DataBeanUtil.Companion.formBeanList
import com.android.takeoutapp.util.DataBeanUtil.Companion.roomListBean
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
        titleBar.setTitle("我的")
        context?.let {
            titleBar.setTitleTextColor(ContextCompat.getColor(it, R.color.textColor))
        }
        context?.let {
            titleBar.setBackGroundColor(ContextCompat.getColor(it, R.color.theme))
        }
        form.onSingleClick {
            FormListActivity.newInstance(activity)
        }
        logOut.setOnClickListener {
            AlertUtil.showAlert(activity, "退出", "确定退出当前账号？", object : AlertCallBack {
                override fun neutralButton() {
                    cache()
                    roomListBean = null
                    formBeanList = null
                    setUser(null)
                    bean = null
                    name!!.text = ""
                    initData()
                    startActivity(Intent(activity, EnterActivity::class.java))
                }

                override fun negativeButton() {}
            })
        }
        update.onSingleClick {
            startActivity(Intent(activity, UpdateManagerActivity::class.java))
        }
    }

    private fun cache() {
        if (roomListBean != null && bean != null) {
            var cacheBean1 = cacheBean
            if (cacheBean1 == null) {
                cacheBean1 = CacheListBean(
                    arrayListOf(
                        CacheBean(
                            roomListBean!!,
                            bean?.username,
                            bean,
                            formBeanList
                        )
                    )
                )
                cacheBean = cacheBean1
            } else {
                cacheBean1.cache.forEachIndexed { index, model ->
                    if (model.name == bean!!.username) {
                        cacheBean1.cache[index].listModel = roomListBean!!
                        cacheBean = cacheBean1
                        return
                    }
                }
                cacheBean1.cache.add(
                    CacheBean(
                        roomListBean!!, bean!!.username,
                        bean,
                        formBeanList
                    )
                )
                cacheBean = cacheBean1
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

            if (bean?.roomId == null) {
                manager.visibility = View.GONE
                name.setTextColor(ContextCompat.getColor(activity!!, R.color.text_black))
                managerLayout.visibility = View.GONE
                roomName.visibility = View.GONE
                allForm.visibility = View.GONE
                update.visibility = View.VISIBLE
            } else {
                update.visibility = View.GONE
                manager.visibility = View.VISIBLE
                allForm.visibility = View.VISIBLE
                name.setTextColor(ContextCompat.getColor(activity!!, R.color.white))
                managerLayout.visibility = View.VISIBLE
                roomName.visibility = View.VISIBLE
                roomName.text = "${bean?.roomName} 管理员"
            }
        }
        allForm.onSingleClick {
            FormListActivity.newInstance(activity, true)
        }
        manager.onSingleClick {
            startActivity(Intent(activity, ManagerFoodActivity::class.java))
        }

    }
}
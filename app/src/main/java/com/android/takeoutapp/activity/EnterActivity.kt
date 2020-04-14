package com.android.takeoutapp.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.core.content.ContextCompat
import com.android.frameworktool.base.BaseActivity
import com.android.takeoutapp.R
import com.android.takeoutapp.util.DataBeanUtil
import com.android.takeoutapp.util.DataBeanUtil.Companion.formBeanList
import com.android.takeoutapp.util.DataBeanUtil.Companion.roomListBean
import com.android.takeoutapp.util.SqlUtil.Companion.getRegisterUser
import com.android.takeoutapp.util.SqlUtil.Companion.setUser
import com.android.takeoutapp.util.ToastUtils
import kotlinx.android.synthetic.main.activity_enter.*

class EnterActivity : BaseActivity() {
    override fun getContentView(): Int {
        return R.layout.activity_enter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        titleBar.setTitleTextColor(ContextCompat.getColor(this, R.color.textColor))
        titleBar.setBackGroundColor(ContextCompat.getColor(this, R.color.theme))
        titleBar.setTitle("登陆")
        titleBar.setLeftOptionImageVisible(true)
        titleBar.leftOptionEvent = {
            finish()
        }
        titleBar.rightOptionEvent = {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        titleBar.setRightOptionImageVisible(visible = true)
        titleBar.setRightOptionText("注册", ContextCompat.getColor(this, R.color.textColor))
        initClick()
    }

    private fun initClick() {
        loginButton.setOnClickListener { buttonClick() }
    }

    private fun buttonClick() {
        if (loginAccount!!.text == null || TextUtils.isEmpty(loginAccount!!.text)) {
            ToastUtils.showToast(this@EnterActivity, "账号不能为空！")
        } else if (loginPassword!!.text == null || TextUtils.isEmpty(loginPassword!!.text)) {
            ToastUtils.showToast(this@EnterActivity, "密码不能为空！")
        } else {
            login()
        }
    }

    private fun login() {
        val registerUser = getRegisterUser()
        if (registerUser == null || registerUser.list.size <= 0) {
            ToastUtils.showToast(this, "请先注册")
        } else {
            registerUser.list.forEach {
                if (it.username == loginAccount.text.toString()) {
                    if (it.password == loginPassword.text.toString()) {
                        setUser(it)
                        val cacheBean = DataBeanUtil.cacheBean
                        cacheBean?.cache?.forEach { cache ->
                            if (it.username == cache.name) {
                                roomListBean = cache.listModel
                                formBeanList=cache.formList
                                setUser(cache.user)
                                ToastUtils.showToast(this, "登陆成功")
                                finish()
                            }
                        }
                        DataBeanUtil.setLocalBean()
                        ToastUtils.showToast(this, "登陆成功")
                        finish()
                    } else {
                        ToastUtils.showToast(this, "密码错误 请重试")
                        return@forEach
                    }
                }
            }
            ToastUtils.showToast(this, "请检查用户名是否正确")
        }
    }
}
package com.android.takeoutapp.activity

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.android.frameworktool.base.BaseActivity
import com.android.takeoutapp.R
import com.android.takeoutapp.bean.UserBean
import com.android.takeoutapp.util.SqlUtil.Companion.getUser
import com.android.takeoutapp.util.SqlUtil.Companion.removeRegister
import com.android.takeoutapp.util.SqlUtil.Companion.setRegisterUser
import com.android.takeoutapp.util.SqlUtil.Companion.setUser
import com.android.takeoutapp.util.ToastUtils
import kotlinx.android.synthetic.main.activity_register.*

open class RegisterActivity : BaseActivity() {
    private var imageUrl: String? = null
    private var isRegister = true

    companion object {
        private const val Is_Register = "Is_Register"
        fun newInstance(context: Context?, isRegister: Boolean = true) {
            val intent = Intent(context, RegisterActivity::class.java).apply {
                putExtra(Is_Register, isRegister)
            }
            context?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isRegister = intent.getBooleanExtra(Is_Register, true)
        titleBar.setTitleTextColor(ContextCompat.getColor(this, R.color.textColor))
        titleBar.setBackGroundColor(ContextCompat.getColor(this, R.color.theme))
        titleBar.setLeftOptionImageVisible(true)
        if (isRegister) {
            titleBar.setTitle("注册")
            signUpButton.text = "注册"
        } else {
            titleBar.setTitle("修改资料")
            signUpButton.text = "确定修改"
            val user = getUser()
            if (user == null) finish()
            loginAccount.setText(user!!.username)
            loginAccount.isClickable = false
            loginAccount.isFocusable = false
            loginPassword.setText(user.password)
            loginAddress.setText(user.address)
            loginNumber.setText("${user.number}")
            imageUrl = user.image
        }
        titleBar.leftOptionEvent = {
            finish()
        }
        initClick()
    }

    override fun getContentView(): Int {
        return R.layout.activity_register
    }

    private fun initClick() {
        uploadButton.setOnClickListener { v: View? ->
            imageUrl = null
            val intent1 = Intent(Intent.ACTION_PICK)
            intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            startActivityForResult(intent1, 100)
        }
        signUpButton!!.setOnClickListener { v: View? -> buttonClick() }
    }

    private fun buttonClick() {
        if (loginAccount.text == null || TextUtils.isEmpty(loginAccount.text)) {
            Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show()
        } else if (loginPassword.text == null || TextUtils.isEmpty(loginPassword.text)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show()
        } else if (loginAddress.text == null || TextUtils.isEmpty(loginAddress.text)) {
            Toast.makeText(this, "地址不能为空", Toast.LENGTH_SHORT).show()
        } else if (loginNumber.text == null || TextUtils.isEmpty(loginNumber.text)) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show()
        } else if (loginNumber.text.toString().length != 11 || !loginNumber.text.toString().startsWith(
                "1"
            )
        ) {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show()
        } else if (imageUrl == null) {
            Toast.makeText(this, "请上传头像！", Toast.LENGTH_SHORT).show()
        } else {
            if (isRegister) {
                signUp()
            } else {
                changeInformation()
            }
        }
    }

    private fun changeInformation() {
        removeRegister(loginAccount.text.toString())
        val user = UserBean(
            loginAccount.text.toString(),
            loginPassword.text.toString(),
            imageUrl!!,
            25,
            0, null, null,
            loginNumber.text.toString().toLong(),
            loginAddress.text.toString()
        )
        val registerUser = setRegisterUser(user)
        if (registerUser) {
            setUser(user)
            ToastUtils.showToast(this, "信息修改成功")
            finish()
        } else {
            ToastUtils.showToast(this, "信息修改失败")
        }
    }

    private fun signUp() {
        val registerUser = setRegisterUser(
            UserBean(
                loginAccount.text.toString(),
                loginPassword.text.toString(),
                imageUrl!!,
                25,
                0, null, null,
                loginNumber.text.toString().toLong(),
                loginAddress.text.toString()
            )
        )
        if (registerUser) {
            finish()
        } else {
            ToastUtils.showToast(this, "注册失败，账号已存在")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 100) {
            if (data != null) {
                val uri = data.data
                if (uri != null) { //图库
                    val cursor: Cursor? = contentResolver.query(
                        uri,
                        arrayOf(MediaStore.Images.Media.DATA),
                        null,
                        null,
                        null
                    )
                    cursor?.moveToNext()
                    val filePath = cursor?.getString(0)
                    cursor?.close()
                    imageUrl = filePath
                }
            }
        }
    }

}
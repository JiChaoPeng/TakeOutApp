package com.android.takeoutapp.util

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.android.newsapp.util.AlertCallBack
import com.android.takeoutapp.R

object AlertUtil {
    fun showAlert(
        context: Context?,
        title: String?,
        message: String?,
        callback: AlertCallBack
    ) {
        val builder =
            AlertDialog.Builder(context!!)
        builder.setTitle(title) //设置内容
            .setIcon(R.mipmap.waimai) //设置是否可以点击对话框以外的地方消失
            .setMessage(message)
            .setCancelable(false)
            .setNeutralButton(
                "确定"
            ) { dialogInterface: DialogInterface, i: Int ->
                callback.neutralButton()
                dialogInterface.dismiss()
            }
            .setNegativeButton(
                "取消"
            ) { dialogInterface: DialogInterface, i: Int ->
                callback.negativeButton()
                dialogInterface.dismiss()
            }
        builder.create().show()
    }
}
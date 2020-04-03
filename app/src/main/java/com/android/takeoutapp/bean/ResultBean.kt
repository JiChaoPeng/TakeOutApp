package com.android.takeoutapp.bean

class ResultBean {
    val isSucceed: Boolean
        get() = resultCode == 200
    var resultCode = 0
    var data: String? = null
}
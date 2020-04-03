package com.android.takeoutapp.bean

class BaseResultModel<T> {
    val isSucceed: Boolean
        get() = resultCode == 200

    var resultCode = 0
    var data: String? = null
    var bean: T? = null

}
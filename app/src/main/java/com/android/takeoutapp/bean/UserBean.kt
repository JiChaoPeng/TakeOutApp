package com.android.takeoutapp.bean

class UserBean(
    var username: String,
    var password: String,
    var image: String,
    var age: Int,
    var id: Int,
    var roomId: Int? = null,
    var roomName: String? = null,
    var number: Int? = null,
    var address: String? = null
)

class UserListBean(var list: ArrayList<UserBean>)
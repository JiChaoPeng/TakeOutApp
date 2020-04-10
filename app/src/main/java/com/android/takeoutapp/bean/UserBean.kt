package com.android.takeoutapp.bean

class UserBean(
    var username: String,
    var password: String,
    var image: String,
    var age: Int,
    var id: Int
)
class UserListBean(var list: ArrayList<UserBean>)
package com.android.takeoutapp.model

/**
 * 2020/4/1
 */
class RoomDetailModel(
    var roomname: String,
    var roomPassword: String,
    var rId: Int,
    var address: String,
    var roomtime: Int,
    var explanation: String,
    var image: Int,
    var levels: Int,
    var oId: Int,
    var list: ArrayList<FoodDetailModel>,
    var isChecked: Boolean = false
    )
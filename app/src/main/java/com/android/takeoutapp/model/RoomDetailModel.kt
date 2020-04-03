package com.android.takeoutapp.model

/**
 * 2020/4/1
 */
class RoomDetailModel(
    var name: String,
    var roomId: Int,
    var adress: String,
    var icon: Int,
    var list: List<FoodDetailModel>
)
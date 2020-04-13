package com.android.takeoutapp.model

import java.io.Serializable

/**
 * 2020/4/1
 */
class FoodDetailModel(
    var foodname: String,
    var fId: Int,
    var price: Int,
    var image: Int,
    var explanation: String,
    var roomId: Int,
    var num: Int = 0,
    var isChecked: Boolean = false
):Serializable
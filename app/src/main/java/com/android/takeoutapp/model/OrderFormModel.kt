package com.android.takeoutapp.model

/**
 * 2020/4/3
 */
class OrderFormModel(
    var roomId: Int,
    var roomName: String,
    var roomImage: Int,
    var roomAddress: String,
    var orderId: Int,
    var orderPrice: Int,
    var orderTime: Long
)
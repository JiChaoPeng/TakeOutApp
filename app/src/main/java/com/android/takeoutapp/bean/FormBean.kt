package com.android.takeoutapp.bean

import com.android.takeoutapp.model.FoodDetailModel
import java.io.Serializable

/**
 * 2020/4/13
 */
class FormBean(
    var username: String,
    var userId: Int,
    var roomId: Int,
    var time: Long,
    var price: Int,
    var address: String,
    var roomName: String,
    var foodList: ArrayList<FoodDetailModel>,
    var isTakeout:Boolean=false,
    var userAddress: String?=null,
    var number: Long?=null
) : Serializable

class FormBeanList(
    var list: ArrayList<FormBean>
)
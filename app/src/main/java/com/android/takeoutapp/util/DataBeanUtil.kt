package com.android.takeoutapp.util

import com.android.takeoutapp.R
import com.android.takeoutapp.model.FoodDetailModel
import com.android.takeoutapp.model.RoomDetailModel
import com.android.takeoutapp.model.RoomListModel
import com.google.gson.Gson
import com.tencent.mmkv.MMKV

class DataBeanUtil {
    companion object {
        const val LocalBean = "LocalBean"
        private const val MDLRoomId = 0
        private const val MwskRoomId = 1
        fun setLocalBean() {
            val decode = MMKV.defaultMMKV().decodeString(LocalBean)
            if (decode == null) {
                val list = arrayListOf<RoomDetailModel>()
                list.add(getMdlModel())
                list.add(getMuskModel())
                MMKV.defaultMMKV().encode("LocalBean", Gson().toJson(RoomListModel(list)))
            }
        }

        var roomListBean: RoomListModel? = null
            set(value) {
                value?.let {
                    MMKV.defaultMMKV().encode("LocalBean", Gson().toJson(it))
                }
                field = value
            }
            get() {
                return if (field == null) {
                    Gson().fromJson(
                        MMKV.defaultMMKV().decodeString(LocalBean),
                        RoomListModel::class.java
                    )
                } else {
                    field
                }
            }

        private fun getMuskModel(): RoomDetailModel {
            val list = arrayListOf<FoodDetailModel>()
            list.add(
                FoodDetailModel(
                    "烤羊肉串（10串/份）",
                    10,
                    20,
                    R.mipmap.mwsk_chuan,
                    "三肥两瘦 黄金比例",
                    MwskRoomId
                )
            )
            list.add(FoodDetailModel("烤五花肉", 11, 22, R.mipmap.musk_chuan2, "内含烤五花肉5串", MwskRoomId))
            list.add(
                FoodDetailModel(
                    "烤茄子",
                    12,
                    12,
                    R.mipmap.mwsk_qz,
                    "锡纸烤茄子一份",
                    MwskRoomId
                )
            )
            list.add(
                FoodDetailModel(
                    "烤韭菜",
                    13,
                    14,
                    R.mipmap.mwsk_jc,
                    "锡纸烤韭菜一份",
                    MwskRoomId
                )
            )
            list.add(FoodDetailModel("手撕羊排", 14, 65, R.mipmap.mwsk_yangpai, "内含羊排一份", MwskRoomId))
            list.add(
                FoodDetailModel(
                    "香海烤虾",
                    15,
                    25,
                    R.mipmap.musk_xia,
                    "内含烤虾五串",
                    MwskRoomId
                )
            )
            list.add(FoodDetailModel("烤乳猪蹄", 15, 89, R.mipmap.musk_zt, "内含烤乳猪蹄3个", MwskRoomId))
            list.add(FoodDetailModel("烤土豆片", 15, 8, R.mipmap.musk_td, "内含烤土豆片3串", MwskRoomId))
            return RoomDetailModel("木屋烧烤", MwskRoomId, "北京市东城区和平里店", R.mipmap.mwsk, list)

        }

        private fun getMdlModel(): RoomDetailModel {
            val list = arrayListOf<FoodDetailModel>()
            list.add(FoodDetailModel("麦辣鸡腿堡", 0, 16, R.mipmap.mdl_1, "内含鸡腿堡一个", MDLRoomId))
            list.add(
                FoodDetailModel(
                    "鸡腿堡薯条套餐",
                    1,
                    16,
                    R.mipmap.mdl_2,
                    "内含鸡腿堡一个,薯条一份，中杯可乐一份",
                    MDLRoomId
                )
            )
            list.add(FoodDetailModel("牛肉堡", 2, 16, R.mipmap.mdl_3, "内含牛肉堡一个", MDLRoomId))
            list.add(
                FoodDetailModel(
                    "乐享三件套餐",
                    3,
                    16,
                    R.mipmap.mdl_4,
                    "内含鸡腿堡一个,牛肉堡一个，翅根两根",
                    MDLRoomId
                )
            )
            list.add(FoodDetailModel("麦趣鸡盒", 4, 16, R.mipmap.mdl_c, "内含麦趣鸡盒一盒", MDLRoomId))
            return RoomDetailModel("麦当兰麦乐送", MDLRoomId, "河北省保定市榕城区", R.mipmap.mdl, list)

        }
    }
}
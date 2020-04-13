package com.android.takeoutapp.util

import com.android.takeoutapp.R
import com.android.takeoutapp.model.*
import com.google.gson.Gson
import com.tencent.mmkv.MMKV

class DataBeanUtil {
    companion object {
        const val LocalBean = "LocalBean"
        const val CacheBean = "CacheBean"
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

        fun getLocalBeanById(roomId: Int): RoomDetailModel? {
            if (roomListBean != null && roomListBean!!.list.isNotEmpty()) {
                roomListBean!!.list.forEach {
                    if (it.rId == roomId) {
                        return it
                    }
                }
                return null
            } else {
                return null
            }
        }

        var roomListBean: RoomListModel? = null
            set(value) {
                if (value == null) {
                    MMKV.defaultMMKV().removeValueForKey("LocalBean")
                } else {
                    MMKV.defaultMMKV().encode("LocalBean", Gson().toJson(value))
                }
                field = value
            }
            get() {
                return if (field == null) {
                    Gson().fromJson(
                        MMKV.defaultMMKV().decodeString("LocalBean"),
                        RoomListModel::class.java
                    )
                } else {
                    field
                }
            }
        var cacheBean: CacheListBean? = null
            set(value) {
                if (value == null) {
                    MMKV.defaultMMKV().removeValueForKey(CacheBean)
                } else {
                    MMKV.defaultMMKV().encode(CacheBean, Gson().toJson(value))
                }
                field = value
            }
            get() {
                return if (field == null) {
                    if (MMKV.defaultMMKV().decodeString(CacheBean) == null) {
                        null
                    } else {
                        Gson().fromJson(
                            MMKV.defaultMMKV().decodeString(CacheBean),
                            CacheListBean::class.java
                        )
                    }

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
            return RoomDetailModel(
                "木屋烧烤",
                MwskRoomId,
                "河北省廊坊市广阳区新华路50号万达广场三楼",
                4,
                "木屋烧烤隶属于深圳市正君餐饮管理顾问有限公司，2003年木屋烧烤诞生于深圳城中村白石洲一家不足5平米的小店，历经十余载风风雨雨，一步一个脚印坚实走在发展的道路上。木屋烧烤一直把努力“做第一好吃的烧烤”作为第一经营理念，从而受大广大消费者的喜爱，发展至今已成为一个被权威媒体和各大平台认可的唯一全国性直营烧烤连锁品牌，已成功入驻北京、上海、广州、深圳、天津、重庆、佛山、东莞、惠州9座城市，时至2019年2月，直营门店已超过120家。",
                R.mipmap.mwsk,
                3,
                1,
                list
            )

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
            return RoomDetailModel(
                "麦当兰麦乐送",
                MDLRoomId,
                "河北省廊坊市广阳区新华路第五大街京客隆1层",
                5,
                "麦当劳是一家现代、锐意进取的汉堡公司。1955年，全球第一家麦当劳餐厅由创始人雷•克洛克（Ray Kroc）在美国伊利诺伊州芝加哥Des Plaines创立",
                R.mipmap.mdl,
                5,
                2,
                list
            )

        }
    }
}
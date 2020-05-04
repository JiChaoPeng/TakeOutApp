package com.android.takeoutapp.util

import com.android.takeoutapp.R
import com.android.takeoutapp.bean.FormBeanList
import com.android.takeoutapp.model.*
import com.google.gson.Gson
import com.tencent.mmkv.MMKV

class DataBeanUtil {
    companion object {
        const val LocalBean = "LocalBean"
        const val CacheBean = "CacheBean"
        private const val FormBeanKey = "FormBean"

        private const val MDLRoomId = 0
        private const val MwskRoomId = 1
        private const val HuangRoomId = 2
        private const val KangRoomId = 3
        private const val ShiRoomId = 4
        private const val WaRoomId = 5
        fun setLocalBean() {
            val decode = MMKV.defaultMMKV().decodeString(LocalBean)
            if (decode == null) {
                val list = arrayListOf<RoomDetailModel>()
                list.add(getMdlModel())
                list.add(getMuskModel())
                list.add(getKangModel())
                list.add(getHuangModel())
                list.add(getShiModel())
                list.add(getWaModel())

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

        var formBeanList: FormBeanList? = null
            set(value) {
                if (value == null) {
                    MMKV.defaultMMKV().removeValueForKey(FormBeanKey)
                } else {
                    MMKV.defaultMMKV().encode(FormBeanKey, Gson().toJson(value))
                }
                field = value
            }
            get() {
                return Gson().fromJson(
                    MMKV.defaultMMKV().decodeString(FormBeanKey),
                    FormBeanList::class.java
                )
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

        private fun getHuangModel(): RoomDetailModel {
            val list = arrayListOf<FoodDetailModel>()
            list.add(
                FoodDetailModel(
                    "黄焖鸡米饭（不含米饭）",
                    20,
                    18,
                    R.mipmap.huang0,
                    "取黄焖鸡一份，香米饭一份，将米饭直接扣入鸡锅内，搅拌均匀食之，会有不一样的味觉冲击（约7两）",
                    HuangRoomId
                )
            )
            list.add(
                FoodDetailModel(
                    "黄焖排骨",
                    21,
                    28,
                    R.mipmap.huang1,
                    "猪排骨为主料，不含米饭（约7两）",
                    HuangRoomId
                )
            )
            list.add(
                FoodDetailModel(
                    "传统米线",
                    22,
                    16,
                    R.mipmap.huang2,
                    "素菜全带，荤菜任选一种，请在备注中标明（鸡丸，鱼丸，鱼豆腐，墨鱼丸，鹌鹑蛋，虾丸，肥牛，肥羊）",
                    HuangRoomId
                )
            )
            list.add(
                FoodDetailModel(
                    "米饭",
                    23,
                    2,
                    R.mipmap.huang3,
                    "米饭约300克",
                    HuangRoomId
                )
            )
            return RoomDetailModel(
                "正宗米线黄焖鸡",
                "222",
                HuangRoomId,
                "河北省唐山市曹妃甸区汇丰路世纪名苑底商",
                3,
                "本店有黄焖鸡或排骨以及米线和配菜（米线可与面条双拼，请在备注中标明）",
                R.mipmap.huang,
                4,
                1,
                list
            )
        }

        private fun getWaModel(): RoomDetailModel {
            val list = arrayListOf<FoodDetailModel>()
            list.add(
                FoodDetailModel(
                    "特色虾恋蛙",
                    50,
                    158,
                    R.mipmap.wa0,
                    "此品鲜香麻辣，主料牛蛙和小龙虾",
                    WaRoomId
                )
            )
            list.add(
                FoodDetailModel(
                    "秘制羊蝎子",
                    51,
                    98,
                    R.mipmap.wa1,
                    "主要食材羊大梁，可挑选清汤，微辣，麻辣，请在备注说明",
                    WaRoomId
                )
            )
            list.add(
                FoodDetailModel(
                    "特色火锅鸡",
                    52,
                    68,
                    R.mipmap.wa2,
                    "可选微辣，麻辣（推荐麻辣），约750克",
                    WaRoomId
                )
            )
            list.add(
                FoodDetailModel(
                    "香辣牛板筋",
                    53,
                    33,
                    R.mipmap.wa3,
                    "主料牛板筋，约150克",
                    WaRoomId
                )
            )
            return RoomDetailModel(
                "虾恋蛙主题餐厅",
                "555",
                WaRoomId,
                "河北省唐山市曹妃甸区龙港景苑小区68号底商",
                3,
                "本店有各种肉锅（虾恋蛙，羊蝎子，火锅鸡等）",
                R.mipmap.wa,
                4,
                1,
                list
            )
        }

        private fun getShiModel(): RoomDetailModel {
            val list = arrayListOf<FoodDetailModel>()
            list.add(
                FoodDetailModel(
                    "鸡翅煲",
                    40,
                    99,
                    R.mipmap.shi0,
                    "主料有鸡翅；辅料有年糕，玉米，藕片，凤爪，南美虾，土豆",
                    ShiRoomId
                )
            )
            list.add(
                FoodDetailModel(
                    "猪手煲",
                    41,
                    69,
                    R.mipmap.shi1,
                    "主料有猪蹄；辅料有年糕，玉米，藕片，凤爪，南美虾，土豆",
                    ShiRoomId
                )
            )
            list.add(
                FoodDetailModel(
                    "明虾煲",
                    42,
                    169,
                    R.mipmap.shi2,
                    "主料有南美虾；辅料有年糕，玉米，藕片，凤爪，南美虾，土豆",
                    ShiRoomId
                )
            )
            list.add(
                FoodDetailModel(
                    "牛蛙煲",
                    43,
                    129,
                    R.mipmap.shi3,
                    "主料有南美虾；辅料有年糕，玉米，藕片，凤爪，南美虾，土豆",
                    ShiRoomId
                )
            )
            return RoomDetailModel(
                "石小胖肉蟹煲",
                "444",
                ShiRoomId,
                "河北省唐山市曹妃甸区唐海镇新城大街167号",
                3,
                "店内有各种口味肉蟹煲",
                R.mipmap.shi,
                4,
                1,
                list
            )
        }

        private fun getKangModel(): RoomDetailModel {
            val list = arrayListOf<FoodDetailModel>()
            list.add(
                FoodDetailModel(
                    "毛血旺",
                    30,
                    48,
                    R.mipmap.kang0,
                    "主要原料有鸭血，黄豆芽，鳝鱼，猪肉，毛肚，黄花菜，鱿鱼，午餐肉",
                    KangRoomId
                )
            )
            list.add(FoodDetailModel("水煮肉片", 31, 38, R.mipmap.kang1, "精选猪肉圆白菜 麻辣具在", KangRoomId))
            list.add(
                FoodDetailModel(
                    "干煸四季豆",
                    32,
                    32,
                    R.mipmap.kang2,
                    "主要原料为豆角，可微辣或麻辣",
                    KangRoomId
                )
            )
            list.add(
                FoodDetailModel(
                    "肥肠炖豆腐",
                    33,
                    38,
                    R.mipmap.kang3,
                    "主料为猪肠，豆腐，酸菜，白菜，鸭血",
                    KangRoomId
                )
            )
            return RoomDetailModel(
                "康庄餐厅",
                "333",
                KangRoomId,
                "河北省唐山市曹妃甸区唐海镇海丰里453号",
                3,
                "本店提供各种家常炒菜",
                R.mipmap.huang,
                4,
                1,
                list
            )
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
            list.add(FoodDetailModel("烤乳猪蹄", 16, 89, R.mipmap.musk_zt, "内含烤乳猪蹄3个", MwskRoomId))
            list.add(FoodDetailModel("烤土豆片", 17, 8, R.mipmap.musk_td, "内含烤土豆片3串", MwskRoomId))
            return RoomDetailModel(
                "木屋烧烤",
                "111",
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
                "000",
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
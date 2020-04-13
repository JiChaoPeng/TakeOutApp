package com.android.takeoutapp.model

import com.android.takeoutapp.bean.FormBeanList

/**
 * 2020/4/13
 */
class CacheBean(
    var listModel: RoomListModel,
    var name: String?,
    var formList: FormBeanList?
)

class CacheListBean(var cache: ArrayList<CacheBean>)


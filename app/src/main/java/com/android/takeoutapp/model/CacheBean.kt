package com.android.takeoutapp.model

import com.android.takeoutapp.bean.FormBeanList
import com.android.takeoutapp.bean.UserBean

/**
 * 2020/4/13
 */
class CacheBean(
    var listModel: RoomListModel,
    var name: String?,
    var user: UserBean?,
    var formList: FormBeanList?
)

class CacheListBean(var cache: ArrayList<CacheBean>)


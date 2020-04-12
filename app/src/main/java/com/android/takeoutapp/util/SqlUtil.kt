package com.android.takeoutapp.util

import com.android.takeoutapp.bean.NumBea
import com.android.takeoutapp.bean.UserBean
import com.android.takeoutapp.bean.UserListBean
import com.android.takeoutapp.event.RefreshEvent
import com.android.takeoutapp.model.FoodDetailModel
import com.android.takeoutapp.util.DataBeanUtil.Companion.roomListBean
import com.google.gson.Gson
import com.tencent.mmkv.MMKV
import org.greenrobot.eventbus.EventBus

/**
 * 2020/4/10
 */
class SqlUtil {
    companion object {
        private const val UserKey = "UserKey"
        private const val RegisterUserKey = "RegisterUserKey"
        private const val ShoppingListKey = "ShoppingListKey"
        fun setUser(user: UserBean?) {
            if (user == null) {
                MMKV.defaultMMKV().removeValueForKey(UserKey)
            } else {
                MMKV.defaultMMKV().encode(UserKey, Gson().toJson(user))
            }
        }

        fun getUser(): UserBean? {
            val decodeString = MMKV.defaultMMKV().decodeString(UserKey)
            return Gson().fromJson<UserBean>(decodeString, UserBean::class.java)
        }

        fun setRegisterUser(user: UserBean): Boolean {
            val registerUser = getRegisterUser()
            if (registerUser == null || registerUser.list.size == 0) {
                MMKV.defaultMMKV()
                    .encode(RegisterUserKey, Gson().toJson(UserListBean(arrayListOf(user))))
                return true
            } else {
                registerUser.list.forEach {
                    if (user.username == it.username) {
                        return false
                    }
                }
                registerUser.list.add(user)
                MMKV.defaultMMKV().encode(RegisterUserKey, Gson().toJson(registerUser))
                return true
            }
        }

        fun getRegisterUser(): UserListBean? {
            val decodeString = MMKV.defaultMMKV().decodeString(RegisterUserKey)
            return Gson().fromJson<UserListBean>(decodeString, UserListBean::class.java)
        }

        fun addShopping(roomId: Int, foodBean: FoodDetailModel, isAdd: Boolean) {
            val model = roomListBean ?: return
            model.list.forEachIndexed { index, it ->
                if (it.rId == roomId) {
                    it.list.forEachIndexed { index1, bean ->
                        if (bean.fId == foodBean.fId) {
                            if (isAdd) {
                                model.list[index].list[index1].num++
                                roomListBean = model
                                return
                            } else if (bean.num > 0) {
                                model.list[index].list[index1].num--
                                roomListBean = model
                                return
                            }
                        }
                    }
                }
            }

        }

        fun shoppingChecked(roomId: Int, checked: Boolean) {
            val model = roomListBean ?: return
            model.list.forEachIndexed { index, it ->
                if (it.rId == roomId) {
                    it.list.forEachIndexed { index1, it ->
                        model.list[index].list[index1].isChecked = checked
                    }
                    model.list[index].isChecked = checked
                    roomListBean = model
                    EventBus.getDefault().post(RefreshEvent())
                    return
                }
            }
        }

        fun shoppingChecked(roomId: Int, foodId: Int, checked: Boolean) {
            val model = roomListBean ?: return
            model.list.forEachIndexed { index, room ->
                if (room.rId == roomId) {
                    room.list.forEachIndexed { index1, food ->
                        if (food.fId == foodId) {
                            model.list[index].list[index1].isChecked = checked
                            roomListBean = model
                            EventBus.getDefault().post(RefreshEvent())
                            return
                        }
                    }
                }
            }
        }

        fun getShoppingPrice(roomId: Int): Int {
            val model = roomListBean ?: return 0
            var price = 0
            model.list.forEachIndexed { index, room ->
                if (room.rId == roomId) {
                    room.list.forEachIndexed { index1, food ->
                        if (food.isChecked){
                            price += food.num * food.price
                        }
                    }
                }
            }
            return price
        }

    }
}
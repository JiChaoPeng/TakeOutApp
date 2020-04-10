package com.android.takeoutapp.util

import com.android.takeoutapp.bean.UserBean
import com.android.takeoutapp.bean.UserListBean
import com.google.gson.Gson
import com.tencent.mmkv.MMKV

/**
 * 2020/4/10
 */
class SqlUtil {
    companion object {
        private const val UserKey = "UserKey"
        private const val RegisterUserKey = "RegisterUserKey"
        fun setUser(user: UserBean) {
            MMKV.defaultMMKV().encode(UserKey, Gson().toJson(user))
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

    }

}
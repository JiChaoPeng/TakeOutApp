package com.android.takeoutapp.net

import com.android.frameworktool.network.NetWorkManager.Companion.getNetworkManager
import retrofit2.Retrofit

/**
 * 2020/3/29
 */
class NetWork {

    companion object {
        private var networkManager: Retrofit? = null
        private var network: NetWork? = null
        val loginServices: NetWork?
            get() = if (network == null) {
                network = NetWork()
                network
            } else {
                network
            }

        private val netWorkServices: Retrofit?
            get() = if (networkManager == null) {
                networkManager =
                    getNetworkManager("http://39.99.210.2:8080/")
                networkManager
            } else {
                networkManager
            }
    }

    var networkServices =
        netWorkServices?.create(
            NetWorkServices::class.java
        )
}
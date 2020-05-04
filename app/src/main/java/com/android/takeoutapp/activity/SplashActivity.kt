package com.android.takeoutapp.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.frameworktool.base.BaseActivity
import com.android.takeoutapp.MainActivity
import com.android.takeoutapp.R
import com.android.takeoutapp.util.SqlUtil
import com.tencent.mmkv.MMKV

class SplashActivity : BaseActivity() {
    override fun getContentView(): Int {
        return R.layout.activity_splash
    }

    override fun onResume() {
        super.onResume()
        val user = SqlUtil.getUser()
        when {
            user == null -> {
                startActivity(Intent(this, EnterActivity::class.java))
            }
            user.roomId != null -> {
                startActivity(Intent(this, ManagerActivity::class.java))
                finish()
            }
            else -> {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MMKV.initialize(this)
        //申请权限
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                100
            )
        }
    }
}

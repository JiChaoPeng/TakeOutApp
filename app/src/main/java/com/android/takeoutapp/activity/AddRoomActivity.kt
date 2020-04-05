package com.android.takeoutapp.activity

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.ContextCompat
import com.android.frameworktool.base.BaseActivity
import com.android.takeoutapp.R
import com.android.takeoutapp.bean.BaseResultModel
import com.android.takeoutapp.bean.ResultBean
import com.android.takeoutapp.model.RoomDetailModel
import com.android.takeoutapp.net.NetWork.Companion.loginServices
import com.android.takeoutapp.util.AppIndex
import com.android.takeoutapp.util.ToastUtils
import kotlinx.android.synthetic.main.activity_add_room.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddRoomActivity : BaseActivity() {
    private var imagePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        titleBar.setTitle("添加餐厅")
        titleBar.setBackGroundColor(ContextCompat.getColor(this, R.color.theme))
        initClick()
    }

    override fun getContentView(): Int {
        return R.layout.activity_add_room
    }

    private fun initClick() {
        uploadImage.setOnClickListener {
            imagePath = null
            val intent1 = Intent(Intent.ACTION_PICK)
            intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            startActivityForResult(intent1, 100)
        }
        complete.setOnClickListener {
            when {
                roomName.text.toString().isNullOrEmpty() -> {
                    ToastUtils.showToast(this, "名称不能为空！！")
                }
                roomAddress.text.toString().isNullOrEmpty() -> {
                    ToastUtils.showToast(this, "地址不能为空！！")
                }
                roomAge.text.toString().isNullOrEmpty() -> {
                    ToastUtils.showToast(this, "年限不能为空！！")
                }
                roomLevel.text.toString().isNullOrEmpty() -> {
                    ToastUtils.showToast(this, "星级不能为空！！")
                }
                (roomLevel.text.toString().toInt()) < 1 || roomLevel.text.toString().toInt() > 5 -> {
                    ToastUtils.showToast(this, "星级只能在1-5！！")
                }
                imagePath.isNullOrEmpty() -> {
                    ToastUtils.showToast(this, "图片不能为空！！")
                }
                else -> {
                    addRoom()
                }
            }
        }
    }

    private fun addRoom() {
        loginServices?.networkServices?.addRoom(
            roomName.text.toString(), "2", roomLevel.text.toString().toInt(),
            roomAge.text.toString().toInt(), imagePath, roomAddress.text.toString(), AppIndex
        )
            ?.enqueue(object : Callback<BaseResultModel<RoomDetailModel>> {
                override fun onFailure(call: Call<BaseResultModel<RoomDetailModel>>, t: Throwable) {
                    Log.d("AddRoomActivity", "faild ${t.message} ${t.cause}")
                }

                override fun onResponse(
                    call: Call<BaseResultModel<RoomDetailModel>>,
                    response: Response<BaseResultModel<RoomDetailModel>>
                ) {
                    Log.d(
                        "AddRoomActivity",
                        "data ${response.body()?.data}  data ${response.body()?.bean?.name}  ${response.body()?.bean?.adress}  "
                    )
                }

            })
    }

    private fun uploadImage(path: String) {
        val file = File(path)
        // 上传文件参数
        val body = RequestBody.create("image/jpg".toMediaTypeOrNull(), file)
        val files: MultipartBody.Part =
            MultipartBody.Part.createFormData("file", file.name, body)
        loginServices?.networkServices?.upload(files)?.enqueue(object : Callback<ResultBean> {
            override fun onFailure(call: Call<ResultBean>, t: Throwable) {
                Log.d("AddRoomActivity", "faild ${t.message} ${t.cause}")
            }

            override fun onResponse(call: Call<ResultBean>, response: Response<ResultBean>) {
                Log.d("AddRoomActivity", response.toString())
                imagePath = response.body()?.data
            }

        })
    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 100) {
            if (data != null) {
                val uri = data.data
                if (uri != null) {
                    val cursor = contentResolver.query(
                        uri,
                        arrayOf(MediaStore.Images.Media.DATA),
                        null,
                        null,
                        null
                    )
                    cursor!!.moveToNext()
                    val filePath = cursor.getString(0)
                    cursor.close()
                    uploadImage(filePath)
                }
            }
        }
    }
}

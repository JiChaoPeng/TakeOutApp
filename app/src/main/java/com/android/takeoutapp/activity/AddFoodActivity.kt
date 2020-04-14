package com.android.takeoutapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import com.android.frameworktool.base.BaseActivity
import com.android.takeoutapp.R
import com.android.takeoutapp.model.FoodDetailModel
import com.android.takeoutapp.util.DataBeanUtil.Companion.cacheBean
import com.android.takeoutapp.util.DataBeanUtil.Companion.roomListBean
import com.android.takeoutapp.util.SqlUtil.Companion.getUser
import com.android.takeoutapp.util.ToastUtils
import kotlinx.android.synthetic.main.activity_add_food.*

class AddFoodActivity : BaseActivity() {
    private var foodDetail: FoodDetailModel? = null
    private var isAddFood: Boolean = true
    private var imagePath: String? = null
    private var image: Int = 0
    override fun getContentView(): Int {
        return R.layout.activity_add_food
    }

    companion object {
        private const val FoodDetail = "FoodDetail"
        fun newInstance(context: Context?, foodDetail: FoodDetailModel? = null) {
            val intent = Intent(context, AddFoodActivity::class.java).apply {
                putExtra(FoodDetail, foodDetail)
            }
            context?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foodDetail = intent.getSerializableExtra(FoodDetail) as? FoodDetailModel
        if (foodDetail != null) {
            isAddFood = false
        }
        titleBar.setTitleTextColor(ContextCompat.getColor(this, R.color.textColor))
        titleBar.setBackGroundColor(ContextCompat.getColor(this, R.color.theme))
        if (isAddFood) {
            titleBar.setTitle("添加餐品")
        } else {
            titleBar.setTitle("编辑餐品")
        }
        titleBar.setLeftOptionImageVisible(true)
        titleBar.leftOptionEvent = {
            finish()
        }
        if (!isAddFood) {
            foodName.setText(foodDetail!!.foodname)
            foodExplanation.setText(foodDetail!!.explanation)
            foodPrice.setText("${foodDetail!!.price}")
            image = foodDetail!!.image
            imagePath = foodDetail!!.imagePath
        }
        initClick()
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
                foodName.text.toString().isEmpty() -> {
                    ToastUtils.showToast(this, "名称不能为空！！")
                }
                foodExplanation.text.toString().isEmpty() -> {
                    ToastUtils.showToast(this, "描述不能为空！！")
                }
                foodPrice.text.toString().isEmpty() -> {
                    ToastUtils.showToast(this, "价格不能为空！！")
                }
                imagePath.isNullOrEmpty() && image == 0 -> {
                    ToastUtils.showToast(this, "图片不能为空！！")
                }
                else -> {
                    if (isAddFood) {
                        addFood()
                    } else {
                        updateFood()
                    }
                }
            }
        }
    }

    private fun updateFood() {
        val foodDetailModel = FoodDetailModel(
            foodName.text.toString(),
            foodDetail!!.fId,
            foodPrice.text.toString().toInt(),
            image,
            foodExplanation.text.toString(),
            foodDetail!!.roomId, 0, false,imagePath
        )

        val roomList = roomListBean
        roomList?.list?.forEachIndexed { index, roomDetailModel ->
            if (roomDetailModel.rId == foodDetail!!.roomId) {
                roomDetailModel.list.forEachIndexed { index2, foodDetail ->
                    if (foodDetail.fId == foodDetailModel.fId) {
                        roomList.list[index].list[index2] = foodDetailModel
                        roomListBean = roomList
                    }
                }
            }
        }
        val cache = cacheBean
        cache?.cache?.forEachIndexed { index, cacheBean ->
            if (cacheBean.name != getUser()!!.username) {
                cacheBean.listModel.list.let { room ->
                    room.forEachIndexed { index1, roomDetailModel ->
                        if (roomDetailModel.rId == foodDetail!!.roomId) {
                            roomDetailModel.list.forEachIndexed { index2, foodDetail ->
                                if (foodDetailModel.fId == foodDetail.fId) {
                                    cache.cache[index].listModel.list[index1].list[index2] =
                                        foodDetailModel
                                }
                            }
                        }
                    }
                }
            }
        }
        cacheBean = cache
        finish()
    }

    private fun addFood() {
        getUser()?.roomId?.let {
            val foodDetailModel = FoodDetailModel(
                foodName.text.toString(),
                (100..10000).random(),
                foodPrice.text.toString().toInt(),
                image,
                foodExplanation.text.toString(),
                it, 0, false, imagePath
            )
            val roomList = roomListBean
            roomList?.list?.forEachIndexed { index, roomDetailModel ->
                if (roomDetailModel.rId == it) {
                    roomList.list[index].list.add(0, foodDetailModel)
                    roomListBean = roomList
                }
            }
            val cache = cacheBean
            cache?.cache?.forEachIndexed { index, cacheBean ->
                if (cacheBean.name != getUser()!!.username) {
                    cacheBean.listModel.list.let { room ->
                        room.forEachIndexed { index1, roomDetailModel ->
                            if (roomDetailModel.rId == it) {
                                cache.cache[index].listModel.list[index1].list.add(
                                    0,
                                    foodDetailModel
                                )
                            }
                        }
                    }
                }
            }
            cacheBean = cache
            finish()
        }
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
                    imagePath = cursor.getString(0)
                    cursor.close()
                }
            }
        }
    }
}

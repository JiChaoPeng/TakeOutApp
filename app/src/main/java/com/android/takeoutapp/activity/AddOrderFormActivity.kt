package com.android.takeoutapp.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.frameworktool.base.BaseActivity
import com.android.frameworktool.util.onSingleClick
import com.android.takeoutapp.R
import com.android.takeoutapp.adapter.OrderFormAdapter
import com.android.takeoutapp.bean.FormBean
import com.android.takeoutapp.bean.FormBeanList
import com.android.takeoutapp.bean.NumBea
import com.android.takeoutapp.model.FoodDetailModel
import com.android.takeoutapp.util.DataBeanUtil.Companion.formBeanList
import com.android.takeoutapp.util.DataBeanUtil.Companion.roomListBean
import com.android.takeoutapp.util.SqlUtil.Companion.getUser
import kotlinx.android.synthetic.main.activity_order_form.*

class AddOrderFormActivity : BaseActivity() {
    private var allPrice = 0
    private val adapter: OrderFormAdapter = OrderFormAdapter()
    private val mealBeans: ArrayList<FoodDetailModel> = ArrayList()
    private var numBea: NumBea? = null

    companion object {
        private const val ROOMID = "ROOMID"
        fun newInstance(context: Context?, num: NumBea) {
            val intent = Intent(context, AddOrderFormActivity::class.java)
            intent.putExtra(ROOMID, num)
            context?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        titleBar.setTitle("提交订单")
        titleBar.setTitleTextColor(ContextCompat.getColor(this, R.color.textColor))
        titleBar.setLeftOptionImageVisible(true)
        titleBar.leftOptionEvent = {
            finish()
        }
        numBea = intent.getSerializableExtra(ROOMID) as? NumBea
        initView()
        commit.onSingleClick {
            val user = getUser()
            user?.let {
                val formBean = FormBean(
                    user.username,
                    user.id,
                    intent.getIntExtra(ROOMID, 0),
                    System.currentTimeMillis(),
                    allPrice,
                    numBea!!.roomAddress,
                    numBea!!.roomName,
                    mealBeans
                )
                var formBeanList1 = formBeanList
                if (formBeanList1 == null || formBeanList1.list.isEmpty()) {
                    formBeanList1 = FormBeanList(arrayListOf(formBean))
                    formBeanList = formBeanList1
                } else {
                    formBeanList1.list.add(0, formBean)
                    formBeanList = formBeanList1
                }
                val listBean = roomListBean
                roomListBean?.list?.forEachIndexed { index, roomDetailModel ->
                    if (roomDetailModel.rId == numBea!!.roomId) {
                        roomDetailModel.list.forEachIndexed { index1, foodDetailModel ->
                            if (foodDetailModel.isChecked) {
                                listBean!!.list[index].list[index1].num = 0
                                listBean.list[index].list[index1].isChecked = false
                            }

                        }
                    }
                }
                roomListBean = listBean

                FormCommitActivity.newInstance(this, formBean)
                finish()
            }
        }
    }

    override fun getContentView(): Int {
        return R.layout.activity_order_form
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        name.text = "下单用户：" + getUser()?.username
        titleBar.setBackGroundColor(ContextCompat.getColor(this, R.color.theme))
        titleBar.setTitle("结算")
        titleBar.setLeftOptionImageVisible(true)
        titleBar.leftOptionEvent = {
            finish()
        }
        commit.setOnClickListener { v: View? -> }
        recyclerView!!.adapter = adapter
        val layout = LinearLayoutManager(this)
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView!!.layoutManager = layout
        allPrice = 0
        roomListBean?.let {
            for (model in it.list) {
                if (model.rId == numBea!!.roomId) {
                    for (food in model.list) {
                        if (food.isChecked) {
                            mealBeans.add(food)
                            allPrice += food.price * food.num
                        }
                    }
                }
            }
            adapter.modelList.clear()
            adapter.modelList.addAll(mealBeans)
            adapter.notifyDataSetChanged()
            price.text = "总价 ： ￥ $allPrice"
        }

    }
}
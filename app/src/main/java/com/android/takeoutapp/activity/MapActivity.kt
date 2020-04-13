package com.android.takeoutapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.services.geocoder.GeocodeQuery
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeResult
import com.android.frameworktool.base.BaseActivity
import com.android.takeoutapp.R
import kotlinx.android.synthetic.main.activity_map.*


class MapActivity : BaseActivity(), GeocodeSearch.OnGeocodeSearchListener {
    companion object {
        private const val RoomName = "RoomName"
        private const val RoomAddress = "RoomAddress"
        fun newInstance(context: Context, roomName: String?, roomAddress: String?) {
            val intent = Intent(context, MapActivity::class.java).apply {
                putExtra(RoomName, roomName)
                putExtra(RoomAddress, roomAddress)
            }
            context.startActivity(intent)
        }
    }

    override fun getContentView(): Int {
        return R.layout.activity_map
    }

    private var aMap: AMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMapView.onCreate(savedInstanceState)
        initView()
        initMap()
    }

    private fun initMap() {
        //定义了一个地图view
        //定义了一个地图view
        //初始化地图控制器对象
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.map
        }
        val myLocationStyle =
            MyLocationStyle() //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000) //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。

        aMap?.myLocationStyle = myLocationStyle //设置定位蓝点的Style
        //设置默认定位按钮是否显示，非必需设置。
        aMap?.uiSettings?.isMyLocationButtonEnabled = true
        //现实室内地图
        aMap?.showIndoorMap(true)
//        aMap?.isMyLocationEnabled = true // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
        //以下三种模式从5.1.0版本开始提供
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
    }

    private fun initView() {
        titleBar.setTitle(intent.getStringExtra(RoomName))
        titleBar.setTitleTextColor(ContextCompat.getColor(this, R.color.textColor))
        titleBar.setLeftOptionImageVisible(true)
        titleBar.leftOptionEvent = {
            finish()
        }
        titleBar.setBackGroundColor(ContextCompat.getColor(this, R.color.theme))
        getAddress()
    }

    private fun getAddress() {
        val geocoderSearch = GeocodeSearch(this)
        geocoderSearch.setOnGeocodeSearchListener(this)
// name表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode
        // name表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode
        val query = GeocodeQuery(intent.getStringExtra(RoomAddress), "廊坊")
        geocoderSearch.getFromLocationNameAsyn(query)

    }

    //根据坐标获取地址信息调用
    override fun onRegeocodeSearched(p0: RegeocodeResult?, p1: Int) {
    }

    //根据地址获取坐标信息是调用
    override fun onGeocodeSearched(p0: GeocodeResult?, p1: Int) {

        val geocodeAddressList = p0?.geocodeAddressList
        if (geocodeAddressList != null && geocodeAddressList.size > 0) {
            val latLonPoint = geocodeAddressList[0].latLonPoint
            val latLng = LatLng(latLonPoint.latitude, latLonPoint.longitude)
            val marker = aMap?.addMarker(
                MarkerOptions().position(latLng).title(
                    intent.getStringExtra(
                        RoomName
                    )
                ).snippet(intent.getStringExtra(RoomAddress))
            )
            //改变可视区域为指定位置
            //CameraPosition4个参数分别为位置，缩放级别，目标可视区域倾斜度，可视区域指向方向（正北逆时针算起，0-360）
            val cameraUpdate =
                CameraUpdateFactory.newCameraPosition(CameraPosition(latLng, 15f, 0f, 0f))
            aMap?.moveCamera(cameraUpdate);//地图移向指定区域

            Log.d("GDMapActivity", "p0 ${p0.toString()} p1 $p1")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy()

    }

    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }


}

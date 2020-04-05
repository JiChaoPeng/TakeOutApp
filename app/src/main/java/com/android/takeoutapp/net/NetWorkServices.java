package com.android.takeoutapp.net;


import com.android.takeoutapp.MainActivity;
import com.android.takeoutapp.bean.BaseResultModel;
import com.android.takeoutapp.bean.ResultBean;
import com.android.takeoutapp.model.RoomDetailModel;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 2020/3/29
 */
public interface NetWorkServices {
    @FormUrlEncoded
    @POST("android/v1/addRoom")
    Call<BaseResultModel<RoomDetailModel>> addRoom(
            @Field("name") String name,
            @Field("ownerId") String ownerId,
            @Field("level") int level,
            @Field("age") int age,
            @Field("imageUrl") String imageUrl,
            @Field("address") String address,
            @Field("appIndex") int appIndex);

//    @FormUrlEncoded
//    @POST("order/v1/signIn")
//    Call<ResultBean<UserBean>> signIn(
//            @Field("account") String account,
//            @Field("password") String password);

    @Multipart
    @POST("android/v1/food/Upload")
    Call<ResultBean> upload(
            @Part MultipartBody.Part file);

}

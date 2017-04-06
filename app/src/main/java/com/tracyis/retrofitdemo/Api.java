package com.tracyis.retrofitdemo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Trasys on 2017/4/6.
 */
public interface Api {

    @Headers("userid:20428")
    @POST("userinfo")
    Call<Object> getPrivateData();

    @GET("home")
    Call<Object> getPublicData();

    @POST("login?username=test&password=test")
    Call<Object> login();

    @Headers("userid:20428")
    @POST("logout")
    Call<Object> logout();
}

package com.tracyis.retrofitdemo.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tracyis.retrofitdemo.Api;
import com.tracyis.retrofitdemo.Constants;
import com.tracyis.retrofitdemo.persistentcookiejar.PersistentCookieJar;
import com.tracyis.retrofitdemo.persistentcookiejar.cache.SetCookieCache;
import com.tracyis.retrofitdemo.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Trasys on 2017/4/6.
 */
public class MyRetrofit {

    private static MyRetrofit sHeiMaRetrofit;

    private static final String CACHE_CONTROL = "Cache-Control";

    private static final int DEFAULT_CACHE_SIZE = 5 * 1024 * 1024;

    private Api mApi;


    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    public static MyRetrofit getInstance() {
        if (sHeiMaRetrofit == null) {
            synchronized (MyRetrofit.class) {
                if (sHeiMaRetrofit == null) {
                    sHeiMaRetrofit = new MyRetrofit();
                }
            }
        }
        return sHeiMaRetrofit;
    }

    public Api getApi() {
        return mApi;
    }

    public void init(Context context) {

        PersistentCookieJar cookieJar = new PersistentCookieJar(
                new SetCookieCache(),new SharedPrefsCookiePersistor(context));

        String directoryPath = context.getCacheDir().getAbsolutePath() + "/responses";
        File directory = new File(directoryPath);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(new Cache(directory, DEFAULT_CACHE_SIZE))
                .addInterceptor(new LoggingInterceptor())
                .cookieJar(cookieJar)
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        mApi = retrofit.create(Api.class);
    }

    /**
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            CacheControl.Builder builder = new CacheControl.Builder().maxAge(10, TimeUnit.MINUTES);
            return originalResponse.newBuilder()
                    .header(CACHE_CONTROL, builder.build().toString())
                    .build();
        }
    };
}

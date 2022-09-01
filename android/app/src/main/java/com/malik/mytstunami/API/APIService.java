package com.malik.mytstunami.API;

import android.content.Context;

import com.malik.mytstunami.BuildConfig;
import com.malik.mytstunami.config.Constants;
import com.malik.mytstunami.model.GempaModel;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface APIService {
    @GET("api/gempa")
    Call<GempaModel> dataGempa();

    class Factory{
        public static APIService create(Context mContext){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.connectTimeout(20, TimeUnit.SECONDS);
            builder.writeTimeout(20, TimeUnit.SECONDS);
            builder.addInterceptor(new NetworkConnectionInterceptor(mContext));
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            if(BuildConfig.DEBUG){
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            }else {
                logging.setLevel(HttpLoggingInterceptor.Level.NONE);
            }

            OkHttpClient client = builder.addInterceptor(logging).build();
//            OkHttpClient client = builder.build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            return retrofit.create(APIService.class);
        }
    }
}

package com.example.mvvmsampleproject.api

import com.example.mvvmsampleproject.data.model.IntroApiResponse
import com.example.mvvmsampleproject.util.DefineConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    //생성자 로 레트로핏 셋팅
    companion object {

        fun create(): ApiService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val gson = GsonBuilder() .setLenient() .create()

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(DefineConfig.DOMAIN)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }
    }

    /**
     * Response<ResponseBody> 리턴 하는데
     * 보통은 예시) Response<BaseApiDto> 이런식으로 ModelClass 를 넣어야하지만
     * Kt API 에서 값을 줄떄 암호화 상태로 값을 줘서 ResponseBody 를 사용해 String 으로 값을 뽑은후 암호화를 풀어서 ModelClass 에서 set 형식으로 넣어야함
     * **/
    @FormUrlEncoded
    @POST("/api/v5/a_Intro.json")
    suspend fun onHttpIntroApi(@Field("market") market : String, @Field("deviceWidth") deviceWidth : String, @Field("deviceHeight") deviceHeight : String): Response<ResponseBody>

}
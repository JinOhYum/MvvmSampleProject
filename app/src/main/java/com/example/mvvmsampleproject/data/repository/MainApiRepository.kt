package com.example.mvvmsampleproject.data.repository

import android.webkit.JavascriptInterface
import com.example.mvvmsampleproject.api.ApiService
import com.example.mvvmsampleproject.api.JsBridge
import com.example.mvvmsampleproject.data.model.IntroApiResponse
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

//레포시토리 패턴
class MainApiRepository @Inject constructor(private val apiService: ApiService ) {

    //suspend 비동기로 처리해야됨
    suspend fun onHttpIntroApi(market : String , deviceWidth : String , deviceHeight : String) : Response<ResponseBody> {

        return apiService.onHttpIntroApi(market, deviceWidth, deviceHeight)
    }


}
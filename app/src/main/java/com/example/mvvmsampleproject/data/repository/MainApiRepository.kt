package com.example.mvvmsampleproject.data.repository

import android.webkit.JavascriptInterface
import com.example.mvvmsampleproject.api.ApiService
import com.example.mvvmsampleproject.api.JsBridge
import com.example.mvvmsampleproject.data.model.IntroApiResponse
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

//Repository 패턴
/**
 * MainActivity 에서 사용되는 API는 여기서 관리
 * **/
class MainApiRepository @Inject constructor(private val apiService: ApiService){

    suspend fun onHttpIntroApi(market : String , deviceWidth : String , deviceHeight : String) : Response<ResponseBody> {

        return apiService.onHttpIntroApi(market, deviceWidth, deviceHeight)
    }


}
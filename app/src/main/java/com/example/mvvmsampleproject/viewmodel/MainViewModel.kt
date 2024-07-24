package com.example.mvvmsampleproject.viewmodel

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmsampleproject.api.JsBridge
import com.example.mvvmsampleproject.data.model.IntroApiResponse
import com.example.mvvmsampleproject.data.model.OpenLoginViewJsDto
import com.example.mvvmsampleproject.data.repository.MainApiRepository
import com.google.gson.Gson
import com.ktshow.cs.ndkaes.NdkAes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * MainActivity 에서 이벤트 요청 처리 및 반환 , 서버통신에서 받은 데이터 처리
 * **/
@HiltViewModel //@HiltViewModel Hilt ViewModel
class MainViewModel @Inject constructor(private val repository: MainApiRepository, var jsBridge: JsBridge) : ViewModel(),JsBridge.JsCallback  {

    private val gson : Gson = Gson()

    private val _introApiResponse = MutableLiveData<IntroApiResponse>()
    val introApiResponse : LiveData<IntroApiResponse> get() = _introApiResponse

    private val _openLoginViewJsDto = MutableLiveData<OpenLoginViewJsDto>()
    val openLoginViewJsDto : LiveData<OpenLoginViewJsDto> get() = _openLoginViewJsDto


    init {
        jsBridge.setCallBack(this)
    }

    /**
     * 자바스크립트 브릿지 호출 처리
     * 자바스크립트 브릿지 에서 호출되서 처리되는 대부분 동작들은 UI와 밀접한 관계가 있어 Repository 에서 관리가 아닌 ViewModel 에서 관리
     * **/
    override fun onRequestFromJs(requestId: Int, tag: String?, vararg params: String?) {
        when (requestId) {
            jsBridge.JS_REQ_TEST -> {
                // 웹뷰에서 들어오는 브릿지에 대한 처리
                Log.d("여기","왔나22")

                onJsOpenLoginView(params.toString())
            }
        }
    }

    private fun onJsOpenLoginView(vararg params: String) {
        val decryptedData = NdkAes.decrypt(params[0], true)
        val dto: OpenLoginViewJsDto = gson.fromJson(decryptedData, OpenLoginViewJsDto::class.java)
        _openLoginViewJsDto.postValue(dto)

    }

    /***
     * Retrofit2 API 서버통신
     * **/
    fun onHttpSetAppCtnApi(){
        viewModelScope.launch {
            val response = repository.onHttpIntroApi("02","1440","2851")

            when (response.isSuccessful) {
                true ->{
                    Log.e("여기 MainViewModel","성공 ")
                    val encryptedData = response.body()?.string()
                    val decryptedData = NdkAes.decrypt(encryptedData, true)

                    // Gson을 사용하여 JSON을 IntroApiResponse 객체로 파싱
                    val introApiResponse = gson.fromJson(decryptedData, IntroApiResponse::class.java)
//
//                    // LiveData에 값을 설정
                    _introApiResponse.postValue(introApiResponse)
                }

                false ->{
                    Log.e("여기 MainViewModel","실패 ")
                }
            }

        }
    }


}
package com.example.mvvmsampleproject.viewmodel

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvmsampleproject.api.JsBridge
import com.example.mvvmsampleproject.data.model.IntroApiResponse
import com.example.mvvmsampleproject.data.model.MainActivityData
import com.example.mvvmsampleproject.data.model.MainBottomData
import com.example.mvvmsampleproject.data.model.OpenLoginViewJsDto
import com.example.mvvmsampleproject.data.repository.MainApiRepository
import com.example.mvvmsampleproject.util.Constant
import com.example.mvvmsampleproject.util.DefineConfig
import com.example.mvvmsampleproject.util.PreferenceUtil
import com.google.gson.Gson
import com.ktshow.cs.ndkaes.NdkAes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull
import javax.inject.Inject

/**
 * MainActivity 에서 이벤트 요청 처리 및 반환 , 서버통신에서 받은 데이터 처리
 * **/
@HiltViewModel //@HiltViewModel Hilt ViewModel
class MainViewModel @Inject constructor(private val repository: MainApiRepository,
                                        private val preferenceUtil: PreferenceUtil,
                                        val jsBridge: JsBridge) : BaseViewModel(),JsBridge.JsCallback  {

    private val gson : Gson = Gson()

    //인트로 API 모델
    private val _introApiResponse = MutableLiveData<IntroApiResponse>()
    val introApiResponse : LiveData<IntroApiResponse> get() = _introApiResponse

    private val _openLoginViewJsDto = MutableLiveData<OpenLoginViewJsDto>()
    val openLoginViewJsDto : LiveData<OpenLoginViewJsDto> get() = _openLoginViewJsDto

    //ViewModel 하단 탭바 관리 데이터
    private var _mainBottomData: ArrayList<MainBottomData> = ArrayList<MainBottomData>()
    val mainBottomData: ArrayList<MainBottomData> get() = _mainBottomData

    //하단 탭바 현재 position 관리 데이터
    private var _mainActivityData: MutableLiveData<MainActivityData> = MutableLiveData<MainActivityData>()
    val mainActivityData: LiveData<MainActivityData> get() = _mainActivityData

    init {
        jsBridge.setCallBack(this)
        _mainActivityData.value = MainActivityData(2, DefineConfig.URL_MAIN, Constant.MY_TAB , false)
    }

    /**
     * 자바스크립트 브릿지 호출 처리
     * 자바스크립트 브릿지 에서 호출되서 처리되는 대부분 동작들은 UI와 밀접한 관계가 있어 Repository 에서 관리가 아닌 ViewModel 에서 관리
     * **/
    override fun onRequestFromJs(requestId: Int, tag: String?, vararg params: String?) {
        viewModelScope.launch {
            when (requestId) {
                jsBridge.JS_REQ_TEST -> {
                    // 웹뷰에서 들어오는 브릿지에 대한 처리
//                    onJsOpenLoginView(params.toString())
                }
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
    fun onHttpIntroApi(){
        viewModelScope.launch {
            val response = repository.onHttpIntroApi("02","1440","2851")

            when (response.isSuccessful) {
                true ->{
                    Log.e("여기 MainViewModel","성공 ")
                    val encryptedData = response.body()?.string()
                    val decryptedData = NdkAes.decrypt(encryptedData, true)

                    // Gson을 사용하여 JSON을 IntroApiResponse 객체로 파싱
                    val introApiResponse = gson.fromJson(decryptedData, IntroApiResponse::class.java)

//                    // LiveData에 값을 설정
                    _introApiResponse.postValue(introApiResponse)

                }

                false ->{
                    Log.e("여기 MainViewModel","실패 ")
                }
            }

        }
    }


    //하단탭바 셋팅
    fun setBottomData(position : Int ,imageView : ImageView , textView: TextView ,
                      @RawRes gifNameRight : Int , @RawRes gifNameDark : Int , @DrawableRes baseImageName : Int){
        viewModelScope.launch {
            _mainBottomData.add(MainBottomData(position, imageView, textView, gifNameRight, gifNameDark, baseImageName)
            )
        }
    }

    //메인에서 사용하는 유틸 데이터
    fun setMainActivityData(mainActivityData: MainActivityData){
        _mainActivityData.value = mainActivityData
    }

    //Url 변동 여부
    fun setMainActivityUrl(url: String){
        _mainActivityData.value!!.url = url
        _mainActivityData.postValue(_mainActivityData.value)
    }

}
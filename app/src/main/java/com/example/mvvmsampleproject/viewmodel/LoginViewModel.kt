package com.example.mvvmsampleproject.viewmodel

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmsampleproject.api.JsBridge
import com.example.mvvmsampleproject.data.manager.JsCallbackCtrl
import com.example.mvvmsampleproject.data.model.CloseWindowJsDto
import com.example.mvvmsampleproject.util.DefineCommon
import com.example.mvvmsampleproject.util.DefineField
import com.example.mvvmsampleproject.util.DefineUrl
import com.example.mvvmsampleproject.util.LogUtil
import com.example.mvvmsampleproject.util.PreferenceUtil
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(val jsBridge: JsBridge) : ViewModel() , JsBridge.JsCallback{


    private val jsCallbackCtrl : JsCallbackCtrl = JsCallbackCtrl

    //LoginActivity 진입전 머물고 있던 Url 값
    private var _loginAfterUrl : String  = ""
    val loginAfterUrl : String get() = _loginAfterUrl

    //onActivityResult 값 에서 사용 하던 값 기존 과 다르게 따로 셋팅 해야함
    private var _requestCode : Int  = DefineCommon.REQ_ERROR
    val requestCode : Int get() = _requestCode

    //onActivityResult 에서 사용하는 결과값
    private var _resultCode : Int  = Activity.RESULT_CANCELED
    val resultCode : Int get() = _resultCode

    //뒤로가기 , 액티비티 종료 JS 브릿지 Model 값 (기존 마이케이티앱 로직)
    private var _closeWindowJsDto : MutableLiveData<CloseWindowJsDto>  = MutableLiveData()
    val closeWindowJsDto : LiveData<CloseWindowJsDto> get() = _closeWindowJsDto

    init {
        jsBridge.setCallBack(this)
    }

    /**
     * JS브릿지 받은값 처리 (기존 마이케이티앱 로직)
     * viewModelScope 로 감싸 안정성 있게 데이터 처리
     * */
    override fun onRequestFromJs(requestId: Int, tag: String?, vararg params: String?) {
        viewModelScope.launch {
            when (requestId) {
                jsBridge.JS_REQ_CLOSE_ACTIVITY -> {
                    LogUtil.d("여기 JS_REQ_CLOSE_ACTIVITY")
                    // 웹뷰에서 들어오는 브릿지에 대한 처리
                    onJsCloseWindow(params.toString())
                }
            }
        }
    }

    /**
     * onJsCloseWindow : 액티비티 종료 (기존 마이케이티앱 동일 로직)
     */
    private fun onJsCloseWindow(vararg params: String) {
        if (params.isEmpty() || TextUtils.isEmpty(params[0])) {
            _closeWindowJsDto.value = CloseWindowJsDto("")
            return
        }
        jsCallbackCtrl.parseJson(params[0], CloseWindowJsDto::class.java).let {
            if (TextUtils.isEmpty(it?.reloadType)) {
                _closeWindowJsDto.value = CloseWindowJsDto("")
                return
            }

            if ("MAIN" == it?.reloadType) {
                _closeWindowJsDto.value = it
            }
        }

    }

    /**
     * Main에서 받은 intent 값 LoginViewModel 에 적재
     * **/
    fun setIntentData(intent : Intent){
        _loginAfterUrl = intent.getStringExtra(DefineField.PARAM_LOGIN_AFTER_URL).toString()
        _requestCode = intent.getIntExtra("requestCode", DefineCommon.REQ_ERROR)
    }

}
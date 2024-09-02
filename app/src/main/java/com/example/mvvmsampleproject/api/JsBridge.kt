package com.example.mvvmsampleproject.api

import android.webkit.JavascriptInterface
import com.example.mvvmsampleproject.util.LogUtil

class JsBridge{


    val JS_REQ_TEST = 1
    val JS_REQ_CLOSE_ACTIVITY = 15

    private var callback: JsCallback? = null


    private val TAG = "JsBridge_"

    interface JsCallback {
        fun onRequestFromJs(requestId: Int, tag: String?, vararg params: String?)
    }

    fun setCallBack(callback: JsCallback){
        this.callback = callback
    }

    private fun requestCallback(requestId: Int, vararg params: String?) {
        if(callback != null){
            callback?.onRequestFromJs(requestId, TAG, *params)
        }
    }

    @JavascriptInterface
    fun doTest() {
        /**
         * 콘솔 테스트 명령어
         * window.ktCsNative.doTest();
         * **/
        LogUtil.d("doTest")
        requestCallback(JS_REQ_TEST)
    }

    // 웹에서 앱 화면 닫기 요청
    @JavascriptInterface
    fun closeWindow() {
        LogUtil.i(TAG + "closeWindow : "  )
        requestCallback(JS_REQ_CLOSE_ACTIVITY)
    }
}
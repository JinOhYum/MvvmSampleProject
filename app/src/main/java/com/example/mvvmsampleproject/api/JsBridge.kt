package com.example.mvvmsampleproject.api

import android.util.Log
import android.webkit.JavascriptInterface
import com.example.mvvmsampleproject.util.LogUtil

class JsBridge{

    private var callback: JsCallback? = null
    private val tag: String = "JsBridge_"
    val JS_REQ_TEST = 1

    interface JsCallback {
        fun onRequestFromJs(requestId: Int, tag: String?, vararg params: String?)
    }

    fun setCallBack(callback: JsCallback){
        this.callback = callback
    }

    private fun requestCallback(requestId: Int, vararg params: String?) {
        if(callback != null){
            callback?.onRequestFromJs(requestId, tag, *params)
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

}
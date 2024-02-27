package com.example.mvvmsampleproject.api

import android.webkit.JavascriptInterface

class JsBridge (private val callback: JsCallback, private val tag: String = "JsBridge_"){

    interface JsCallback {
        fun onRequestFromJs(requestId: Int, tag: String?, vararg params: String?)
    }

//    private lateinit var callback: JsCallback
//    private var tag = "JsBridge_"


    val JS_REQ_DO_MRKTNG_TOAST_POPUP = 62

    private fun requestCallback(requestId: Int, vararg params: String?) {
        callback.onRequestFromJs(requestId, tag, *params)
    }

    @JavascriptInterface
    fun doMrktngToastPopupOpen(json: String) {
        requestCallback(JS_REQ_DO_MRKTNG_TOAST_POPUP, json)
    }

}
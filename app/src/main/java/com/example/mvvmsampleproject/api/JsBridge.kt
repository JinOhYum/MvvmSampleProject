package com.example.mvvmsampleproject.api

import android.webkit.JavascriptInterface

class JsBridge {

    interface JsCallback {
        fun onRequestFromJs(requestId: Int, tag: String?, vararg params: String?)
    }

    private lateinit var callback: JsCallback
    private var tag = "JsBridge_"

    constructor(callback: JsCallback?) {
        this.callback = callback!!
    }

    constructor(callback: JsCallback , tag : String) {
        this.callback = callback
        this.tag = tag
    }


    val JS_REQ_GET_BADGE_CNT = 1

    private fun requestCallback(requestId: Int, vararg params: String?) {
        callback.onRequestFromJs(requestId, tag, *params)
    }

    // 배지 개수 조회
    @JavascriptInterface
    fun getBadgeCnt() {
        requestCallback(JS_REQ_GET_BADGE_CNT)
    }

}
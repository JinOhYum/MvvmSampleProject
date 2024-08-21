package com.example.mvvmsampleproject.util

import android.text.TextUtils
import android.webkit.WebBackForwardList

object CommonUtil {

    /**
     * checkGoBackForward : 마이케이티 뒤로가기
     */
    fun checkGoBackForward(curUrl: String): Boolean {
        if (TextUtils.isEmpty(curUrl)) return false
        val urls = DefineUrl.URL_GO_BACK_FORWARD
        for (url in urls) {
            if (curUrl.contains(url!!)) {
                return true
            }
        }
        return false
    }

    /**
     * getLastHistory : 마지막 페이지의 url 조회
     */
    fun getLastHistory(webBackForwardList: WebBackForwardList): String {
        val list: WebBackForwardList = webBackForwardList
        if (list.size <= 1) return ""
        // 현재 인덱스
        val idx = list.currentIndex
        return if (idx < 1) "" else list.getItemAtIndex(idx - 1).url
        // 인덱스로 URL 조회
    }
    /**
     * canGoBack : 뒤로가기 가능여부 확인
     */
    fun getCanGoBack(canGoBack : Boolean): Boolean {
        var hasBack = false
        hasBack = canGoBack
        return hasBack
    }
}
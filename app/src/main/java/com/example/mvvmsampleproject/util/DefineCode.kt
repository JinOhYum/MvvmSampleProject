package com.example.mvvmsampleproject.util

object DefineCode {
    const val API_SUCCESS_CODE = "SETTANDR01000" // 정상

    const val API_ERROR_CODE = "SETTANDR01001" // 정상


    // 웹뷰
    const val VIEW_MAIN = 0
    const val VIEW_MY = 1
    const val VIEW_BENEFITS = 2
    const val VIEW_MAIN_DETAIL = 3

    // 플로팅 버튼
    const val VIEW_FAB_SHOW = 1
    const val VIEW_FAB_HIDE = 2
    const val VIEW_FAB_ACTIVE = 3

    // 위젯
    const val WIDGET_TYPE_22 = 0
    const val WIDGET_TYPE_31 = 1
    const val WIDGET_TYPE_42 = 2

    // 웹뷰 URL 타입
    const val URL_TYPE_NETWORK_DISABLE = 0
    const val URL_TYPE_LOGIN = 1
    const val URL_TYPE_HTTP = 2
    const val URL_TYPE_TEL = 3
    const val URL_TYPE_MAILTO = 4
    const val URL_TYPE_MARKET = 5
    const val URL_TYPE_SMS = 6
    const val URL_TYPE_AUDIO = 7
    const val URL_TYPE_VIDEO = 8
    const val URL_TYPE_INTENT = 9
    const val URL_TYPE_SORI_ASP = 10
    const val URL_TYPE_CSTORE = 11
    const val URL_TYPE_ISP_APP = 12
    const val URL_TYPE_BELLING = 13
    const val URL_TYPE_MOVIE_TICKET = 14

    // 위젯/알림바 새로고침 주기 코드
    const val REFRESH_PERIOD_CONST = 9999 // 작업공지

    const val REFRESH_PERIOD_M = 0 // 수동

    const val REFRESH_PERIOD_12 = 1 // 12시간


    // 로그인 웹뷰 태그
    const val LOGIN_WEBVIEW_TAG = "LOGIN_WEBVIEW_TAG"

    const val UPDATE_INTERVAL_MS = 1000 // 1초

    const val FASTEST_UPDATE_INTERVAL_MS = 500 // 0.5초

    const val REQUEST_LOCATION_PERMISSION = 10001
    const val REQUEST_LOCATION_STATUS = 10002
    const val REQUEST_WIFI_STATUS = 10003


    //    202306-dynatrace costom log
    const val apiToken = "Api-Token dt0c01.EAWGRCVMVS6W2W6HUX2YL4WN.7YCDOABJBLY3V7H3DSUVLKBSKUDAF5O7TVCNKI2ZT4HPIF3Y5I226ZVBEJ3N5FE4"
}
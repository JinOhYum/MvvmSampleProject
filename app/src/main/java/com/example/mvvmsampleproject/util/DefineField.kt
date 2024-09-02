package com.example.mvvmsampleproject.util


object DefineField {
    const val PARAM_OS_TYPE = "os" // 단말기 유형
    const val PARAM_APP_VER = "appver" // 버전
    const val PARAM_DEVICE_MODEL = "model" // 단말기 모델명
    const val PARAM_OS_VER = "osver" // OS 버전
    const val PARAM_DEVICE_BRAND = "maker" // 단말기 제조사
    const val PARAM_VENDOR = "vendor" // 통신사
    const val PARAM_GUID = "guid" // FCM Token
    const val PARAM_IS_WIDGET = "isWidget" // 위젯 여부
    const val PARAM_DEVICE_ID = "deviceId" // 단말기 device ID
    const val PARAM_ANDROID_ID = "deviceUuid" // 단말기 UUID ( Q OS 대응하며 ANDROID_ID를 uuid 로 사용 )
    const val PARAM_LANDING_URL = "param_landing_url" // 메인 페이지 실행 시 로딩할 URL 정보
    const val PARAM_URL_TYPE = "param_url_type" // URL 유형
    const val PARAM_LOGIN_AFTER_URL = "param_login_after_url" // 로그인 후 메인 Activity 갱신 URL 설정
    const val PARAM_LOGIN_AFTER_ACTIVITY = "param_login_after_activity" // 로그인 후 실행할 Activity
    const val PARAM_LOGIN_FROM_CLASS = "param_login_from_class" // 로그인 Activity 실행한 클래스명
    const val PARAM_LOGIN_URL = "param_login_url" // 로그인 Activity 로딩 URL
    const val PARAM_LOGIN_AUTO = "param_login_auto" // 로그인 Activity 자동 로그인 여부
    const val PARAM_IS_INSTANT = "param_is_instant" // 브로드캐스트 메시지 수신 시 바로 실행 여부
    const val PARAM_RELOAD_URL = "param_reload_url" // 갱신 URL
    const val PARAM_WIDGET_INFO_RESULT = "param_widget_info_result"
    const val PARAM_WIDGET_INFO_MSG = "param_widget_info_msg"
    const val PARAM_WIDGET_INFO_DATA = "param_widget_info_data"
    const val PARAM_WIDGET_INFO_NOTICE_DATA = "param_widget_info_notice_data"
    const val PARAM_WIDGET_INFO_PREV_STATUS = "param_widget_info_prev_status"
    const val PARAM_WIDGET_CLICK = "param_widget_click"
    const val PARAM_WIDGET_CLICK_ADOBE = "param_widget_click_adobe"

    //2307-커버스크린
    const val PARAM_COVER_WIDGET_INFO_DATA = "param_cover_widget_info_data"
    const val PARAM_LINK_URL = "param_link_url"
    const val PARAM_LINK_TITLE = "param_link_title"

    // [DR-2019-63684] android 위젯 가독성 개선
    const val PARAM_WIDGET_BANNER_CLICK_STAT = "param_widget_banner_click_stat" // 위젯 > 배너통계 클릭액션
    const val PARAM_WIDGET_BANNER_PAGE_STAT = "param_widget_banner_page_stat" // 위젯 > 배너통계 페이지이동
    const val PARAM_BANNER_ADD_FEE = "param_banner_add_fee" // 위젯 > 배너링크 페이지의 과금 여부
    const val PARAM_WIDGET_TYPE = "param_widget_type" // 위젯 > 위젯 스타일 설정 화면으로 이동 , 위젯 유형
    const val PARAM_WIDGET_FEE_TYPE = "param_widget_fee_type"
    const val PARAM_IS_FROM_WIDGET =
        "param_is_from_widget" // 위젯 > 위젯 스타일 설정 화면으로 이동 , 위젯에서 넘어왔는지 여부
    const val PARAM_IMAGE_URL = "param_image_url"
    const val PARAM_IMAGE_DESC = "param_image_desc"
    const val PARAM_IS_MOVE_MAIN = "param_is_move_main"

    // [DR-2018-68111] 마이케이티 앱 챗봇 상세화면 내 홈버튼 추가
    const val PARAM_IS_HOME_BUTTON = "PARAM_IS_HOME_BUTTON"

    // [DR-2019-20426] 흔들어서 멤버십 호출
    const val PARAM_MEMBERSHIP_CARD_NO = "PARAM_MEMBERSHIP_CARD_NO"
    const val PARAM_MEMBERSHIP_POINT = "PARAM_MEMBERSHIP_POINT"
    const val PARAM_MEMBERSHIP_GRADE = "PARAM_MEMBERSHIP_GRADE"
    const val PARAM_MEMBERSHIP_GRADE_COLOR = "PARAM_MEMBERSHIP_GRADE_COLOR"

    // [DR-2020-24293] 안드로이드 push 링크 클릭 시 일부 파라미터 손실 개선
    const val PARAM_PUSH_URL = "param_push_url"
    const val PARAM_PUSH_WINDOWS = "param_push_windows"
    const val PARAM_CHANGE_THEME_MODE = "param_change_theme_mode"
    const val GET_NEW_INTENT_DEEP_LINK_DATA = "GET_NEW_INTENT_DEEP_LINK_DATA"

    // KT 멤버십 > 내 통장 결제
    const val PARAM_MOVIE_TICKET_BANK_ACCOUNT_LIST = "param_movie_ticket_bank_account_list"
    const val PARAM_MOVIE_TICKET_AMOUNT = "param_movie_ticket_amount"
    const val PARAM_MOVIE_TICKET_SMART5KEY = "param_movie_ticket_smart5key"
    const val PARAM_MOVIE_TICKET_RETURN_CD = "param_movie_ticket_return_cd"
    const val PARAM_PAYMENT_AUTH_RESULT = "param_payment_auth_result"
    const val PARAM_PAYMENT_AUTH_SETTING = "param_payment_auth_setting"

    // 202303 nsr - barcodeActivity 멤버십 바코드 x버튼 클릭시 뒤로가기 할수 있는지 판단
    const val PARAM_CAN_GO_BACK = "can_go_back"
}

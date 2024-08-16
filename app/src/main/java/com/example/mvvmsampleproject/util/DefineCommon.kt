package com.example.mvvmsampleproject.util

object DefineCommon {

    // [DR-2018-05200] Android 위젯 과다호출 수정 및 방어 로직 추가
    const val DEFAULT_WIDGET_REFRESH_MIN = -1 // 위젯 데이터 갱신 주기

    const val DEFAULT_WIDGET_NOTI_CHECK_MIN = -1 // 위젯 작업 공지 갱신 주기


    const val DIALOG_ID_BASE = 1000
    const val API_ID_BASE = 2000

    // 팝업 ID
    const val DIALOG_ID_LOGIN = DIALOG_ID_BASE + 1
    const val DIALOG_ID_LOGOUT = DIALOG_ID_BASE + 2
    const val DIALOG_ID_FINISH_APP = DIALOG_ID_BASE + 3
    const val DIALOG_ID_MOVE_CHANGE_PWD = DIALOG_ID_BASE + 4
    const val DIALOG_ID_PERMISSION_SHOW = DIALOG_ID_BASE + 5
    const val DIALOG_ID_PERMISSION_SET = DIALOG_ID_BASE + 6
    const val DIALOG_ID_NET_DISABLED = DIALOG_ID_BASE + 7
    const val DIALOG_ID_MARKETING_PUSH = DIALOG_ID_BASE + 8
    const val DIALOG_ID_CHARGE = DIALOG_ID_BASE + 9
    const val DIALOG_ID_SIMPLE_SEARCH_AGREE = DIALOG_ID_BASE + 10
    const val DIALOG_ID_SIMPLE_LOGIN = DIALOG_ID_BASE + 12
    const val DIALOG_ID_SIMPLE_LOGOUT = DIALOG_ID_BASE + 13

    // activity request code
    private const val REQ_BASE = 100
    const val REQ_ACT_PERMISSION_AGREE = REQ_BASE + 1
    const val REQ_ACT_INIT_SETTING = REQ_BASE + 2
    const val REQ_ACT_LOGIN = REQ_BASE + 3
    const val REQ_ACT_HOME_CARD = REQ_BASE + 4
    const val REQ_ACT_SETTING = REQ_BASE + 5
    const val REQ_PERMISSION_APP = REQ_BASE + 6
    const val REQ_PERMISSION_AUDIO = REQ_BASE + 8
    const val REQ_PERMISSION_WEB = REQ_BASE + 9
    const val REQ_ACT_VOICETALK = REQ_BASE + 10
    const val REQ_ACT_BARCODE_OCR = REQ_BASE + 11
    const val REQ_PERMISSION_OCR = REQ_BASE + 12
    const val REQ_PERMISSION_BARCODE = REQ_BASE + 13
    const val REQ_ACT_ONBOARDING = REQ_BASE + 14
    const val REQ_PERMISSION_OCR_IDCARD = REQ_BASE + 15 // [DR-2018-63013] OCR 기능 개발(신규)

    const val REQ_ACT_MEMBERSHIP = REQ_BASE + 16 // [DR-2019-20426] 흔들어서 멤버십 호출

    const val REQ_PERMISSION_QR = REQ_BASE + 17
    const val REQ_ACT_SIMPLE_TUTORIAL = REQ_BASE + 18
    const val REQ_PERMISSION_CONTACTS = REQ_BASE + 19
    const val REQ_ACT_BANK_ACCOUNT_CHOICE = REQ_BASE + 20
    const val REQ_ACT_PATMENT_AUTH = REQ_BASE + 21
    const val REQ_ACT_PATMENT_AUTH_RESET = REQ_BASE + 22
    const val REQ_ACT_FINGERPRINT = REQ_BASE + 23
    const val REQ_PERMISSION_TARGET_30 = REQ_BASE + 21
    const val REQ_SYSTEM_SETTING_PERMISSION = REQ_BASE + 22
    const val REQ_POST_NOTIFICATION_PERMISSION = REQ_BASE + 23
    const val BLUETOOTH_ADVERTISE = REQ_BASE + 24
    // [DR-2019-36940] 위치권한 필수 선택 변경
    // [DR-2019-52573] 위치 권한 제거
//    public static final int REQ_PERMISSION_GPS          = REQ_BASE + 17;
//    public static final int REQ_ACT_LOCATION_SETTING    = REQ_BASE + 18;

    // [DR-2019-36940] 위치권한 필수 선택 변경
    // [DR-2019-52573] 위치 권한 제거
    //    public static final int REQ_PERMISSION_GPS          = REQ_BASE + 17;
    //    public static final int REQ_ACT_LOCATION_SETTING    = REQ_BASE + 18;
    // [DR-2019-56383] 알림상세 확인후 뱃지 제거
    const val REQ_ACT_ALARM = REQ_BASE + 19

    // [DR-2019-62540] 챗봇 PASS 인증 개발
    const val REQ_ACT_SUBWEBVIEW = REQ_BASE + 20

    // [DR-2020-53286] 쇼미 서비스 설정시 파일 업로드
    const val REQ_FILE_UPLOAD_APP = REQ_BASE + 30

    // 앱 잠금 요청 코드
    const val REQ_INTENT_ONOFF = REQ_BASE + 101 // 암호 잠금 사용/미사용 설정 요청코드

    const val REQ_INTENT_CHANGE = REQ_BASE + 102 // 암호 변경 설정 요청코드

    const val REQ_INTENT_CHECK = REQ_BASE + 103 // 암호 검증 모드 요청


    //kcy1000-202206 - CallGate  추가.
    const val REQUEST_CALL_GATE_READ_PHONE_STATE = REQ_BASE + 200
    const val REQUEST_CALL_GATE_DRAW_OVERLAY = REQ_BASE + 201

    // 설정 - 회원탈퇴
    const val REQUEST_MEMBERSHIP_WITHDRAWAL = REQ_BASE + 202

    //kcy1000-202210 - 알림권한
    const val REQUEST_POST_NOTIFICATION_PERMISSION = REQ_BASE + 203

    const val REQUEST_POST_NOTI_BAR_PERMISSION = REQ_BASE + 204
    const val REQUEST_POST_NOTI_PUSH_PERMISSION = REQ_BASE + 205
    const val REQUEST_POST_NOTI_ARS_PERMISSION = REQ_BASE + 206
    const val REQUEST_POST_NOTIFICATION_PERMISSION_SETTING_INIT = REQ_BASE + 207
    const val REQUEST_POST_NOTIFICATION_PERMISSION_NORMAR_LOGIN = REQ_BASE + 208

    // 챗봇 호출
    const val REQ_ACT_CHATBOT = REQ_BASE + 209
    const val REQ_ACT_PERMISSION_NOTIFICATION = REQ_BASE + 210

    // 알람 및 리마인더 권한 요청.
    const val REQUEST_SCHEDULE_EXACT_ALARM = REQ_BASE + 211

    //LoginActivity 에서 goMain 브릿지 호출 되었을때 사용할 resultCode
    const val REQ_LOGIN_GO_MAIN = REQ_BASE + 211

    // 2406-스마트안심 sdk
    const val REQ_ACT_SAFE_SYSTEM_ALERT_WINDOW_1 = 3701
    const val REQ_ACT_SAFE_SYSTEM_ALERT_WINDOW_2 = 4901
    const val REQ_ACT_SAFE_TALK_BACK = 6001
    const val REQ_ACT_SAFE_BATTERY_SAVING_MODE = 7001


    // 시스템 인텐트 필터
    const val ACTION_SIM_STATE_CHANGED = "android.intent.action.SIM_STATE_CHANGED"

    // 알림바 인텐트 필터
    // 시스템 갱신
    const val ACTION_NOTI_STOP = "com.ktshow.cs.ACTION_NOTI_STOP"
    const val ACTION_NOTI_VISIBILITY = "com.ktshow.cs.ACTION_VISIBILITY"
    const val ACTION_NOTI_SETTING_CHANGE = "com.ktshow.cs.ACTION_NOTI_SETTING_CHANGE"
    const val ACTION_NOTI_REFRESH = "com.ktshow.cs.ACTION_NOTI_REFRESH"
    const val ACTION_NOTI_RESTART = "com.ktshow.cs.ACTION_NOTI_RESTART"
    const val ACTION_NOTI_LOGIN = "com.ktshow.cs.ACTION_NOTI_LOGIN"
    const val ACTION_NOTI_LOGIN_CHANGE = "com.ktshow.cs.ACTION_NOTI_LOGIN_CHANGE"
    const val ACTION_NOTI_LOGIN_FAIL =
        "com.ktshow.cs.ACTION_NOTI_LOGIN_FAIL" // 앱에서 자동로그인 실패시 알림바와 동기화

    const val ACTION_NOTI_CTN_CHANGE = "com.ktshow.cs.ACTION_NOTI_CTN_CHANGE"

    // 사용자 액션
    const val ACTION_NOTI_LOGO = "com.ktshow.cs.ACTION_NOTI_LOGO"
    const val ACTION_NOTI_MAIN = "com.ktshow.cs.ACTION_NOTI_MAIN"

    const val ACTION_BATTERY_SAVER = "com.ktshow.cs.ACTION_BATTERY_SAVER"
    const val ACTION_DATA_SAVER = "com.ktshow.cs.ACTION_DATA_SAVER"
    const val ACTION_NOTI_REFRESH_REQUEST =
        "com.ktshow.cs.ACTION_NOTI_REFRESH_REQUEST" // 알림바 새로고침 주기 (주기적 새로고침이 시작되도록 요청한다)


    // 위젯 인텐트 필터
    const val ACTION_WIDGET_INFO_UPDATE = "com.ktshow.cs.ACTION_WIDGET_INFO_UPDATE"
    const val ACTION_WIDGET_BARCODE_UPDATE = "com.ktshow.cs.ACTION_WIDGET_BARCODE_UPDATE"

    const val ACTION_WIDGET_CLICK = "com.ktshow.cs.ACTION_WIDGET_CLICK"
    const val ACTION_WIDGET_LOGIN_CHANGE = "com.ktshow.cs.ACTION_WIDGET_LOGIN_CHANGE"
    const val ACTION_WIDGET_CTN_CHANGE = "com.ktshow.cs.ACTION_WIDGET_CTN_CHANGE"
    const val ACTION_WIDGET_TIME_TICK = "com.ktshow.cs.ACTION_WIDGET_TIME_TICK"
    const val ACTION_WIDGET_SETTING_CHANGE =
        "com.ktshow.cs.ACTION_WIDGET_SETTING_CHANGE" // 위젯 음성/기타 설정 변경 액션

    const val ACTION_WIDGET_ENABLED =
        "com.ktshow.cs.ACTION_WIDGET_ENABLED" // 위젯 설정에서 업데이트 , 위젯 재시도 , 위젯 최초실행

    const val ACTION_WIDGET_LOGIN_FAIL =
        "com.ktshow.cs.ACTION_WIDGET_LOGIN_FAIL" // 앱에서 자동로그인 실패시 위젯과 동기화

    const val ACTION_WIDGET_REFRESH_REQUEST =
        "com.ktshow.cs.ACTION_WIDGET_REFRESH_REQUEST" // 위젯 새로고침 주기

    const val ACTION_WIDGET_NOTIFICATION_CLICK = "com.ktshow.cs.ACTION_WIDGET_NOTIFICATION_CLICK"

    //2307-커버스크린 위젯
    const val ACTION_COVER_WIDGET_INFO_UPDATE = "com.ktshow.cs.ACTION_COVER_WIDGET_INFO_UPDATE"
    const val ACTION_COVER_WIDGET_ENABLED =
        "com.ktshow.cs.ACTION_COVER_WIDGET_ENABLED" // 위젯 설정에서 업데이트 , 위젯 재시도 , 위젯 최초실행

    const val ACTION_COVER_WIDGET_LOGIN_CHANGE = "com.ktshow.cs.ACTION_COVER_WIDGET_LOGIN_CHANGE"
    const val ACTION_COVER_WIDGET_LOGIN_FAIL =
        "com.ktshow.cs.ACTION_COVER_WIDGET_LOGIN_FAIL" // 앱에서 자동로그인 실패시 위젯과 동기화

    const val ACTION_COVER_WIDGET_CTN_CHANGE = "com.ktshow.cs.ACTION_COVER_WIDGET_CTN_CHANGE"
    const val ACTION_COVER_WIDGET_CLICK = "com.ktshow.cs.ACTION_COVER_WIDGET_CLICK"
    const val ACTION_COVER_WIDGET_SETTING_CHANGE =
        "com.ktshow.cs.ACTION_COVER_WIDGET_SETTING_CHANGE" // 위젯 음성/기타 설정 변경 액션


    // FCM
    const val FCM_SENDER_ID = "1066818802336" // 고객센터

    const val FCM_REG_READY = "FCM_REG_READY"
    const val FCM_REG_GENERATING = "FCM_REG_GENERATING"
    const val FCM_REG_COMPLETE = "FCM_REG_COMPLETE"
    const val FCM_REG_FAILED = "FCM_REG_FAILED"

    // 앱 상태 변경 BroadcastReceiver action
    const val ACTION_APP_CHANGE_LOGIN = "ACTION_APP_CHANGE_LOGIN"
    const val ACTION_APP_CHANGE_LOGOUT = "ACTION_APP_CHANGE_LOGOUT"
    const val ACTION_APP_CHANGE_APP_CTN = "ACTION_APP_CHANGE_APP_CTN"
    const val ACTION_APP_CHANGE_FONT_SIZE = "ACTION_APP_CHANGE_FONT_SIZE"
    const val ACTION_APP_CHANGE_HOME_CARD = "ACTION_APP_CHANGE_HOME_CARD"
    const val ACTION_APP_CHANGE_MAIN = "ACTION_APP_CHANGE_MAIN"
    const val ACTION_APP_CHANGE_USIM = "ACTION_APP_CHANGE_USIM"

    // 폰트 크기
//    public static final String FONT_SIZE_DEFAULT = "DFONT";
//    public static final String FONT_SIZE_BIG = "LFONT";

    // 폰트 크기
    //    public static final String FONT_SIZE_DEFAULT = "DFONT";
    //    public static final String FONT_SIZE_BIG = "LFONT";
    const val INTENT_PROTOCOL_START = "intent:"

    // [DR-2020-15828] 스플래시 이미지 변경 가능 개발(CMS)
    const val DIR_INTRO = "image_intro"

    // 권한설정
    const val ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION"
    const val ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION"

    //PCI KT 제휴사 코드
    const val PCI_KT_CODE = "2001"
    const val BIO_LOGIN = "bioLogin"
    const val JOIN_INFO = "joininfo"

    const val On = "1"
    const val Off = "0"
    const val S00000 = "S00000" // 성공

    const val KT_PASSKEY_ = "PASSKEY_MYKTAPP" // 성공


    const val ACTION_NETWORK_CHECK = "com.ktshow.cs.ACTION_NETWORK_CHECK"
}
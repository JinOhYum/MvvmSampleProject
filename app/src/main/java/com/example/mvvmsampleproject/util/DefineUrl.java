package com.example.mvvmsampleproject.util;

import static com.example.mvvmsampleproject.util.DefineCommon.API_ID_BASE;
import static com.example.mvvmsampleproject.util.DefineConfig.CHATBOT_HOST;
import static com.example.mvvmsampleproject.util.DefineConfig.DOMAIN;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Arrays;

public class DefineUrl {

    private static final String LOGIN_HOST = "";
    public static final String URL_BLANK = "about:blank";

    public static final String LOGIN_DOMAIN = "https://" + LOGIN_HOST;

    // 새창으로 띄울 로그인 URL
    public static final String LOGIN_FILTER1 = "/wamui/appLogin.do";
    public static final String LOGIN_FILTER2 = "/wamui/AthMobile.do";
    public static final String LOGIN_FILTER3 = "/wamui/bioLogin.do";

//    public static final String LOGIN_FILTER3 = LOGIN_HOST + "/wamui/CusFindPasswordMobile.do";


//    public static final String LOGIN_FILTER1 = "login.kt.com/wamui/appLogin.do";
//    public static final String LOGIN_FILTER2 = "login.kt.com/wamui/AthMobile.do";

    // 간편 로그인 원터치 로그인 페이지  //kcy1000-202211- IAMUI확인
    public static final String LOGIN_ONE_SIMPLE_FILTER = LOGIN_HOST + "/wamui/appSimpleLogin.do";
//    public static final String LOGIN_ONE_SIMPLE_FILTER = LOGIN_HOST + "/wamui/appLogin.do";

    // 웹뷰를 새창으로 띄우기 위해 가로챌 URL
    public static final ArrayList<String> LOGIN_FILTER_LIST = new ArrayList<>(Arrays.asList(LOGIN_FILTER1, LOGIN_FILTER2, LOGIN_FILTER3));

    // 로그인 URL
//    public static final String URL_LOGIN = LOGIN_DOMAIN + "/wamui/appLogin.do";

    public static final String URL_LOGIN = LOGIN_DOMAIN + "/wamui/bioLogin.do";
    // 2406-프로젝트_생체인증로그인
    public static final String URL_BIOMETRICS_LOGIN = LOGIN_DOMAIN + "/wamui/bioLogin.do?mskUserId=%s&passkeyonoff=%s&passkeydevice=%s&passkeyreg=%s&lastlogintype=%s";
    public static final String URL_BIOMETRICS_JOIN_INFO_LOGIN = LOGIN_DOMAIN + "/wamui/passkeyJoinInfo.do?mskUserId=%s&passkeyonoff=%s&passkeydevice=%s&passkeyreg=%s&lastlogintype=%s";

    // 번호 로그인(새창)
    public static final String LOGIN_NUMBER_FILTER = LOGIN_HOST + "/wamui/appCtnLogin.do";

    // 번호 로그인  //kcy1000-202211- IAMUI확인
    public static final String URL_LOGIN_NUMBER = LOGIN_DOMAIN + "/wamui/appCtnLogin.do";

    // 2023 신규 URL
    public static final String URL_MAIN = DOMAIN + "/cardmain/v5/a_CardMain.do";
    public static final String URL_MAIN_V2 = URL_MAIN + "#loginProgressY";
    public static final String URL_MAIN_V3 = URL_MAIN + "#mainScroll=";
    public static final String URL_BENEFIT = DOMAIN + "/cardmain/v5/a_BenefitCardMain.do";
    public static final String URL_MENU = DOMAIN + "/menu/v5/a_AllMenu.do";
    public static final String URL_SHOP = "https://m.shop.kt.com:444/m/main.do";
    //    public static final String URL_SHOP = "https://m.dev.shop.kt.com/m/main.do";
    public static final String URL_ALL_SEARCH = "https://m.search.kt.com";

    // 챗봇 URL
    public static final String URL_CHATBOT = CHATBOT_HOST + "/client/mobile-app/chat.html";
    public static final String URL_WEB_CHATBOT = CHATBOT_HOST + "/client/mobile-web/chat.html";

    // [DR-2019-25911] Android IPIN 화면 뒤로가기 오류 수정
    // 뒤로가기시 2칸 뒤로가기 해야하는 url 목록
    public static final String[] URL_GO_BACK_FORWARD = new String[]{
            "https://ipin.siren24.com/i-PINM2/jsp/ipin2_j21.jsp"
    };
    // 액션바 홈 버튼(사용안함)
    public static final String URL_HOME = URL_MAIN + "?homeButtonYn=Y";

    //kcy1000-202206 - Smart Switch 통해 자동로그인 추가하기.
    public static final String URL_LOGIN_ID_AUTO_FMT_BLOCK_STORE = DOMAIN + "/login/v2/a_AutoLoginInfo.do?simpleLoginYn=N&appToken=%s&deviceId=%s&deviceUuid=%s&blockStoreYn=%s";
    // 간편 로그인 자동 로그인
    public static final String URL_LOGIN_SIMPLE_AUTO_FMT = DOMAIN + "/login/a_AutoSimplLoginInfo.do?uid=%s&ktToken=%s&ktUserId=%s&deviceId=%s&deviceUuid=%s";
    // [DR-2018-52712] 자동로그인 속도 개선
    // 심플로그인 url 추가 (심플로그인 여부 파라미터 추가)
    public static final String URL_LOGIN_SIMPLE_AUTO_FMT_V2 = DOMAIN + "/login/v2/a_AutoLoginInfo.do?simpleLoginYn=Y&uid=%s&ktToken=%s&ktUserId=%s&appToken=%s&deviceId=%s&deviceUuid=%s";
    // [2019-07-18 : hangoo] 6.2.3 버전
    // 새로운 간편 로그인 SDK를 사용하기 위해서 파라미터에 simpleSdkYn 추가
    public static final String URL_LOGIN_SIMPLE_AUTO_FMT_V2_EXT = DOMAIN + "/login/v2/a_AutoLoginInfo.do?simpleLoginYn=Y&simpleSdkYn=Y&uid=%s&ktToken=%s&ktUserId=%s&appToken=%s&deviceId=%s&appId=%s&deviceUuid=%s";
    // 2405-간편로그인 통합SDK변경으로 인한 URL 변경
    public static final String URL_LOGIN_SIMPLE_AUTO_FMT_V3 = DOMAIN + "/login/v2/a_AutoLoginInfo.do?simpleLoginYn=Y&uid=%s&ktToken=%s&ktUserId=%s&appToken=%s&deviceId=%s&appId=%s&deviceUuid=%s";
    // +++++

    // 간편 로그인 원터치 로그인 페이지(ktId 마스킹 처리 때문에 고객센터에서 마스킹 후 wamui 페이지로 리다이렉션)
    //public static final String URL_LOGIN_ONE_SIMPLE_FMT = LOGIN_DOMAIN + "/wamui/appSimpleLogin.do?mskUserId=%s";

    // 2407-생체인증 프로젝트에의해 제거
//    public static final String URL_LOGIN_ONE_SIMPLE = DOMAIN + "/login/a_OneSimpleLogin.do";
    public static final String URL_LOGIN_ONE_SIMPLE_FMT = DOMAIN + "/login/a_OneSimpleLogin.do?ktUserId=%s";

    // 로그인 화면에서 Activity 종료를 위한 URL
    public static final String URL_LOGIN_FINISH_ACTIVITY = DOMAIN + "/cardmain/a_CardMain.do?windowCloase=Y";

    // 로그인 성공 후 브릿지
    public static final String URL_LOGIN_BRIDGE = DOMAIN + "/login/a_LoginBlankBridge.do";

    // 로그아웃
    public static final String URL_LOGOUT = DOMAIN + "/login/a_LogoutGateway.do";

    // 비밀번호 변경
    public static final String URL_CHANGE_PWD = LOGIN_DOMAIN + "/wamui/CusChangePasswordMobile.do";

    // SNS ID 관리
    public static final String URL_SNS_ID_MANAGE = LOGIN_DOMAIN + "/wamui/CusSocialInterlinkMobile.do";

    // 홈카드 설정
    public static final String URL_HOME_CARD = DOMAIN + "/setting/a_CardSettingMain.do";

    // 오픈 라이센스
    public static final String URL_OPEN_LICENSE = DOMAIN + "/setting/a_License.do";

    // 도움말
    public static final String URL_FAQ = DOMAIN + "/setting/a_Faq.do";

    // 요금 명세서 조회
    public static final String URL_BILL_CHARGE = DOMAIN + "/bill/s_BillChargeTotalList.do";

    // 멤버십 바코드
    public static final String URL_MEMBERSHIP = DOMAIN + "/cardmain/a_EnlargeMemBarcodeHtml.do";

    // 패밀리박스 >가입
    public static final String URL_FAMILY_BOX_JOIN = DOMAIN + "/cardmain/v5/a_FamilyBoxJoin.do";

    // 요금 조회
    public static final String URL_FEE_LIST = DOMAIN + "/usage/s_MoUsageDataChargeLte.do";

    // 가입정보 조회
    public static final String URL_MEMBER_INFO = DOMAIN + "/myinfo/s_MobileMyInfoDetail.do";

    // 위젯 고객센터
    public static final String URL_WIDGET_HELP = "https://ermsweb.kt.com/mobile/faq/faqList.do";

    // 요금조회
    public static final String URL_FEE_INQUERY = DOMAIN + "/bill/s_BillChargeTotalList.do";

    // 나만의 혜택
    public static final String URL_MY_BENEFIT = DOMAIN + "/myproduct/s_CouponList.do";

    // 카카오톡 로그인
    public static final String URL_KAKAOTALK_LOGING = "https://accounts.kakao.com/login";

    public static final String MARKET_PREFIX = "market://details?id=";
    public static final String MARKET_GOOGLE_PREFIX = "http://play.google.com/store/apps/details?id=";
    public static final String MARKET_ONESTORE_GET_VERSION = "http://openapi.onestore.co.kr/api/devcenter/getAppVersion/v1"; // 원스토어 버전정보 API

    // 위치기반 서비스 이용 동의 상세보기
    public static final String MARKETING_LOCATION_PERMISSION_DETAIL = "https://dev.m.my.kt.com/app/cardmain.a_marketing_tems_location.html";

    // kcy1000-202207 - 비정상실행 공지 팝업
//    public static final String APP_FAIL_URE_RESPONESE ="http://dev.m.my.kt.com/apps/appMainMgt/webjson/appFailureResponse_AND.webjson";
    public static final String APP_FAIL_URE_RESPONESE =DOMAIN + "/apps/appMainMgt/webjson/appFailureResponse_AND.webjson";
    // 회원탈퇴
    public static final String URL_MEMBERSHIP_WITHDRAWAL = LOGIN_DOMAIN + "/wamui/MemGuideCancellationMobile.do";
    //2307-샵 출첵기능
    public static final String URL_SHOP_EVENT_ATTEND = "https://m.shop.kt.com:444/m/deal/eventAttend.do";

    public static final String URL_CHATBOT_LOGIN_SESSION_KEY =  "client/wamuiLoginAfter?sessionKey";


    // ------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 원스토어 업데이트 요청 (자동설치)
     */
    // 요청하는 상품의 PKG명이 설치요청 PKG와 동일한 경우에만 동작
    // 실제 버전이 낮은 경우에만 동작.
    // 구매이력이 있는 경우에만 동작 ( 자동 구매 처리 없음)
    public static final Uri FORCE_UPDATE_URI = Uri.parse("onestore://common/product/bg_update/0000697302");

    /**
     * 원스토어 앱 상세호출
     */
//    public static final Uri DETAIL_URI = Uri.parse("onestore://ollehmarket/product/0000697302");
    // [2019-04-18] deprecated 되어 개선
    public static final Uri DETAIL_URI = Uri.parse("onestore://common/product/0000697302");
    // (1) 기구매 상품인 경우 자동설치 (기존에 설치되어 있어도 재설치)
    // (2) 무료 상품이고, 구매이력이 없는 경우 구매 이력 자동 생성 후 자동설치
    // (3) 유료인 경우, 결제페이지로 이동 ( 자동 설치 미진행이므로, 사용자 확인 창은 불필요 )
    // ------------------------------------------------------------------------------------------------------------------------------------

    // API ID
    public static final int API_ID_INTRO = API_ID_BASE + 2;
    public static final int API_ID_UPDATE_ONESTORE = API_ID_BASE + 3;
    public static final int API_ID_GET_SETTING_INFO = API_ID_BASE + 4;
    public static final int API_ID_SET_SETTING_INIT_INFO = API_ID_BASE + 5;
    public static final int API_ID_SET_USER_INFO = API_ID_BASE + 6;
    public static final int API_ID_APP_CONFIG_INFO = API_ID_BASE + 7;
    public static final int API_ID_SET_APP_CTN = API_ID_BASE + 8;
    public static final int API_ID_SET_WIDGET_CTN = API_ID_BASE + 9;
    public static final int API_ID_SET_MOBILE_AMT_CTN = API_ID_BASE + 10;
    public static final int API_ID_SET_MOBILE_AMT_TERM = API_ID_BASE + 11;
    public static final int API_ID_SET_NOTIFY_BAR_CTN = API_ID_BASE + 12;
    public static final int API_ID_SET_MARKETING_AGREE = API_ID_BASE + 13;
    public static final int API_ID_SET_PUSH_MOBILE_AMT_YN = API_ID_BASE + 15;
    public static final int API_ID_GET_OTP = API_ID_BASE + 16;
    public static final int API_ID_GET_SIMPLE_SEARCH_AGREE = API_ID_BASE + 17;
    public static final int API_ID_GET_NOTIFY_BAR_INFO = API_ID_BASE + 18;
    public static final int API_ID_GET_NOTIFY_BAR_INFO_USER = API_ID_BASE + 19;
    public static final int API_ID_GET_WIDGET_INFO = API_ID_BASE + 20;
    public static final int API_ID_GET_WIDGET_INFO_USER = API_ID_BASE + 21;
    public static final int API_ID_GET_MEMBERSHIP_INFO = API_ID_BASE + 22;
    public static final int API_ID_SET_NOTIFY_BAR_YN = API_ID_BASE + 23;
    public static final int API_ID_FCM_TOKEN_SENDER = API_ID_BASE + 24;
    public static final int API_ID_AUTO_LOGIN_RETRY = API_ID_BASE + 25;
    public static final int API_ID_SHAKE_MEMBERSHIP_INFO = API_ID_BASE + 26; // [DR-2019-20426] 흔들어서 멤버십 호출
    public static final int API_ID_CHATBOT_URL_LIST = API_ID_BASE + 27; // [DR-2019-62540] 챗봇 PASS 인증 개발
    public static final int API_ID_SET_PUSH_MARKETING_PERSON_AGREE = API_ID_BASE + 28;        // DR-2020-23225 마케팅 수신동의
    public static final int API_ID_SET_PUSH_MARKETING_AD_AGREE = API_ID_BASE + 29;        // DR-2020-23225 마케팅 수신동의
    public static final int API_ID_GET_MY_BANK_ACCOUNT = API_ID_BASE + 30;
    public static final int API_ID_SET_PUSH_PERMISSION = API_ID_BASE + 31;
    public static final int API_ID_SET_PCI_INFO = API_ID_BASE + 32;
    public static final int API_ID_GET_MARKETING_ALL_OW_BY_CUSTID_INFO_URL = API_ID_BASE + 33;
    public static final int API_ID_STALK_DIALOG_TALK = API_ID_BASE + 34;
    public static final int API_ID_CLIENT_VOC_MESSAGE = API_ID_BASE + 35;
    public static final int API_ID_GET_PUSH_ALLOW_YN = API_ID_BASE + 36;
    public static final int API_ID_SET_SHOP_PUSH_PERMISSION = API_ID_BASE + 37;
    public static final int API_ID_GET_COVER_WIDGET_INFO = API_ID_BASE + 38;

    public static final int API_ID_GET_ATTEND_YN = API_ID_BASE + 39;

    public static final int API_ID_SET_ATTEND_YN = API_ID_BASE + 40;

    public static final int API_ID_SET_ATTEND_OFF_PERMISSION = API_ID_BASE + 41;

    // 인트로 정보 조회
//    public static final String API_INTRO_V5 = DOMAIN + "/api/v2/a_Intro.json";
    public static final String API_INTRO_V5 = DOMAIN + "/api/v5/a_Intro.json";

    // 자동로그인 토큰만료 확인
    public static final String API_AUTO_LOGIN_RETRY = DOMAIN + "/api/v2/setting/a_AutoLoginRetryYn.json";

    // GCM 토큰 전송
    public static final String API_FCM_TOKEN_SEND = DOMAIN + "/api/v2/setting/a_SetRegId.json";

    // 설정 조회
    public static final String API_GET_SETTING_INFO = DOMAIN + "/api/setting/a_GetUserSettingInfo.json";

    // 설정 초기 정보 저장
    public static final String API_SET_SETTING_INIT_INFO = DOMAIN + "/api/setting/a_SetUserSettingInfo.json";

    // [DR-2018-52712] 자동로그인 속도 개선
    public static final String API_SET_USER_INFO = DOMAIN + "/api/v2/setting/a_SetUserInfo.json";

    // 앱 구성 정보 조회
    public static final String API_APP_CONFIG_INFO = DOMAIN + "/api/info/a_GetAppConfigurationInfo.json";

    // 앱 사용번호 설정 저장
    public static final String API_SET_APP_CTN = DOMAIN + "/api/setting/login/a_SetAppCtn.json";

    // 위젯 사용번호 설정 저장
    public static final String API_SET_WIDGET_CTN = DOMAIN + "/api/setting/widget/a_SetWidgetCtn.json";

    // 모바일 이용량 알림 번호 설정 저장
    public static final String API_SET_MOBILE_AMT_CTN = DOMAIN + "/api/setting/noticenter/a_SetMblAmountUsedCtn.json";

    // 모바일 이용량 알림 주기 설정 저장
    public static final String API_SET_MOBILE_AMT_TERM = DOMAIN + "/api/setting/noticenter/a_SetMblAmountUsedTerm.json";

    // 상단바 조회 번호 설정 저장
    public static final String API_SET_NOTIFY_BAR_CTN = DOMAIN + "/api/setting/android/a_SetCurtainbarCtn.json";

    // 마케팅 수신 동의 설정 저장
    public static final String API_SET_MARKETING_AGREE = DOMAIN + "/api/setting/noticenter/a_SetMarketingAlarm.json";

    // 공지 혜택 알림 수신 여부 저장
    public static final String API_SET_PUSH_NOTICE_YN = DOMAIN + "/api/setting/noticenter/a_SetNotiAlarm.json";

    // 모바일 이용량 알림 수신 여부 저장
    public static final String API_SET_PUSH_MOBILE_AMT_YN = DOMAIN + "/api/setting/noticenter/a_SetMblAmountUsed.json";

    // 1회용 비밀번호 받기
    public static final String API_GET_OTP = DOMAIN + "/api/setting/login/a_GetOtp.json";

    // 간편조회 이용 동의 팝업 정보
    public static final String API_GET_SIMPLE_SEARCH_AGREE = DOMAIN + "/api/info/a_GetSmplSrchAgreePopupInfo.json";

    // 상단바 정보 조회
    // [DR-2019-52396] 위젯 상단바 주기적 호출을 위한 개발
//    public static final String API_GET_NOTIFY_BAR_INFO = DOMAIN + "/api/info/a_GetAndroidExtend.json";
    public static final String API_GET_NOTIFY_BAR_INFO = DOMAIN + "/api/info/a_GetNewAndroidExtend.json";

    // 위젯 정보 조회 => 202301-V5변경됨
//    public static final String API_GET_WIDGET_INFO = DOMAIN + "/api/widget/a_GetNewWidgetData.json";
    public static final String API_GET_WIDGET_INFO = DOMAIN + "/api/widget/v5/a_GetNewWidgetData.json";
    // 2308-커버스크린 위젯
    public static final String API_GET_COVER_SCREEN_WIDGET_INFO = DOMAIN + "/api/widget/v5/a_GetCoverScreenWidgetData.json";

    // 멤버십 정뵤 조회
    public static final String API_GET_MEMBERSHIP_INFO = DOMAIN + "/api/widget/a_GetMembershipInfo.json";

    //스마트톡 > 대화형 검색
    public static final String API_STALK_DIALOG_TALK = DOMAIN + "/api/stalk/a_DialogTalk.json";

    // [DR-2019-62540] 챗봇 PASS 인증 개발
    // 챗봇 > 챗봇 로그인 URL 조회
    public static final String API_CHATBOT_URL_LIST = DOMAIN + "/api/stalk/a_KTalkUrlList.json";

    // DR-2020-23225 마케팅 수신동의
    public static final String API_SET_PUSH_MARKETING_AGREE = DOMAIN + "/api/marketing/a_agree.json";

    // KT 멤버십 > 내 통장 결제 > 계좌목록 조회
    public static final String API_GET_MY_BANK_ACCOUNT = DOMAIN + "/api/membership/a_GetMyAccountData.json";
    // My 멤버십
    public static final String URL_MY_MEMBERSHIP = DOMAIN + "/membership/s_MyMembershipInfo.do";

    // Error 페이지 URL
    public static final String WEBVIEW_URL_NETWORK_ERR = "file:///android_asset/www/html/err_webview_network.html";

    // webview UNKNOWN Error 처리
    public static final String WEBVIEW_URL_UNKNOWN_ERR = "file:///android_asset/www/html/err_webview_unknown.html";


    // 챗봇 로그인 url 목록
    public static final String URL_CHATBOT_PASSAPP_BEFORE = CHATBOT_HOST + "/client/passLoginBefore";
    public static final String URL_CHATBOT_PASSAPP_AFTER = CHATBOT_HOST + "/client/passLoginAfter";
    public static final String URL_CHATBOT_KMCERT = "https://www.kmcert.com";
    public static final String URL_CHATBOT_LOGIN_AFTER = CHATBOT_HOST + "/client/wamuiLoginAfter";
    public static final String URL_CHATBOT_LOGIN_WEB_BEFORE = CHATBOT_HOST + "/client/wamuiLoginWebBefore";
    public static final String URL_CHATBOT_LOGIN_MOB_BEFORE = CHATBOT_HOST + "/client/wamuiLoginMobileBefore";
    public static final String URL_CHATBOT_LOGIN_APP_BEFORE = CHATBOT_HOST + "/client/wamuiLoginAppBefore";

    // KT 멤버십 다운로드 URL
    public static final String URL_KT_MEMBERSHIP_DOWNLOAD = "http://app.membership.kt.com/membership/webview/om?type=1&code=KT_mykt";
    public static final String PACKAGE_KT_MEMBERSHIP = "com.olleh.android.oc2";

    // kcy1000-202209 - 고객 vod 대응 디버깅 App
    public static final String API_SET_CLIENT_VOC_URL= DOMAIN + "/api/appvoc/appVocLog.json";

    // kcy1000-202212 - PCI 정보전송
    public static final String API_SET_PCI_INFO_URL= DOMAIN + "/api/setting/a_CollectPciAdAgreeProc.json";

    //kcy1000-202212 - 55,56번 정보가져오기
    public static final String API_GET_MARKETING_ALL_OW_YN_INFO_URL= DOMAIN + "/api/setting/a_GetMarketingAllowYnByCustId.json";

    //= 2023리뉴얼  =============================================================================================================================================
    //화면상단 웹뷰 바코드 진입
    public static final String URL_MY_MEMBERSHIP_BARCODE = DOMAIN + "/cardmain/v5/a_MyMembershipHtml.do";
    //알림화면
    public static final String URL_NOTICE_MAIN = DOMAIN + "/notice/v5/a_NoticeMain.do";
    //사용자 알림설정여부
    public static final String URL_GET_PUSH_ALLOW_YN = DOMAIN + "/api/setting/a_GetPushAllowYn.json";

    //출첵 알림 ON/OFF 조회
    public static final String URL_GET_ATTEND_YN = "https://m.shop.kt.com:444/ktshop/v1.0/getAttendOnOff.json";
    //출첵 알림 ON/OFF 전송
    public static final String URL_SET_ATTEND_YN = "https://m.shop.kt.com:444/ktshop/v1.0/setAttendOnOff.json";
    //출첵 상세보기 페이지
    public static final String URL_ATTEND_PUSH_DETAILS = DOMAIN+"/app/cardmain/a_shopAttnPushDetails.html";

    //모두 동의하기 상세보기 페이지
    public static final String URL_PUSH_ALL_DETAILS = DOMAIN+"/app/cardmain/a_AgreeAllDetails.html";

    //밀리의 서재 고유 Url
    public static final String URL_MILLIE = "millie.co.kr";

    public static final String URL_SAFE_SDK_DETAILS_INFO_URL = "https://smishing-keeper.greeninternet.co.kr/smishing_policy.html";


}
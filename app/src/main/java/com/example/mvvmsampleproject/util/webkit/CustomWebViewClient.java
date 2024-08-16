package com.example.mvvmsampleproject.util.webkit;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebBackForwardList;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.mvvmsampleproject.util.DataUtils;
import com.example.mvvmsampleproject.util.DefineCode;
import com.example.mvvmsampleproject.util.DefineCommon;
import com.example.mvvmsampleproject.util.DefineUrl;
import com.example.mvvmsampleproject.util.DeviceUtil;
import com.example.mvvmsampleproject.util.LogUtil;
import com.example.mvvmsampleproject.util.webview.OnWebViewListener;

import java.lang.ref.WeakReference;

public class CustomWebViewClient extends WebViewClient {

//    private static final String REAL_DOMAIN = "https://m.my.kt.com";
//    private static final String DEBUG_DOMAIN = "https://m.myapp.kt.com";

    private final WeakReference<Context> weakContext;
    private final OnWebViewListener listener;

    private String mStartUrl = "";      // 시작 url
    private String reqLoginUrl = "";    // login after url

    public CustomWebViewClient(Context context, OnWebViewListener listener) {
        weakContext = new WeakReference<Context>(context);
        this.listener = listener;
    }


    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        LogUtil.i("onPageStarted");
        if (DataUtils.isNull(view) || DataUtils.isNull(listener))
            return;

        int type = DefineCode.URL_TYPE_HTTP;

        // 네트워크 상태 체크
        if (DataUtils.isNotNull(weakContext) && DataUtils.isNotNull(weakContext.get()) && !DeviceUtil.isNetworkConnected(weakContext.get())) {
            type = DefineCode.URL_TYPE_NETWORK_DISABLE;
            if (!DefineUrl.WEBVIEW_URL_NETWORK_ERR.equals(url)) {
                listener.onWebViewStart(view, type, url);
            }
            return;
        }

        // URL 타입 체크
        Uri uri = Uri.parse(url);
        String scheme = uri.getScheme();
        if (scheme.equalsIgnoreCase("tel")) {
            type = DefineCode.URL_TYPE_TEL;
        } else if (scheme.equalsIgnoreCase("mailto")) {
            type = DefineCode.URL_TYPE_MAILTO;
        } else if (scheme.equalsIgnoreCase("hook")) {
            type = DefineCode.URL_TYPE_MOVIE_TICKET;
        } else if (url.startsWith(DefineCommon.INTENT_PROTOCOL_START)) {
            type = DefineCode.URL_TYPE_INTENT;
        }

        if (type != DefineCode.URL_TYPE_HTTP) {
            LogUtil.i("HTTP 아님 ");
            listener.onWebViewStart(view, type, url);
            return;
        }

        // 로그인 페이지로 이동해야하는 url 인지 체크
        WebBackForwardList list = view.copyBackForwardList();
        if ((DataUtils.isNull(list) || list.getSize() <= 0) && checkLoginUrl(url)) {
            type = DefineCode.URL_TYPE_LOGIN;
            LogUtil.i("로그인 페이지로 이동해야하는 url 인지 체크 ");
            listener.onWebViewStart(view, type, url);
            return;
        }

        mStartUrl = url;

        if (DataUtils.isNotNull(weakContext) && DataUtils.isNotNull(weakContext.get())
                && weakContext.get() instanceof Activity && !((Activity) weakContext.get()).isFinishing() && !((Activity) weakContext.get()).isDestroyed()) {
            listener.onWebViewStart(view, type, url);
        } else {
            LogUtil.i("type = 정상 ?");
        }

        super.onPageStarted(view, url, favicon);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        LogUtil.i("shouldOverrideUrlLoading");
        if (DataUtils.isNull(view) || DataUtils.isNull(request) || DataUtils.isNull(listener))
            return false;

        String url = request.getUrl().toString();
        LogUtil.i("여기 url = " + url);

        // 네트워크 상태 체크
        if (DataUtils.isNotNull(weakContext)
                && DataUtils.isNotNull(weakContext.get())
                && !DeviceUtil.isNetworkConnected(weakContext.get())) {
            LogUtil.i("shouldOverrideUrlLoading >> URL_TYPE_NETWORK_DISABLE");
            listener.onWebViewUrlChange(view, DefineCode.URL_TYPE_NETWORK_DISABLE, url, request.isRedirect());
            return true;
        }

        // URL 타입 체크
        int type = checkCatchUrlType(url);
        if (type != DefineCode.URL_TYPE_HTTP) {
            LogUtil.i("shouldOverrideUrlLoading >> URL_TYPE_HTTP X");
            listener.onWebViewUrlChange(view, type, url, request.isRedirect());
            return true;
        }

        // 로그인 페이지로 이동해야하는 url 인지 체크
        if (checkLoginUrl(url)) {
            LogUtil.i("shouldOverrideUrlLoading >> URL_TYPE_LOGIN");
            type = DefineCode.URL_TYPE_LOGIN;
            listener.onWebViewUrlChange(view, type, url, request.isRedirect());
            return true;
        }

        if (DataUtils.isNotNull(weakContext.get())
                && weakContext.get() instanceof Activity
                && !((Activity) weakContext.get()).isFinishing()
                && !((Activity) weakContext.get()).isDestroyed()) {
            LogUtil.i("shouldOverrideUrlLoading >> onWebViewUrlChange");
            return listener.onWebViewUrlChange(view, type, url, request.isRedirect());
        }

        return false;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LogUtil.i("shouldOverrideUrlLoading");
        if (DataUtils.isNull(view) || DataUtils.isNull(listener))
            return false;

        // 네트워크 상태 체크
        if (DataUtils.isNotNull(weakContext)
                && DataUtils.isNotNull(weakContext.get())
                && !DeviceUtil.isNetworkConnected(weakContext.get())) {
            LogUtil.i("shouldOverrideUrlLoading >> URL_TYPE_NETWORK_DISABLE");
            listener.onWebViewUrlChange(view, DefineCode.URL_TYPE_NETWORK_DISABLE, url, false);
            return true;
        }

        // URL 타입 체크
        int type = checkCatchUrlType(url);
        if (type != DefineCode.URL_TYPE_HTTP) {
            LogUtil.i("shouldOverrideUrlLoading >> URL_TYPE_HTTP X");
            listener.onWebViewUrlChange(view, type, url, false);
            return true;
        }
        // 로그인 페이지로 이동해야하는 url 인지 체크
        if (checkLoginUrl(url)) {
            LogUtil.i("shouldOverrideUrlLoading >> URL_TYPE_LOGIN");
            type = DefineCode.URL_TYPE_LOGIN;
            listener.onWebViewUrlChange(view, type, url, false);
            return true;
        }

        if (DataUtils.isNotNull(weakContext)
                && DataUtils.isNotNull(weakContext.get())
                && weakContext.get() instanceof Activity
                && !((Activity) weakContext.get()).isFinishing()
                && !((Activity) weakContext.get()).isDestroyed()) {
            //LogUtil.i("shouldOverrideUrlLoading >> onWebViewUrlChange");
            listener.onWebViewUrlChange(view, type, url, false);
        }

        return false;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        LogUtil.i("onPageFinished URL : " + url);

        if (DataUtils.isNull(view))
            return;

        // [DR-2034-63014] 안드로이드 이전차수 결함조치 - 네트워크 차단 오류페이지
        String viewUrl = view.getUrl();
        if (DefineUrl.WEBVIEW_URL_NETWORK_ERR.equals(viewUrl))
            return;

        // Action bar, 로딩 관련 깜박임 때문에 로그인 페이지 이동인 경우 WebView Activity 로 페이지 종료를 전달하지 않는다.
        if (DataUtils.isNotNull(reqLoginUrl) && DataUtils.isNotNull(url) && url.equals(reqLoginUrl)) {
            reqLoginUrl = "";
        } else {
            if (DataUtils.isNotNull(weakContext.get())
                    && weakContext.get() instanceof Activity
                    && !((Activity) weakContext.get()).isFinishing()
                    && !((Activity) weakContext.get()).isDestroyed()
                    && DataUtils.isNotNull(listener)) {
                LogUtil.i("onWebViewFinish URL : " + url);
                listener.onWebViewFinish(view, url);
            }
        }
        super.onPageFinished(view, url);
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        LogUtil.i("doUpdateVisitedHistory");
        super.doUpdateVisitedHistory(view, url, isReload);
    }


    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        LogUtil.i("onReceivedError");
        super.onReceivedError(view, request, error);
        if (DataUtils.isNull(view))
            return;

        if(request.isForMainFrame()) {
            onReceivedError(view, error.getErrorCode(), error.getDescription().toString(), request.getUrl().toString());
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onReceivedError(final WebView view, int errorCode, String description, String failingUrl) {
        LogUtil.e("onReceivedError : " +  "errorCode : "  + errorCode +  "/// description : "  + description+  "/// failingUrl : "  + failingUrl );
        super.onReceivedError(view, errorCode, description, failingUrl);
        if (DataUtils.isNull(view))
            return;

        // 로그인 웹뷰는 에러페이지 안띄움
        if (DefineCode.LOGIN_WEBVIEW_TAG.equals(view.getTag()))
            return;

        if (errorCode == ERROR_UNKNOWN && "net::ERR_EMPTY_RESPONSE".equals(description)) {
            //2401-네트워크에러 예외처리
            if ((weakContext.get() instanceof Activity) && (!((Activity) weakContext.get()).isFinishing()) && (!((Activity) weakContext.get()).isDestroyed())) {
                LogUtil.i("onWebViewReceivedError");
                listener.onWebViewReceivedError(view, errorCode, failingUrl);
            }
        } else {
            String webViewUrl = view.getUrl();

            // REMARK
            // 일반적으로 에러 발생시 에러 페이지를 표시해야 하나
            // 고객센터 페이지 내부에서 타 시스템 호출이 많고, 타 시스템에서 에러가 많이 발생하여
            // 현재 로딩하는 페이지와 에러페이지가 같을 경우에만 빈페이지로 에러 처리
            // [DR-2020-24079] Android 앱 소스진단 취약점 조치
//        if ( TextUtils.isEmpty( webViewUrl ) || TextUtils.isEmpty( failingUrl ) || ( !DefineUrl.URL_BLANK.equals( webViewUrl ) && !webViewUrl.equals( failingUrl ) ) )
            if (DataUtils.isNull(webViewUrl) || DataUtils.isNull(failingUrl))
                return;

            // [DR-2020-24079] Android 앱 소스진단 취약점 조치
            // 에러페이지가 현재페이지도 아니고 호출한 페이지도 아니면 넘어감
            if (!webViewUrl.equals(failingUrl) && !mStartUrl.equals(failingUrl))
                return;

            //2404-error처리 구현하기.
            if (errorCode == ERROR_HOST_LOOKUP || errorCode == ERROR_CONNECT || errorCode == ERROR_TIMEOUT ||errorCode == ERROR_UNSUPPORTED_SCHEME  ) {
                if ((weakContext.get() instanceof Activity) && (!((Activity) weakContext.get()).isFinishing()) && (!((Activity) weakContext.get()).isDestroyed())) {
                    LogUtil.i("onWebViewReceivedError");
                    listener.onWebViewReceivedError(view, errorCode, failingUrl);
                }
            }
        }
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        LogUtil.i("onReceivedHttpError");
        super.onReceivedHttpError(view, request, errorResponse);
        int errorCode = errorResponse.getStatusCode();
        String failingUrl = request.getUrl().toString();

        if (!TextUtils.isEmpty(failingUrl) && failingUrl.equals(view.getUrl())) {
            // [DR-2019-26385] Android 모바일 취약점 2건 조치 - 500 에러
            if (errorCode == 403 || errorCode == 404 || errorCode == 500 || errorCode == 503 || errorCode == 504) {
                if (!DefineCode.LOGIN_WEBVIEW_TAG.equals(view.getTag())) {
                    LogUtil.i("check !");
                    if (weakContext.get() != null && weakContext.get() instanceof Activity && !((Activity) weakContext.get()).isFinishing() && !((Activity) weakContext.get()).isDestroyed()) {
                        listener.onWebViewHttpError(view, errorCode, failingUrl);
                    }
                } else {
                    // [DR-2018-52712] 자동로그인 속도 개선
                    // 로그인웹뷰에서 에러시 메인웹뷰에 콜백을 호출한다
//                    if (weakContext != null && weakContext.get() != null && weakContext.get() instanceof MainActivity) {
//                        // 로그인웹뷰
//                        ((MainActivity) weakContext.get()).onJsLoginFail();
//                    }
                }
            }
        }
    }

    @Override
    public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
        LogUtil.i("onReceivedSslError");
//        switch (error.getPrimaryError()) {
//            case SslError.SSL_EXPIRED:
//            case SslError.SSL_IDMISMATCH:
//            case SslError.SSL_NOTYETVALID:
//            case SslError.SSL_UNTRUSTED:
//            default:
//                break;
//        }
        if (DataUtils.isNotNull(handler)) {
//            if (!BUILD_CONFIG_IS_SSL_ALERT) {
//                handler.proceed();
//                return;
//            }
            if (DataUtils.isNotNull(weakContext.get())
                    && weakContext.get() instanceof Activity
                    && !((Activity) weakContext.get()).isFinishing()
                    && !((Activity) weakContext.get()).isDestroyed()) {
                listener.onWebViewSslError(handler);
            }
        } else {
            super.onReceivedSslError(view, handler, error);
        }
    }

// ==============================================================================================================
// FUNCTION
// ==============================================================================================================
    /**
     * checkLoginUrl : 로그인 URL 체크
     */
    protected boolean checkLoginUrl(String url) {
        LogUtil.i("checkLoginUrl :  " + url)    ;
        if (TextUtils.isEmpty(url) || DataUtils.isNull(weakContext) || DataUtils.isNull(weakContext.get()))
            return false;

        Context context = weakContext.get();
//        if (context.getClass() == LoginActivity.class)
//            return false;

        Uri uri = Uri.parse(url);
        String path = uri.getPath();
//        String compareUrl = uri.getHost();
//        compareUrl += TextUtils.isEmpty(path) ? "" : path;

        //2309-IOS와 동일하게 수정
        String compareUrl = TextUtils.isEmpty(path) ? "" : path;
        int loginUrlIndex = DefineUrl.LOGIN_FILTER_LIST.indexOf(compareUrl);
        String loginAfterUrl = "";
        try {
            loginAfterUrl = uri.getQueryParameter("mRt");
        } catch (Exception e) {
        }

        // 로그인 경로가 아닌 경우
        if (loginUrlIndex < 0) {
            // 번호 로그인 or 간편 로그인 원터치 체크
            if (compareUrl.contains(DefineUrl.LOGIN_NUMBER_FILTER) || compareUrl.contains(DefineUrl.LOGIN_ONE_SIMPLE_FILTER)) {
                reqLoginUrl = loginAfterUrl;
                return true;
            }
            return false;
        }
        return true;
    }

    /**
     * checkCatchUrlType : URL 타입을 얻는다.
     */
    public int checkCatchUrlType(String url) {
        LogUtil.i("checkCatchUrlType");
        int type = DefineCode.URL_TYPE_HTTP;

        if (TextUtils.isEmpty(url))
            return type;

        Uri uri = Uri.parse(url);
        String scheme = uri.getScheme();
        String host = uri.getHost();

        if (scheme.equalsIgnoreCase("tel")) {
            type = DefineCode.URL_TYPE_TEL;
        } else if (scheme.equalsIgnoreCase("mailto")) {
            type = DefineCode.URL_TYPE_MAILTO;
        } else if ((scheme.equalsIgnoreCase("hook") && host.equalsIgnoreCase("command")) || url.startsWith("hook://?command=")) {
            type = DefineCode.URL_TYPE_MOVIE_TICKET;
        } else if (url.contains("sori_iphone_listen.asp")) {
            type = DefineCode.URL_TYPE_SORI_ASP;
        } else if (url.toLowerCase().endsWith(".mp3")) {
            type = DefineCode.URL_TYPE_AUDIO;
        } else if (url.toLowerCase().endsWith(".mp4") || url.toLowerCase().endsWith(".3gp")) {
            type = DefineCode.URL_TYPE_VIDEO;
        } else if (url.startsWith("cstore:")) {
            // 다운로드를 위해 올레마켓 연결
            type = DefineCode.URL_TYPE_CSTORE;
        } else if (url.startsWith("ispmobile")) {
            // ISP MOBILE APP 호출
            type = DefineCode.URL_TYPE_ISP_APP;
        } else if (url.contains("http://market.android.com") || url.contains("vguard") ||
                url.contains("droidxantivirus") || url.contains("smshinhanansimclick://") ||
                url.contains("smshinhancardusim://") || url.contains("shinhan-sr-ansimclick://") ||
                url.contains("market://") || url.contains("v3mobile") || url.endsWith(".apk") ||
                url.endsWith("ansimclick") || url.contains("http://m.ahnlab.com/kr/site/download") ||
                url.contains("smhyundaiansimclick://") || url.contains("mpocketansimclick://") ||
                url.contains("kftc-bankpay://") || url.contains("ktpasslink20://") ) {
            // 안심클릭 보안 APP 호출 아래 string들은 보안APP scheme 임. 만약 보안APP이 추가가 된다면 이 부분에 scheme 를 추가 해주어야 함.
            type = DefineCode.URL_TYPE_MARKET;
        } else if (url.startsWith("sms")) {
            // SMS
            type = DefineCode.URL_TYPE_SMS;
        } else if (url.contains("smartbellringkt:")) {
            // 벨링 APP 추가
            type = DefineCode.URL_TYPE_BELLING;
        } else if (url.startsWith(DefineCommon.INTENT_PROTOCOL_START)) {
            type = DefineCode.URL_TYPE_INTENT;
        }
        return type;
    }

}
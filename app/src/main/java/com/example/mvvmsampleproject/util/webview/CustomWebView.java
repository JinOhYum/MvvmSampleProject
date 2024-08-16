package com.example.mvvmsampleproject.util.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import com.example.mvvmsampleproject.util.webview.OverScrollHelper;

public class CustomWebView extends WebView {

    public void setOnWebViewListener(OnWebViewListener listener) {
        this.webViewListener = listener;
    }

    private OnWebViewListener webViewListener;

    private OverScrollHelper overScroll;

    public CustomWebView(Context context) {
        super(context);
        init();
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        this.setOverScrollMode(OVER_SCROLL_ALWAYS);
        this.overScroll = new OverScrollHelper(this);
        this.overScroll.setOnOverScrollMeasure(this::computeVerticalScrollOffset);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        return overScroll.onTouchEvent(event) || super.onTouchEvent(event);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        overScroll.onScrollChanged();
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        overScroll.onSizeChanged(w, h, oldw, oldh);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public OverScrollHelper getOverScroll() {
        return overScroll;
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initWebViewSetting(String appendUserAgent, boolean isWindowOpen, Context context) {
        WebSettings settings = getSettings();

//        if (DataUtils.isNotNull(appendUserAgent)) {
//            // KITKAT 부터는 웹페이지가 로딩하는 동안 변경하면 웹뷰가 다시 로드된다.
//            settings.setUserAgentString(String.format("%s %s", settings.getUserAgentString(), appendUserAgent));
//            //LogUtil.i("WebView UserAgent >> " + settings.getUserAgentString());
//        }
        settings.setUserAgentString(String.format("%s %s", settings.getUserAgentString(), appendUserAgent));
        settings.setJavaScriptEnabled(true); //202212 캐쉬옵션 수정
//        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 웹페이지를 화면 폭에 맞추기
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setSaveFormData(false);            // [20150319] 삼성 키패드 쿼티 이슈 - 자동완성 OFF
        settings.setDomStorageEnabled(true);        // 로컬 스토리지/세션 스토리지 사용 여부(def:false)
        settings.setDatabaseEnabled(true);          // 데이터베이스 접근 허용 (def:false)
        // kcy10000-202209 - AppCache관련 API가 API Level 33부터 Deprecated됐다.
//        settings.setAppCacheEnabled(true);
        settings.setGeolocationEnabled(true);       // HTML5 Configuration Parameter Settings.
        settings.setUseWideViewPort(true);          // HTML "viewport" 메타태그 지원 여부
        settings.setAllowFileAccess(true);          // 시스템 파일 접근 허용 여부(def:true) false 이더라도 file:///android_asset and file:///android_res 는 사용가능? (확인 필요)
        settings.setLoadWithOverviewMode(true);     // 내용을 화면 폭에 맞게 축소할지 여부(def:false)
        settings.setNeedInitialFocus(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setBlockNetworkImage(false);       // 네트워크 이미지 리소스 로드 여부(def:false)
        settings.setBuiltInZoomControls(false);     // 안드로이드에서 제공하는 줌 아이콘을 사용할 수 있도록 설정(def:false)
        settings.setDisplayZoomControls(false);     // (def:true)
        settings.setSupportZoom(false);             // 확대, 축소 기능 사용 여부(def:true)
        settings.setTextZoom(100);                  // 웹뷰에서 시스템 텍스트 크기를 무시

        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        if (isWindowOpen) {
            settings.setJavaScriptCanOpenWindowsAutomatically(true);    // javascript가 window.open()을 사용할 수 있도록 설정(def:false)
            settings.setSupportMultipleWindows(true);
        } else {
            settings.setJavaScriptCanOpenWindowsAutomatically(false);
            settings.setSupportMultipleWindows(false);
        }
        // HTTPS > HTTP 전송시 내장 브라우저에서 block 시켜 데이터 전송이 안되는 문제(롤리팝(5.0) 이상)
        // [blocked] The page at 'https://xxx' was loaded over HTTPS, but ran insecure content from http://xxx.css': this content should also be loaded over HTTPS.
        // 혼합된 컨텐츠와 서드파티 쿠키가 설정에 따라 Webview 에서 Block 시키는 게 기본으로 변경
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
    }

}

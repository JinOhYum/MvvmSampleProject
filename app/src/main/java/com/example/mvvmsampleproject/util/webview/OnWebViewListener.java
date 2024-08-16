package com.example.mvvmsampleproject.util.webview;

import android.os.Message;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;

public interface OnWebViewListener {
    // WebChromeClient 에서 호출
    void onWebViewTitle(String url, String title);
    void onWebViewProgressChanged(String url, int newProgress);
    //    void onWebViewProgressChanged(WebView view, String url, int newProgress);
    void onWebViewCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg); // 윈도우 오픈 열기 콜백 ( 윈도우 오픈을 하는 부모웹뷰 에서 호출된다 )
    void onWebViewCloseWindow(WebView window);  // 윈도우 오픈 닫기 콜백 ( 윈도웅 오픈된 자식웹뷰 에서 호출된다 )
    void onWebViewPermissionRequest(String permission);
    // WebViewClient 에서 호출
    void onWebViewStart(WebView view, int urlType, String url);
    void onWebViewFinish(WebView view, String url);
    boolean onWebViewUrlChange(WebView view, int urlType, String url, boolean isRedirect);
    void onWebViewReceivedError(WebView view, int errorCode, String failingUrl);
    void onWebViewHttpError(WebView view, int errorCode, String failingUrl);
    void onWebViewSslError(SslErrorHandler handler);
}

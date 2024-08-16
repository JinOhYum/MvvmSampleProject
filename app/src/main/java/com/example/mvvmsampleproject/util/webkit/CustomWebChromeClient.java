package com.example.mvvmsampleproject.util.webkit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.mvvmsampleproject.util.DataUtils;
import com.example.mvvmsampleproject.util.DefineUrl;
import com.example.mvvmsampleproject.util.LogUtil;
import com.example.mvvmsampleproject.util.StringUtil;
import com.example.mvvmsampleproject.util.webview.OnWebViewListener;

import java.lang.ref.WeakReference;

public class CustomWebChromeClient extends WebChromeClient {

    private final WeakReference<Context> weakContext;
    private final OnWebViewListener webViewListener;
    private Dialog dialog;

    public CustomWebChromeClient(Context context, OnWebViewListener listener, boolean isSupportWindowOpen, WebView parentWebView) {
        this.weakContext = new WeakReference<Context>(context);
        this.webViewListener = listener;
    }

    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        LogUtil.i("onCreateWindow");
        if (DataUtils.isNotNull(webViewListener)) {
            webViewListener.onWebViewCreateWindow(view, isDialog, isUserGesture, resultMsg);
        }
        return true;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (DataUtils.isNotNull(webViewListener) && DataUtils.isNotNull(view)&& DataUtils.isNotNull(view.getUrl())) {
            webViewListener.onWebViewProgressChanged(view.getUrl(), newProgress);
        }
    }


    @Override
    public void onCloseWindow(WebView window) {
        super.onCloseWindow(window);
        if (DataUtils.isNotNull(webViewListener)) {
            window.destroy();
            webViewListener.onWebViewCloseWindow(window);
        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        //kcy1000-202206 - 앱으로볼께요이슈 : 앱에서 보기 타이틀 잘못노출되는 이슈 수정
        if (webViewListener != null && view != null && title != null) {
            String dispTitle = (TextUtils.isEmpty(title) || DefineUrl.URL_BLANK.equals(title)) ? "" : title;
            if(!TextUtils.isEmpty(dispTitle)) {
                webViewListener.onWebViewTitle(view.getUrl(), StringUtil.parseKtWebTitle(dispTitle));
            }

        }
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {

        return false;
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
        if (view != null && weakContext != null && weakContext.get() != null) {
        }
        return false;
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return super.onConsoleMessage(consoleMessage);
    }

    /**
     * dismissDialog : 다이얼로그 제거
     */
    public void dismissDialog() {
        try {
            if (dialog != null && dialog.isShowing())
                dialog.dismiss();
            dialog = null;
        } catch (RuntimeException e) {
            LogUtil.e(e.getLocalizedMessage());
        } catch (Exception e) {
            LogUtil.e(e.getLocalizedMessage());
        }
    }

}

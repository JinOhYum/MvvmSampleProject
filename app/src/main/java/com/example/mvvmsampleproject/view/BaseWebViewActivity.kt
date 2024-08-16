package com.example.mvvmsampleproject.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Message
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.webkit.PermissionRequest
import android.webkit.SslErrorHandler
import android.webkit.ValueCallback
import android.webkit.WebView
import com.example.mvvmsampleproject.api.JsBridge
import com.example.mvvmsampleproject.util.Define
import com.example.mvvmsampleproject.util.DefineCode
import com.example.mvvmsampleproject.util.LogUtil
import com.example.mvvmsampleproject.util.webkit.CustomWebChromeClient
import com.example.mvvmsampleproject.util.webkit.CustomWebViewClient
import com.example.mvvmsampleproject.util.webview.CustomWebView
import com.example.mvvmsampleproject.util.webview.OnWebViewListener

open class BaseWebViewActivity : BaseActivity() , OnWebViewListener {


    @SuppressLint("SetJavaScriptEnabled")
    fun setWebView(webView : CustomWebView, jsBridge : JsBridge,  isWindowOpen : Boolean){
        LogUtil.e("WebView setWebView ")

        val chromeClient : CustomWebChromeClient
        // [DR-2020-53286] 쇼미 서비스 설정시 파일 업로드
        chromeClient = object : CustomWebChromeClient(this, this, true, webView) {
            //kcy1000-202208 - 챗봇 음성인식 권한 추가
            override fun onPermissionRequest(request: PermissionRequest) {
                runOnUiThread { request.grant(request.resources) }
            }

            override fun onShowFileChooser(
                webView: WebView?,
                fileCallback: ValueCallback<Array<Uri?>?>,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                LogUtil.i("onShowFileChooser")
                //2310-쇼미권한 추가
                return true
            }
        }
//			chromeClient = new CustomWebChromeClient(this, this, true, webView);
        //			chromeClient = new CustomWebChromeClient(this, this, true, webView);
        webView.webChromeClient = chromeClient

        webView.webViewClient = CustomWebViewClient(webView.context , this)
        webView.settings.userAgentString = ""

        val userAgent = "; appname=ollehcs; os=OS003; MCC=; appver=08.00.00; deviceId=55Ler1d4Kw6F1NXZUdX6+TM/R8SzlU/XT8kRAMeY8FYtn91EJmwLqfNKubmu+3zK; deviceUuid=55Ler1d4Kw6F1NXZUdX6+TM/R8SzlU/XT8kRAMeY8FYtn91EJmwLqfNKubmu+3zK; model=SM-S711N; ktalk=Y; osver=14;";
        webView.initWebViewSetting(userAgent, true, applicationContext)

        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(jsBridge, "ktCsNative")
        webView.clearHistory()
        webView.clearCache(true)
        webView.isFocusable = true
        webView.isFocusableInTouchMode = true
        webView.requestFocus(View.FOCUS_DOWN)
        webView.setInitialScale(1)
        webView.setVerticalScrollbarOverlay(true)
        webView.isHorizontalScrollBarEnabled = true
        webView.isVerticalScrollBarEnabled = true
        webView.setNetworkAvailable(true)
        webView.isDrawingCacheEnabled = true
        webView.clipChildren = true
        webView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        webView.isScrollbarFadingEnabled = true
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        webView.settings.domStorageEnabled = true


        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(webView, true)
        WebView.setWebContentsDebuggingEnabled(true)

        Log.e("WebView ","setWebView 22")
    }
    fun callJavaScript(webView: WebView?, funcName: String?, vararg params: String?){
        if (webView == null)
            return

        if (TextUtils.isEmpty(funcName))
            return

        try {
            var script = "javascript:" + funcName + Define.FRONT_BRACKET
            var param = ""
            if (params.isNotEmpty()) {
                param = TextUtils.join("', '", params)
                script += "'$param'"
            }
            script += Define.BACK_BRACKET + Define.SEMI
            val javascript = script
            Thread { runOnUiThread { webView.loadUrl(javascript) } }.start()
        } catch (e: Exception) {
        }
    }

    protected fun setCookie() {
//        val cookieMap = HashMap<String, String>()
//        cookieMap["os"] = getApp().getOSType()
//        cookieMap["osver"] = getApp().getOSVersion()
//        cookieMap["appver"] = getApp().getAppVersion()
//        cookieMap["guid"] = getApp().getFcmToken(true)
//        cookieMap["appsetupctn"] = getApp().getAppEncCtn()
//        cookieMap["encUsimSvcNo"] = PreferenceUtil.getInstance(this).getUsimNo(false)
//        cookieMap["appFontSize"] = "FONT_SIZE" + PreferenceUtil.getInstance(this).getFontSize()
//        cookieMap["darkMode"] =
//            if (PreferenceUtil.getInstance(this).getDarkModeTheme()) Define.YES else Define.NO
//        CookieUtil.setCookie(DefineConfig.COOKIE_DOMAIN, cookieMap)
    }

    override fun onWebViewTitle(url: String?, title: String?) {
//        TODO("Not yet implemented")
    }

    override fun onWebViewProgressChanged(url: String?, newProgress: Int) {
    }

    override fun onWebViewCreateWindow(
        view: WebView?,
        isDialog: Boolean,
        isUserGesture: Boolean,
        resultMsg: Message?
    ) {
//        TODO("Not yet implemented")
    }

    override fun onWebViewCloseWindow(window: WebView?) {
    }

    override fun onWebViewPermissionRequest(permission: String?) {
    }

    override fun onWebViewStart(view: WebView?, urlType: Int, url: String?) {
    }

    override fun onWebViewFinish(view: WebView?, url: String?) {
    }

    override fun onWebViewUrlChange(
        view: WebView?,
        urlType: Int,
        url: String?,
        isRedirect: Boolean
    ): Boolean {
        if (urlType == DefineCode.URL_TYPE_NETWORK_DISABLE) {
//            showNetworkAvailableDialog(view, url, 0)
            return true
        }
        if (urlType == DefineCode.URL_TYPE_LOGIN) {
            view!!.stopLoading()
//            checkLoginMove(url)
            return true
        }
        if (urlType != DefineCode.URL_TYPE_HTTP) {
//            executeUrl(urlType, url)
            return true
        }
        return false
    }

    override fun onWebViewReceivedError(view: WebView?, errorCode: Int, failingUrl: String?) {
    }

    override fun onWebViewHttpError(view: WebView?, errorCode: Int, failingUrl: String?) {
    }

    override fun onWebViewSslError(handler: SslErrorHandler?) {
    }
}
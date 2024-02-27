package com.example.mvvmsampleproject.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvmsampleproject.databinding.ActivityMainBinding
import com.example.mvvmsampleproject.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    //'androidx.activity:activity-ktx:1.8.2' 라이브러리를 추가해야 사용가능 액티비티와 뷰모델의 연결을 쉽게 만들어줌
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        onObserve()
        init()
    }

    private fun init(){
        viewModel.onHttpSetAppCtnApi()

        binding.webView.webChromeClient = WebChromeClient()
        //202307-userAgent 초기화
        binding.webView.settings.userAgentString = ""

        binding.webView.addJavascriptInterface(viewModel.jsBridge, "ktCsNative")
        binding.webView.clearHistory()
        binding.webView.clearCache(true)
        binding.webView.isFocusable = true
        binding.webView.isFocusableInTouchMode = true
        binding.webView.requestFocus(View.FOCUS_DOWN)
        binding.webView.setInitialScale(1)
        binding.webView.setVerticalScrollbarOverlay(true)
        binding.webView.isHorizontalScrollBarEnabled = true
        binding.webView.isVerticalScrollBarEnabled = true
        binding.webView.setNetworkAvailable(true)
        binding.webView.isDrawingCacheEnabled = true
        binding.webView.clipChildren = true
        binding.webView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        binding.webView.isScrollbarFadingEnabled = true
        binding.webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(binding.webView, true)

        binding.webView.loadUrl("https://m.my.kt.com/cardmain/v5/a_CardMain.do")
    }

    private fun onObserve(){

        //introApiResponse 옵저버 데이터가 변경되면 반응
        viewModel.introApiResponse.observe(this){data ->
            Log.e("여기 UI 변동사항","data "+data.code)
        }
    }
}
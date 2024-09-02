package com.example.mvvmsampleproject.view

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import com.example.mvvmsampleproject.databinding.ActivityLoginBinding
import com.example.mvvmsampleproject.util.DefineCommon
import com.example.mvvmsampleproject.util.DefineConfig
import com.example.mvvmsampleproject.util.DefineField
import com.example.mvvmsampleproject.util.DefineUrl
import com.example.mvvmsampleproject.util.LogUtil
import com.example.mvvmsampleproject.viewmodel.LoginViewModel

class LoginActivity : BaseWebViewActivity() {

    private lateinit var binding : ActivityLoginBinding

    private val viewModel : LoginViewModel by viewModels()

    private lateinit var actionBar : ActionBar


    //신규 뒤로가기 기존 onBackPressed 는 Deprecated 되었음
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val returnIntent = Intent()
            returnIntent.putExtra("requestCode", viewModel.requestCode)
            returnIntent.putExtra(DefineField.PARAM_RELOAD_URL, viewModel.loginAfterUrl)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.onBackPressedDispatcher.addCallback(this , onBackPressedCallback)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root).apply {
            init()
            viewModel.setIntentData(intent)
            setWebViewSetting()
            onObserve()
        }


    }

    /**
     * MainWebView Url 로딩 시작 시점
     * **/
    override fun onWebViewStart(view: WebView?, urlType: Int, url: String?) {
        super.onWebViewStart(view, urlType, url)
        showBaseLoadingPopup()
    }

    /**
     * MainWebView Url 로딩 진행 시점
     * **/
    override fun onWebViewProgressChanged(url: String?, newProgress: Int) {
        super.onWebViewProgressChanged(url, newProgress)
        if (newProgress >= 100) {
            hideBaseLoadingPopup()
        }
    }

    /**
     * MainWebView Url 변경 여부
     * **/
    override fun onWebViewUrlChange(view: WebView?, urlType: Int, url: String?, isRedirect: Boolean): Boolean {
        return super.onWebViewUrlChange(view, urlType, url, isRedirect)
    }

    /**
     * MainWebView Url 로딩 완료 시점
     * **/
    override fun onWebViewFinish(view: WebView?, url: String?) {
        super.onWebViewFinish(view, url)
    }

    /**
     * LoginActivity 셋팅
     * **/
    private fun init(){

        setSupportActionBar(binding.layoutToolbarSub.toolbar)
        actionBar = supportActionBar!!

        actionBar.setDisplayShowCustomEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(false)
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.hide()
        binding.webview.overScrollMode = View.OVER_SCROLL_NEVER

        binding.layoutToolbarSub.btnToolbarBack.setOnClickListener {
            setResultCancel()
        }
    }

    /**
     * 옵저버 등록 함수 (새로운 로직)
     * ViewModel 에 있는 LiveData의 값이 변경되었을떄 옵저버를 통해 데이터 확인 가능
     * **/
    private fun onObserve(){

        /**
         * 뒤로가기 브릿지 호출 데이터 처리
         */
        viewModel.closeWindowJsDto.observe(this){
            LogUtil.d("closeWindowJsDto = "+it)
            if(it.reloadType == ""){
                setResultCancel()
            }
            else if(it.reloadType == "MAIN"){
                setResultReloadMain()
            }
        }

    }


    /**
     * 웹뷰 셋팅
     * **/
    private fun setWebViewSetting(){
        setWebView(binding.webview , viewModel.jsBridge , true).apply {
            binding.webview.loadUrl("https://login.kt.com/wamui/bioLogin.do?mskUserId=&passkeyonoff=0&passkeydevice=1&passkeyreg=0&lastlogintype=2")
        }
    }

    /**
     * setResultCancel : 로그인 취소 결과 설정
     */
    private fun setResultCancel() {
        if (viewModel.resultCode == Activity.RESULT_CANCELED) {
            val returnIntent = Intent()
            returnIntent.putExtra("requestCode", viewModel.requestCode)
            returnIntent.putExtra(DefineField.PARAM_RELOAD_URL, viewModel.loginAfterUrl)
            setResult(Activity.RESULT_CANCELED, returnIntent)
        }
        finish()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE, 0,0)
        } else {
            overridePendingTransition(0,0)
        }
    }

    /**
     * setResultReloadMain : 메인 Activity 리로드
     */
    private fun setResultReloadMain() {
        val returnIntent = Intent()
        returnIntent.putExtra(DefineField.PARAM_RELOAD_URL, DefineUrl.URL_MAIN)
        returnIntent.putExtra("requestCode", viewModel.requestCode)
        setResult(RESULT_FIRST_USER, returnIntent)
        finish()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE, 0,0)
        } else {
            overridePendingTransition(0,0)
        }
    }


    /**
     * changeActionBar : 액션바 변경 처리
     */
    private fun changeActionBar(url: String, isStart: Boolean) {
//        if (TextUtils.isEmpty(url) || getApp().isHideActionBar(url) || DefineUrl.URL_BLANK.equals(
//                url
//            )
//        ) {
//            actionBar.hide()
//        } else {
//            if (!isStart) {
//                actionBar.show()
//            }
//        }
    }
}
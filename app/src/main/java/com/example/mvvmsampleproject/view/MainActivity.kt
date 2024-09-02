package com.example.mvvmsampleproject.view

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.mvvmsampleproject.R
import com.example.mvvmsampleproject.databinding.ActivityMainBinding
import com.example.mvvmsampleproject.util.Constant.BENEFIT_TAB
import com.example.mvvmsampleproject.util.Constant.CHATBOT_TAB
import com.example.mvvmsampleproject.util.Constant.MENU_TAB
import com.example.mvvmsampleproject.util.Constant.MY_TAB
import com.example.mvvmsampleproject.util.Constant.NOT_TAB
import com.example.mvvmsampleproject.util.Constant.SHOP_TAB
import com.example.mvvmsampleproject.util.DefineCode
import com.example.mvvmsampleproject.util.DefineCommon
import com.example.mvvmsampleproject.util.DefineConfig.Companion.URL_BENEFIT
import com.example.mvvmsampleproject.util.DefineConfig.Companion.URL_CHATBOT
import com.example.mvvmsampleproject.util.DefineConfig.Companion.URL_MAIN
import com.example.mvvmsampleproject.util.DefineConfig.Companion.URL_MENU
import com.example.mvvmsampleproject.util.DefineConfig.Companion.URL_SHOP
import com.example.mvvmsampleproject.util.DefineField
import com.example.mvvmsampleproject.util.DefineUrl
import com.example.mvvmsampleproject.util.LogUtil
import com.example.mvvmsampleproject.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseWebViewActivity() {

    private lateinit var binding : ActivityMainBinding

    //MainViewModel 등록
    private val viewModel : MainViewModel by viewModels()

    //신규 뒤로가기 기존 onBackPressed 는 Deprecated 되었음
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            LogUtil.d("뒤로가기")
            if(binding.wbMain.url == URL_MENU){
                binding.wbMain.goBack()
            }
            else{
                // 뒤로가기 시 실행할 코드
                goBack()
            }
        }
    }

    private val TAG ="MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.onBackPressedDispatcher.addCallback(this , onBackPressedCallback)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root).apply {
            init()
            setWebViewSetting()
            setOnBottomClick()
            onObserve()
        }

    }

    override fun onResume() {
        super.onResume()
        setStatusBarColor(R.color.white)
    }

    /**
     * BaseActivity 에서 startActivityForResult 를 통해 데이터 리턴 받는곳
     * **/
    override fun doBaseActivityResult(data: Intent?) {
        super.doBaseActivityResult(data)
        LogUtil.d("여기 Main")
        if(data != null){
            val requestCode = data.getIntExtra("requestCode", DefineCommon.REQ_ERROR)
            when(requestCode){

                DefineCommon.REQ_ACT_LOGIN->{
                    LogUtil.d("여기 "+data.getStringExtra(DefineField.PARAM_RELOAD_URL))

                }

            }
        }
    }

    /**
     * MainWebView Url 로딩 시작 시점
     * **/
    override fun onWebViewStart(view: WebView?, urlType: Int, url: String?) {
        super.onWebViewStart(view, urlType, url)
        LogUtil.d(TAG,"onWebViewStart "+url)
        showBaseLoadingPopup()
    }

    /**
     * MainWebView Url 로딩 진행 시점
     * **/
    override fun onWebViewProgressChanged(url: String?, newProgress: Int) {
        if (newProgress >= 100) {
            hideBaseLoadingPopup()
            setBottomTabPosition(url.toString())
        }
    }

    override fun onWebViewUrlChange(view: WebView?, urlType: Int, url: String?, isRedirect: Boolean): Boolean {

        // 로그인 요청이 들어올 경우
        if (urlType == DefineCode.URL_TYPE_LOGIN) {
//            widgetLogin = true
            moveLogin(null.toString(), url.toString(), null, false, null)
            return true
        }

        return false
    }

    /**
     * MainWebView Url 로딩 완료 시점
     * **/
    override fun onWebViewFinish(view: WebView?, url: String?) {
        super.onWebViewFinish(view, url)
        LogUtil.d("여기 onWebViewFinish ="+url)
    }

    /**
     * MainActivity 셋팅
     * **/
    private fun init(){
        viewModel.onHttpIntroApi()
        viewModel.setBottomData(0,binding.mainTabBar.ivBottomMenu , binding.mainTabBar.menuText  , R.raw.menu_icon , R.raw.menu_icon_dark , R.mipmap.menu_icon)
        viewModel.setBottomData(1,binding.mainTabBar.ivBottomBenefit , binding.mainTabBar.benefitsText  , R.raw.benefit_icon , R.raw.benefit_icon_dark ,R.mipmap.benefit_icon)
        viewModel.setBottomData(2,binding.mainTabBar.ivBottomHome , binding.mainTabBar.myText  , R.raw.home_icon , R.raw.home_icon_dark , R.mipmap.home_icon)
        viewModel.setBottomData(3,binding.mainTabBar.ivBottomShop , binding.mainTabBar.shopText  , R.raw.shop_icon , R.raw.shop_icon_dark , R.mipmap.shop_icon)
        viewModel.setBottomData(4,binding.mainTabBar.ivBottomChatbot , binding.mainTabBar.chatbotText  , R.raw.chat_icon , R.raw.chat_icon_dark  , R.mipmap.chatbot_icon)

        viewModel.setBottomTabPosition(MY_TAB)
        setBottomGifLoad(true, getBottomTabPosition(MY_TAB))


        binding.swiperefreshlayout.setOnRefreshListener {
            //202302-새로고침 이슈 수정
            val url = binding.wbMain.url
            if (url!!.contains(DefineUrl.URL_MAIN) || url.contains(DefineUrl.URL_BENEFIT) || url.contains(DefineUrl.URL_SHOP)) {
                // 202303-웹뷰 스크립트 오류시 새로고침 안되는 이슈발생으로
                binding.wbMain.loadUrl(url)
            } else if (url.contains(DefineUrl.URL_MAIN_V2)) {
                binding.wbMain.loadUrl(DefineUrl.URL_MAIN)
            }

            binding.swiperefreshlayout.isRefreshing = false
        }

    }

    /**
     * 옵저버 등록 함수 (새로운 로직)
     * ViewModel 에 있는 LiveData의 값이 변경되었을떄 옵저버를 통해 데이터 확인 가능
     * **/
    private fun onObserve(){

        /**
         * 인트로 api 값 처리 옵저버
         * **/
        viewModel.introApiResponse.observe(this){data ->
            LogUtil.d("introApiResponse observe = "+data.code)
        }

        /**
         * 특정 브릿지 처리 옵저버
         * **/
        viewModel.openLoginViewJsDto.observe(this){data ->
            LogUtil.d("openLoginViewJsDto observe = $data")
        }

        /**
         * url 변경 감지 옵저버
         * **/
        viewModel.mainWebViewUrl.observe(this){data ->
            LogUtil.d("mainActivityData observe = $data")
            onMainWebViewLoadUrl(data)
        }

        /**
         * 하단탭바 변경 감지 롭저버
         * **/
        viewModel.bottomTabPosition.observe(this){data ->
            //하단탭바 감추기 여부
            isBottomTabVisible(data)
        }
    }

    /**
     * 하단 탭바 클릭 로직 함수 (새로운 로직)
     * **/
    private fun setOnBottomClick(){
        binding.mainTabBar.llMenu.setOnClickListener {
            onMainWebViewLoadUrl(URL_MENU)
            setBottomTabPosition(URL_MENU)
        }
        binding.mainTabBar.llBenefit.setOnClickListener {
            onMainWebViewLoadUrl(URL_BENEFIT)
            setBottomTabPosition(URL_BENEFIT)
        }
        binding.mainTabBar.llHome.setOnClickListener {
            onMainWebViewLoadUrl(URL_MAIN)
            setBottomTabPosition(URL_MAIN)
        }
        binding.mainTabBar.llShop.setOnClickListener {
            onMainWebViewLoadUrl(URL_SHOP)
            setBottomTabPosition(URL_SHOP)
        }
        binding.mainTabBar.llChatBot.setOnClickListener {
//            viewModel.setBottomTabPosition(CHATBOT_TAB)
//            viewModel.setMainWebViewUrl(URL_BENEFIT)
        }
    }

    /**
     * 마이케이티앱 웹뷰로드 로직 함수(기존 마이케이티앱 로직)
     * **/
    private fun onMainWebViewLoadUrl(url : String){
        if (TextUtils.isEmpty(url)) {
            return
        }
//        setCookie()
        binding.wbMain.loadUrl(url)
    }

    /**
     * 하단 팝업 GIF 재생(기존 마이케이티앱 로직)
     * **/
    private fun setBottomGifLoad(isDark : Boolean ,position : Int){
        Glide.with(this)
            .asGif()
            .load(viewModel.mainBottomData[position].gifNameRight)
            .listener(object : RequestListener<GifDrawable>{
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<GifDrawable>?, isFirstResource: Boolean): Boolean {
                    return false
                }

                override fun onResourceReady(resource: GifDrawable?, model: Any?, target: Target<GifDrawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    if (resource != null && !resource.isRunning) {
                        resource.setLoopCount(1)
                        resource.registerAnimationCallback(object :
                            Animatable2Compat.AnimationCallback() {
                            override fun onAnimationEnd(drawable: Drawable) {
                                super.onAnimationEnd(drawable)
                                resource.stop()
                                resource.startFromFirstFrame()
//                                if (position == 4) {
//                                    onMoveChatBot(position)
//                                }
                            }
                        })
                    }
                    return false
                }

        }).into(viewModel.mainBottomData[position].imageView)

        setBottomImg(position)
    }

    /**
     * 하단 탭바 활성화 된 탭 제외 나머지 탭 이미지 비활성화 (기존 마이케이티앱 로직)
     * **/
    private fun setBottomImg(position: Int) {
        for (i in 0..4) {
            if (i != position) {
                viewModel.mainBottomData[i].imageView
                    .setImageResource(viewModel.mainBottomData[i].baseImageName)
            }
        }
    }

    /**
     * 웹뷰 셋팅 (기존 마이케이티앱 + 신규 로직 추가)
     * **/
    private fun setWebViewSetting(){
        setWebView(binding.wbMain , viewModel.jsBridge , true).apply {
            onMainWebViewLoadUrl(viewModel.mainWebViewUrl.value!!)
        }
    }

    /**
     * 하단탭바 감추기 여부 (신규 로직 url 이 아닌 탭바 기준)
     * **/
    private fun isBottomTabVisible(bottomTab : String){
        LogUtil.d("isBottomTabVisible = "+bottomTab)
        //메뉴 , 챗봇 , 탭이 불필요한 화면이면 하단 탭바 숨김
        if(bottomTab == MENU_TAB || bottomTab == NOT_TAB || bottomTab == CHATBOT_TAB ){
            binding.mainTabBar.root.visibility = View.GONE
        }
        else{
            binding.mainTabBar.root.visibility = View.VISIBLE
        }

        if(bottomTab != NOT_TAB){
            setBottomGifLoad(true, getBottomTabPosition(bottomTab))
        }
    }

    /**
     * Url 을 기준으로 해당 탭바 position 값 변경
     * **/
    private fun setBottomTabPosition(url : String){
        LogUtil.d("setBottomTabPosition = " + url)

        when(url){
            URL_MENU->{//메뉴
                viewModel.setBottomTabPosition(MENU_TAB)
            }
            URL_BENEFIT->{//혜택
                viewModel.setBottomTabPosition(BENEFIT_TAB)
            }
            URL_MAIN->{//홈
                viewModel.setBottomTabPosition(MY_TAB)
            }
            URL_SHOP->{//샵
                viewModel.setBottomTabPosition(SHOP_TAB)
            }
            URL_CHATBOT ->{//챗봇
//                viewModel.setMainActivityData(MainActivityData(4, url, SHOP_TAB))
            }
            else ->{//하단탭바가 필요없는 url
                viewModel.setBottomTabPosition(NOT_TAB)
            }
        }
    }

    /**
     * 바텀 위치에 따른 UI 셋팅 함수
     * **/
    private fun getBottomTabPosition(bottomTab : String) : Int{

        when(bottomTab){
            MENU_TAB->{//메뉴
                return 0
            }
            BENEFIT_TAB->{//혜택
                return 1
            }
            MY_TAB->{//홈
                return 2
            }
            SHOP_TAB->{//샵
                return 3
            }
            CHATBOT_TAB->{//챗봇
                return 4
            }
            else->{//그 외 페이지
                return -1
            }
        }

    }


}
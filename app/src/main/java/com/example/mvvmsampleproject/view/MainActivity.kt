package com.example.mvvmsampleproject.view

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
import com.example.mvvmsampleproject.data.model.MainActivityData
import com.example.mvvmsampleproject.databinding.ActivityMainBinding
import com.example.mvvmsampleproject.util.Constant.BENEFIT_TAB
import com.example.mvvmsampleproject.util.Constant.CHATBOT_TAB
import com.example.mvvmsampleproject.util.Constant.MENU_TAB
import com.example.mvvmsampleproject.util.Constant.MY_TAB
import com.example.mvvmsampleproject.util.Constant.SHOP_TAB
import com.example.mvvmsampleproject.util.DefineConfig.Companion.URL_BENEFIT
import com.example.mvvmsampleproject.util.DefineConfig.Companion.URL_CHATBOT
import com.example.mvvmsampleproject.util.DefineConfig.Companion.URL_MAIN
import com.example.mvvmsampleproject.util.DefineConfig.Companion.URL_MENU
import com.example.mvvmsampleproject.util.DefineConfig.Companion.URL_SHOP
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
     * MainWebView Url 로딩 시작 시점
     * **/
    override fun onWebViewStart(view: WebView?, urlType: Int, url: String?) {
        super.onWebViewStart(view, urlType, url)
        /**
         * 2407 인트로가 완전히 제거 되었을때만 실행
         * 인트로가 완전히 벗겨지지 않은 상태에서 해당 로직 실행시
         * 앱실행시 깜빡이는 현상 있음
         */
        showBaseLoadingPopup()
    }

    /**
     * MainWebView Url 로딩 진행 시점
     * **/
    override fun onWebViewProgressChanged(url: String?, newProgress: Int) {
        LogUtil.d("여기 onWebViewProgressChanged ="+url + " "+newProgress)
        if (newProgress >= 100) {
            hideBaseLoadingPopup()
            if(url != viewModel.mainActivityData.value!!.url){
                setBottomTabData(url.toString() , false)
            }
        }
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

        setBottomGifLoad(true, viewModel.mainActivityData.value!!.mainBottomPosition)


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

        //introApiResponse 옵저버 데이터가 변경되면 반응
        viewModel.introApiResponse.observe(this){data ->
            LogUtil.d("introApiResponse observe = "+data.code)
        }

        viewModel.openLoginViewJsDto.observe(this){data ->
            LogUtil.d("openLoginViewJsDto observe = $data")
        }

        viewModel.mainActivityData.observe(this){data ->
            LogUtil.d("mainActivityData observe = $data")
            //하단탭바 감추기 여부
            isBottomTabVisible(data)
            if(data.mainBottomIsClick){
                onMainWebViewLoadUrl(data.url)
            }

        }
    }

    /**
     * 하단 탭바 클릭 로직 함수 (새로운 로직)
     * **/
    private fun setOnBottomClick(){
        binding.mainTabBar.llMenu.setOnClickListener {
            setBottomTabData(URL_MENU , true)
        }
        binding.mainTabBar.llBenefit.setOnClickListener {
            setBottomTabData(URL_BENEFIT , true)
        }
        binding.mainTabBar.llHome.setOnClickListener {
            setBottomTabData(URL_MAIN , true)
        }
        binding.mainTabBar.llShop.setOnClickListener {
            setBottomTabData(URL_SHOP , true)
        }
        binding.mainTabBar.llChatBot.setOnClickListener {
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
            onMainWebViewLoadUrl(viewModel.mainActivityData.value!!.url)
        }
    }

    /**
     * 하단탭바 감추기 여부 (기존 마이케이티앱 + 신규 로직 추가)
     * **/
    private fun isBottomTabVisible(data : MainActivityData){

        //하단탭바 감추기 여부
        if (!data.url.contains(URL_MENU)) {
            if (data.url.contains(URL_MAIN) || data.url.contains(URL_BENEFIT) || data.url.contains(URL_SHOP)) {
                binding.mainTabBar.root.visibility = View.VISIBLE
            } else {
                binding.mainTabBar.root.visibility = View.GONE
            }
        }
        else{
            binding.mainTabBar.root.visibility = View.GONE
        }
        if(data.mainBottomPosition != -1){
            setBottomGifLoad(true, data.mainBottomPosition)
        }
    }

    /**
     * 웹뷰애 실행되는 url 데이터 처리
     * 메뉴,혜택,홈,샵,챗봇 을 제외한 나머지 url 은 position 을 -1 로 지정한다
     * 액티비티에서는 값을 가지고 있으면 안되고 값은 ViewModel 로 보내 LiveData 저장후
     * LiveData 의 옵저버를 통해 값이 변경된걸 확인한다
     * **/
    private fun setBottomTabData(url : String , isClick : Boolean){
        when(url){
            URL_MENU->{//메뉴
                viewModel.setMainActivityData(MainActivityData(0, url, MENU_TAB , isClick))
            }
            URL_BENEFIT->{//혜택
                viewModel.setMainActivityData(MainActivityData(1, url, BENEFIT_TAB , isClick))
            }
            URL_MAIN->{//홈
                viewModel.setMainActivityData(MainActivityData(2, url, MY_TAB , isClick))
            }
            URL_SHOP->{//샵
                viewModel.setMainActivityData(MainActivityData(3, url, SHOP_TAB , isClick))
            }
            URL_CHATBOT ->{//챗봇
//                viewModel.setMainActivityData(MainActivityData(4, url, SHOP_TAB))
            }
            else ->{
                viewModel.setMainActivityData(MainActivityData(-1, url, "" , isClick))
            }
        }
    }


}
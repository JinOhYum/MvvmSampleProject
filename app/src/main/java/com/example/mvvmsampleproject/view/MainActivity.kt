package com.example.mvvmsampleproject.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.WebBackForwardList
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
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
import com.example.mvvmsampleproject.util.DataUtils
import com.example.mvvmsampleproject.util.DefineConfig.Companion.URL_BENEFIT
import com.example.mvvmsampleproject.util.DefineConfig.Companion.URL_CHATBOT
import com.example.mvvmsampleproject.util.DefineConfig.Companion.URL_MAIN
import com.example.mvvmsampleproject.util.DefineConfig.Companion.URL_MENU
import com.example.mvvmsampleproject.util.DefineConfig.Companion.URL_SHOP
import com.example.mvvmsampleproject.util.DefineUrl
import com.example.mvvmsampleproject.util.LogUtil
import com.example.mvvmsampleproject.util.SnackBarOption
import com.example.mvvmsampleproject.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseWebViewActivity() {

    private lateinit var binding : ActivityMainBinding

    //'androidx.activity:activity-ktx:1.8.2' 라이브러리를 추가해야 사용가능 액티비티와 뷰모델의 연결을 쉽게 만들어줌
    private val viewModel : MainViewModel by viewModels()

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

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root).apply {
            init()
            setOnBottomClick()
            setWebViewSetting()
            onObserve()
        }

    }

    override fun onWebViewProgressChanged(url: String?, newProgress: Int) {
        LogUtil.d("여기 onWebViewProgressChanged ="+url)
        if (newProgress >= 100) {
            viewModel.setMainActivityUrl(url.toString())
        }
    }

    override fun onWebViewFinish(view: WebView?, url: String?) {
        super.onWebViewFinish(view, url)
        LogUtil.d("여기 onWebViewFinish ="+url)
    }

    //MainActivity 셋팅
    private fun init(){
        this.onBackPressedDispatcher.addCallback(this , onBackPressedCallback)
        viewModel.onHttpSetAppCtnApi()

        viewModel.setBottomData(0,binding.mainTabBar.ivBottomMenu , binding.mainTabBar.menuText  , R.raw.menu_icon , R.raw.menu_icon_dark , R.mipmap.menu_icon)
        viewModel.setBottomData(1,binding.mainTabBar.ivBottomBenefit , binding.mainTabBar.benefitsText  , R.raw.benefit_icon , R.raw.benefit_icon_dark ,R.mipmap.benefit_icon)
        viewModel.setBottomData(2,binding.mainTabBar.ivBottomHome , binding.mainTabBar.myText  , R.raw.home_icon , R.raw.home_icon_dark , R.mipmap.home_icon)
        viewModel.setBottomData(3,binding.mainTabBar.ivBottomShop , binding.mainTabBar.shopText  , R.raw.shop_icon , R.raw.shop_icon_dark , R.mipmap.shop_icon)
        viewModel.setBottomData(4,binding.mainTabBar.ivBottomChatbot , binding.mainTabBar.chatbotText  , R.raw.chat_icon , R.raw.chat_icon_dark  , R.mipmap.chatbot_icon)

        setBottomGifLoad(true, viewModel.mainActivityData.value!!.mainBottomPosition)
    }

    //하단 탭바 클릭 셋팅
    private fun setOnBottomClick(){
        binding.mainTabBar.llMenu.setOnClickListener {
            viewModel.setMainActivityData(MainActivityData(viewModel.mainActivityData.value!!.mainBottomPosition,URL_MENU,MENU_TAB))
            setMainTabBarLoadUrl(URL_MENU)
        }
        binding.mainTabBar.llBenefit.setOnClickListener {
            viewModel.setMainActivityData(MainActivityData(1, URL_BENEFIT, BENEFIT_TAB))
            setMainTabBarLoadUrl(URL_BENEFIT)
        }
        binding.mainTabBar.llHome.setOnClickListener {
            viewModel.setMainActivityData(MainActivityData(2, URL_MAIN, MY_TAB))
            setMainTabBarLoadUrl(URL_MAIN)
        }
        binding.mainTabBar.llShop.setOnClickListener {
            viewModel.setMainActivityData(MainActivityData(3, URL_SHOP, SHOP_TAB))
            setMainTabBarLoadUrl(URL_SHOP)
        }
        binding.mainTabBar.llChatBot.setOnClickListener {
            viewModel.setMainActivityData(MainActivityData(viewModel.mainActivityData.value!!.mainBottomPosition, URL_CHATBOT, CHATBOT_TAB))
            setMainTabBarLoadUrl(URL_CHATBOT)
        }
    }

    //하단탭바 클릭시 이동할 url
    private fun setMainTabBarLoadUrl(url: String) {
//        mSetBottomMenu = false
        LogUtil.d("여기 setMainTabBarLoadUrl = "+url)
        when (url) {
            URL_MENU -> {
//                mOnAllMenu = true
                if (viewModel.mainActivityData.value!!.mBottomNavigation == SHOP_TAB || viewModel.mainActivityData.value!!.mBottomNavigation == CHATBOT_TAB) {
                    onMainWebViewLoadUrl(URL_MENU)
                } else {
                    callJavaScript(binding.wbMain, "a_AllMenu.showMenu")
                }
            }
            URL_BENEFIT ->{
                onMainWebViewLoadUrl(URL_BENEFIT)
            }
            URL_MAIN ->{
                onMainWebViewLoadUrl(URL_MAIN)
            }
            URL_SHOP -> {
//                showBaseLoadingPopup()
                onMainWebViewLoadUrl(URL_SHOP)
            }
            URL_CHATBOT ->{
//                moveChatbot()
            }
        }
    }

    //마이케이티앱 웹뷰로드 로직 함수
    private fun onMainWebViewLoadUrl(url : String){
        if (TextUtils.isEmpty(url)) {
            return
        }
//        setCookie()
        binding.wbMain.loadUrl(url)
    }
    //하단 팝업 GIF 재생
    private fun setBottomGifLoad(isDark : Boolean ,position : Int){
        Glide.with(this)
            .asGif()
            .load(viewModel.mainBottomData[position].gifNameDark)
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

    private fun setBottomImg(position: Int) {
        for (i in 0..4) {
            if (i != position) {
                viewModel.mainBottomData[i].imageView
                    .setImageResource(viewModel.mainBottomData[i].baseImageName)
            }
        }
    }
    //웹뷰 셋팅
    private fun setWebViewSetting(){
        setWebView(binding.wbMain , viewModel.jsBridge , true)
        binding.wbMain.loadUrl(URL_MAIN)
    }


    //옵저버 등록 함수
    private fun onObserve(){

        //introApiResponse 옵저버 데이터가 변경되면 반응
        viewModel.introApiResponse.observe(this){data ->
            LogUtil.d("여기 UI 변동사항 "+data.code)
        }

        viewModel.openLoginViewJsDto.observe(this){data ->
            LogUtil.d("여기 UI 변동사항 $data")
        }

        viewModel.mainActivityData.observe(this){data ->
            LogUtil.d("여기 Main Util 데이터 변동사항 data $data")

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

            setBottomGifLoad(true, data.mainBottomPosition)
        }
    }

    //현재 마이케이티앱 goBack 로직
    private fun goBack() {
        LogUtil.i("goBack")
        if (DataUtils.isNotNull(binding.wbMain) && canGoBack()) {
            val lastHistory: String = getLastHistory()
            val curUrl: String = binding.wbMain.url.toString()
            // 패밀리박스 가입단계에서 Back버튼 클릭시 가입중단 안내 팝업 노출을 위해 체크
            if (curUrl.contains(DefineUrl.URL_FAMILY_BOX_JOIN)) {
                lifecycleScope.launch(Dispatchers.Main) {
                    binding.wbMain.loadUrl("javascript:joinExitConfirm()")
                }
                return
            }
            if (!TextUtils.isEmpty(lastHistory) && (DefineUrl.URL_BLANK == lastHistory || !TextUtils.isEmpty(
                    curUrl
                ) && curUrl == lastHistory)
            ) {
                if (binding.wbMain.canGoBackOrForward(-2)) {
                    binding.wbMain.goBackOrForward(-2)
                } else {
                    binding.wbMain.goBack()
                }
            } else if (checkGoBackForward(curUrl)) { // [DR-2019-25911] Android IPIN 화면 뒤로가기 오류 수정
                if (binding.wbMain.canGoBackOrForward(-2)) {
                    binding.wbMain.goBackOrForward(-2)
                } else {
                    binding.wbMain.goBack()
                }
            } else {
                binding.wbMain.goBack()
            }
        }
    }
    fun checkGoBackForward(curUrl: String): Boolean {
        if (TextUtils.isEmpty(curUrl)) return false
        val urls = DefineUrl.URL_GO_BACK_FORWARD
        for (url in urls) {
            if (curUrl.contains(url!!)) {
                return true
            }
        }
        return false
    }

    /**
     * getLastHistory : 마지막 페이지의 url 조회
     */
    fun getLastHistory(): String {
        val list: WebBackForwardList = binding.wbMain.copyBackForwardList()
        if (list.size <= 1) return ""
        // 현재 인덱스
        val idx = list.currentIndex
        return if (idx < 1) "" else list.getItemAtIndex(idx - 1).url
        // 인덱스로 URL 조회
    }
    /**
     * canGoBack : 뒤로가기 가능여부 확인
     */
    fun canGoBack(): Boolean {
        var hasBack = false
        if (binding.wbMain != null) {
            hasBack = binding.wbMain.canGoBack()
        }
        return hasBack
    }
}
package com.example.mvvmsampleproject.view

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mvvmsampleproject.dialog.CommonLoadingPopup
import com.example.mvvmsampleproject.util.DataUtils
import com.example.mvvmsampleproject.util.DefineCommon
import com.example.mvvmsampleproject.util.DefineField
import com.example.mvvmsampleproject.util.LogUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() , SensorEventListener {

    private var baseLoading: Dialog? = null

    /**
     * startActivityForResult 는 OS 11 부터 Deprecated 되어
     * androidx 에서 제공되는 ActivityResultLauncher , registerForActivityResult() 사용 권장
     * **/
    private val baseActivityResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            /**
             * startActivityForResult 에서 받은 data 값을 받아 doActivityResult 함수에 전달
             * doActivityResult 함수는 오버라이드 함수이며 상속받고 있는 서브 클래스에서 사용가능
             * **/
            doBaseActivityResult(data)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    /**
     * open 으로 오버라이드 함수로 선언
     * **/
    open fun doBaseActivityResult(data: Intent?) {

    }

    override fun onSensorChanged(p0: SensorEvent?) {

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    /**
     * moveLogin : 로그인 웹뷰 이동
     */
    protected fun moveLogin(loginUrl: String, loginAfterUrl: String, loginAfterClass: Class<*>?, isAutoLogin: Boolean, fromClass: Class<*>?) {
        LogUtil.i("moveLogin + $loginUrl////$loginAfterUrl////$loginAfterClass")
        if (isFinishing || isDestroyed) return
        val intent = Intent(this, LoginActivity::class.java)
        // 자동 로그인 여부
        intent.putExtra(DefineField.PARAM_LOGIN_AUTO, isAutoLogin)
        // 로그인 웹뷰 landing url
        if (!TextUtils.isEmpty(loginUrl)) {
            intent.putExtra(DefineField.PARAM_LOGIN_URL, loginUrl)
        }
        // 로그인 이후 부모 Activity(호출한 Activity) 이동할 URL
        if (!TextUtils.isEmpty(loginAfterUrl)) {
            intent.putExtra(DefineField.PARAM_LOGIN_AFTER_URL, loginAfterUrl)
        }
        // 로그인 이후 이동할 Activity
        if (loginAfterClass != null) {
            intent.putExtra(DefineField.PARAM_LOGIN_AFTER_ACTIVITY, loginAfterClass.name)
        }
        // 로그인 Activity 호출한 클래스명
        if (fromClass != null) {
            intent.putExtra(DefineField.PARAM_LOGIN_FROM_CLASS, fromClass.name)
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.putExtra("requestCode", DefineCommon.REQ_ACT_LOGIN)

        /**
         * 신규 startActivityForResult 방식
         * **/
        baseActivityResultLauncher.launch(intent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, 0,0)
        } else {
            overridePendingTransition(0,0)
        }
    }

    /**
     * showLoadingPopup : 로딩 팝업을 호출
     */
    fun showBaseLoadingPopup() {
        if (isFinishing || isDestroyed) return
        if (baseLoading == null) {
            baseLoading = CommonLoadingPopup(this)
        }
        if (!isFinishing && !baseLoading?.isShowing!!) {
            LogUtil.i("로딩 팝업을 호출")
            baseLoading!!.show()
        }
    }

    /**
     * hideLoadingPopup : 로딩 팝업을 종료
     */
    fun hideBaseLoadingPopup() {
        if (baseLoading != null && baseLoading!!.isShowing) {
            LogUtil.i("로딩 팝업을 종료")
            baseLoading!!.dismiss()
        }
    }

    /**
     * setStatusBarColor : 상태바 색상 변경
     */
    protected fun setStatusBarColor(idColor: Int) {
        val window = window
        val view = window.decorView
        if (DataUtils.isNotNull(view)) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            //202306-상태바색상변경
            when (ContextCompat.getColor(this, idColor)) {
                Color.WHITE -> {
                    window.statusBarColor = ContextCompat.getColor(this, idColor)
                    view.windowInsetsController?.setSystemBarsAppearance(
                        APPEARANCE_LIGHT_STATUS_BARS,
                        APPEARANCE_LIGHT_STATUS_BARS
                    )
                }
                Color.BLACK -> {
                    window.statusBarColor = ContextCompat.getColor(this, idColor)
                    view.windowInsetsController?.setSystemBarsAppearance(
                        0,
                        APPEARANCE_LIGHT_STATUS_BARS
                    )
                }
            }
        }
    }

}
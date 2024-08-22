package com.example.mvvmsampleproject.view

import android.app.Dialog
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.os.Bundle
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mvvmsampleproject.dialog.CommonLoadingPopup
import com.example.mvvmsampleproject.util.DataUtils
import com.example.mvvmsampleproject.util.LogUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() , SensorEventListener {

    private var baseLoading: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onSensorChanged(p0: SensorEvent?) {

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

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
package com.example.mvvmsampleproject.util

import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import kotlin.math.min

object DifferentResolutionChecker {
    private const val GALEXY_TAB7_S = "SHW-M180S"
    private const val GALEXY_TAB7_K = "SHW-M180K"
    private const val GALEXY_TAB7_L = "SHW-M180L"
    private const val GALEXY_TAB7_W = "SHW-M180W"
    private const val GALEXY_TAB77_S = "SHV-E150S"
    private const val GALEXY_TAB89_S = "SHV-E140S"
    private const val GALEXY_TAB89_K = "SHV-E140K"
    private const val GALEXY_TAB89_L = "SHV-E140L"
    private const val GALEXY_TAB101_S = "SHW-M380S"
    private const val GALEXY_TAB101_K = "SHW-M380K"
    private const val GALEXY_TAB101_W = "SHW-M380W"
    private const val GALEXY_NOTE_101_S = "SHW-M480S"
    private const val GALEXY_NOTE_101_K = "SHW-M480K"
    private const val GALEXY_NOTE_101_W = "SHW-M480W"
    private const val GALEXY_NOTE_LTE_101_S = "SHV-E230S"
    private const val GALEXY_NOTE_LTE_101_K = "SHV-E230K"
    private const val GALEXY_NOTE_LTE_101_L = "SHV-E230L"

    private const val NEXUS_7 = "ME370T"

    // [20131107] 탭 모델 추가
    private const val GALEXY_TAB_LTE_101 = "SM-P605"
    private const val GALEXY_NOTE_101_WIFI = "SHW-M485W" // 스마트홈패드2

    private const val GALEXY_TAB89_WIFI = "SHW-M305W" // 갤럭시탭 8.9 (wifi버전)

    private const val HTC_FLYER_4G = "HTC-P515E" // 플라이어 4G

    private const val MAGIC_ALBUM = "ESP_ES101-107W" // 매직앨범(Enspert)

    private const val IDENTITI_TAB = "ESP_E201K" // 아이덴티티 탭

    private const val IDENTITI_CRON = "ESP_E301BK" // 아이덴티티 크론

    private const val KPAD = "ES901-008W" // KPAD

    private const val ESP_ES101_109W = "ESP_ES101-109W"

    // [20141023] 추가
    private const val SH_T805K = "SH-T805K"

    // public static final int DENSITY_MEDIUM_WIDTH = 320;
    private const val DENSITY_HIGH_WIDTH = 480
    private val SCALE: Float = DisplayUtil.getScreenWidth() / DENSITY_HIGH_WIDTH.toFloat()

    fun setAutoSize(view: View?) {
        if (SCALE > 1.0f && DisplayUtil.getDPI().toInt() == DisplayMetrics.DENSITY_HIGH && view != null) {
            val width = (view.background.intrinsicWidth * SCALE).toInt()
            val height = (view.background.intrinsicHeight * SCALE).toInt()
            if (min(width.toDouble(), height.toDouble()) > 0) {
                if (view.layoutParams is RelativeLayout.LayoutParams) {
                    val params = RelativeLayout.LayoutParams(width, height)
                    view.layoutParams = params
                } else {
                    val params = ViewGroup.LayoutParams(width, height)
                    view.layoutParams = params
                }
                view.requestLayout()
            }
        }
    }

    fun getScale(): Float {
        return SCALE
    }

    fun isGalexyTab(): Boolean {
        val PhoneModel = Build.MODEL
        return PhoneModel.equals(GALEXY_TAB7_S, ignoreCase = true) || PhoneModel.equals(
            GALEXY_TAB7_K,
            ignoreCase = true
        ) || PhoneModel.equals(GALEXY_TAB7_L, ignoreCase = true) || PhoneModel.equals(
            GALEXY_TAB7_W,
            ignoreCase = true
        ) || PhoneModel.equals(GALEXY_TAB77_S, ignoreCase = true) || PhoneModel.equals(
            GALEXY_TAB89_S,
            ignoreCase = true
        ) || PhoneModel.equals(GALEXY_TAB89_K, ignoreCase = true) || PhoneModel.equals(
            GALEXY_TAB89_L,
            ignoreCase = true
        ) || PhoneModel.equals(GALEXY_TAB101_S, ignoreCase = true) || PhoneModel.equals(
            GALEXY_TAB101_K,
            ignoreCase = true
        ) || PhoneModel.equals(GALEXY_TAB101_W, ignoreCase = true) || PhoneModel.equals(
            GALEXY_NOTE_101_S,
            ignoreCase = true
        ) || PhoneModel.equals(GALEXY_TAB101_K, ignoreCase = true) || PhoneModel.equals(
            GALEXY_NOTE_101_W,
            ignoreCase = true
        ) || PhoneModel.equals(NEXUS_7, ignoreCase = true) || PhoneModel.equals(
            GALEXY_NOTE_101_K,
            ignoreCase = true
        ) || PhoneModel.equals(GALEXY_NOTE_LTE_101_S, ignoreCase = true) || PhoneModel.equals(
            GALEXY_NOTE_LTE_101_K,
            ignoreCase = true
        ) || PhoneModel.equals(GALEXY_NOTE_LTE_101_L, ignoreCase = true) || PhoneModel.startsWith(
            GALEXY_TAB_LTE_101
        ) || PhoneModel.equals(GALEXY_NOTE_101_WIFI, ignoreCase = true) || PhoneModel.equals(
            GALEXY_TAB89_WIFI,
            ignoreCase = true
        ) || PhoneModel.equals(HTC_FLYER_4G, ignoreCase = true) || PhoneModel.equals(
            MAGIC_ALBUM,
            ignoreCase = true
        ) || PhoneModel.equals(IDENTITI_TAB, ignoreCase = true) || PhoneModel.equals(
            IDENTITI_CRON,
            ignoreCase = true
        ) || PhoneModel.equals(KPAD, ignoreCase = true) || PhoneModel.equals(
            ESP_ES101_109W,
            ignoreCase = true
        ) || PhoneModel.equals(SH_T805K, ignoreCase = true)
    }

    fun isGalexyTab7(): Boolean {
        val PhoneModel = Build.MODEL
        return PhoneModel.equals(GALEXY_TAB7_S, ignoreCase = true) || PhoneModel.equals(
            GALEXY_TAB7_K,
            ignoreCase = true
        ) || PhoneModel.equals(GALEXY_TAB7_L, ignoreCase = true)
    }
}
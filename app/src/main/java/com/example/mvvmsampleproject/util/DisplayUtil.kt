package com.example.mvvmsampleproject.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager

object DisplayUtil {

    private val displayMetrics = Resources.getSystem().displayMetrics
    private val density = displayMetrics.density

    fun px2dp(px: Int): Int {
        return (px / density).toInt()
    }

    fun dp2px(dp: Int): Int {
        return (dp * density).toInt()
    }

    fun px2dp(px: Float): Int {
        return (px / density).toInt()
    }

    fun dp2px(dp: Float): Int {
        return (dp * density).toInt()
    }

    fun getDensity(): Float {
        return density
    }

    fun getDPI(): Float {
        return displayMetrics.densityDpi.toFloat()
    }

    fun getScreenWidth(): Int {
        return displayMetrics.widthPixels
    }

    fun getScreenHeight(): Int {
        return displayMetrics.heightPixels
    }

    fun dipToPixel(a_nNum: Float): Int {
        return (a_nNum * density + 0.5f).toInt()
    }

    fun dpToPx(context: Context, dp: Int): Int {
        val displaymetrics = context.resources.displayMetrics
        //int px = Math.round(dp * (displaymetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), displaymetrics)
            .toInt()
    }

    fun pxToDp(context: Context, px: Int): Int {
        val displaymetrics = context.resources.displayMetrics
        return Math.round(px / (displaymetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    fun pxToDp(px: Int): Int {
        return (px / Resources.getSystem().displayMetrics.density).toInt()
    }

    /*
        dimens.xml 에 정의되어 있는 값(R.dimen.idname)
        tvTest.textSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.idname));
     */
    fun getPxFromDimension(context: Context, id: Int): Int {
        //context.getResources().getDimension(id);
        return context.resources.getDimensionPixelSize(id)
    }

    fun getScreenSize(context: Context): Point {
        var deviceWidth = 0
        var usableHeight = 0
        run {
            val metrics = DisplayMetrics()
            val window = context.applicationContext
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
            window.defaultDisplay.getMetrics(metrics)
            deviceWidth = metrics.widthPixels
            usableHeight = metrics.heightPixels
            window.defaultDisplay.getRealMetrics(metrics)
            val deviceHeight = metrics.heightPixels // 상태바 포함
        }
        return Point(deviceWidth, usableHeight)
    }

    // 상태바 높이
    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun getDensityDpi(context: Context): String {
        val metrics: DisplayMetrics
        metrics = DisplayMetrics()
        val window =
            context.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        window.defaultDisplay.getMetrics(metrics)
        var density = ""
        if (metrics != null) {
            density = if (metrics.densityDpi <= DisplayMetrics.DENSITY_LOW) {
                "DENSITY_LOW : " + metrics.densityDpi
            } else if (metrics.densityDpi <= DisplayMetrics.DENSITY_MEDIUM) {
                "DENSITY_MEDIUM : " + metrics.densityDpi
            } else if (metrics.densityDpi <= DisplayMetrics.DENSITY_HIGH) {
                "DENSITY_HIGH : " + metrics.densityDpi
            } else if (metrics.densityDpi <= DisplayMetrics.DENSITY_XHIGH) {
                "DENSITY_XHIGH : " + metrics.densityDpi
            } else if (metrics.densityDpi <= DisplayMetrics.DENSITY_XXHIGH) {
                "DENSITY_XXHIGH : " + metrics.densityDpi
            } else if (metrics.densityDpi <= DisplayMetrics.DENSITY_XXXHIGH) {
                "DENSITY_XXXHIGH : " + metrics.densityDpi
            } else {
                "DENSITY : " + metrics.densityDpi
            }
        }
        return density
    }

    fun getSmallScreenWidthDp(context: Context): Int {
        val configuration = context.resources.configuration
        val screenWidthDp = configuration.screenWidthDp
        return configuration.smallestScreenWidthDp
    }


    fun getScreenHeightDp(context: Context): Int {
        val configuration = context.resources.configuration
        return configuration.screenHeightDp
    }

}
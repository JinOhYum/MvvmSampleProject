package com.example.mvvmsampleproject.dialog.custom

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.example.mvvmsampleproject.R
import com.example.mvvmsampleproject.util.DataUtils
import com.example.mvvmsampleproject.util.Define
import com.example.mvvmsampleproject.util.LogUtil


class CustomTextView : AppCompatTextView {
    private val fontMap: MutableMap<String, Typeface> = HashMap()

    constructor(context: Context?) : super(context!!)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        applyTypeface(context, this, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        applyTypeface(context, this, attrs)
    }

    fun applyTypeface(context: Context, tv: TextView, attrs: AttributeSet?) {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView)
        var typefaceName = arr.getString(R.styleable.CustomTextView_typeface)
        if (DataUtils.isNull(typefaceName) || typefaceName!!.length == 0) {
            typefaceName = Define.TextFont.REGULAR
        }
        try {
            when (typefaceName) {
                Define.TextFont.ROBOTO_BOLD -> tv.setTypeface(
                    ResourcesCompat.getFont(
                        context,
                        R.font.roboto_bold
                    )
                )

                Define.TextFont.ROBOTO_MEDIUM -> tv.setTypeface(
                    ResourcesCompat.getFont(
                        context,
                        R.font.roboto_medium
                    )
                )

                Define.TextFont.ROBOTO_REGULAR -> tv.setTypeface(
                    ResourcesCompat.getFont(
                        context,
                        R.font.roboto_regular
                    )
                )

                Define.TextFont.BOLD -> tv.setTypeface(
                    ResourcesCompat.getFont(
                        context,
                        R.font.noto_sans_bold
                    )
                )

                Define.TextFont.MEDIUM -> tv.setTypeface(
                    ResourcesCompat.getFont(
                        context,
                        R.font.noto_sans_medium
                    )
                )

                Define.TextFont.REGULAR -> tv.setTypeface(
                    ResourcesCompat.getFont(
                        context,
                        R.font.noto_sans_regular
                    )
                )

                else -> tv.setTypeface(ResourcesCompat.getFont(context, R.font.noto_sans_regular))
            }
            arr.recycle()
        } catch (e: Exception) {
            e.localizedMessage?.let { LogUtil.e(it) }
        }
    }

    fun getFont(context: Context, fontName: String): Typeface? {
        return if (fontMap.containsKey(fontName)) {
            fontMap[fontName]
        } else {
            val tf = Typeface.createFromAsset(context.assets, fontName)
            fontMap[fontName] = tf
            tf
        }
    }
}

package com.example.mvvmsampleproject.data.model

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes


data class MainBottomData(
    val position: Int,
    val imageView: ImageView,
    val textView: TextView,
    @RawRes val gifNameRight: Int,
    @RawRes val gifNameDark: Int,
    @DrawableRes val baseImageName: Int
)

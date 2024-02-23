package com.example.mvvmsampleproject

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KtApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}
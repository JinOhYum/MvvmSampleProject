package com.example.mvvmsampleproject.api

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils

class PreferenceUtil {

    private var pref: SharedPreferences

    private val mInatance: PreferenceUtil? = null


    constructor(pref :SharedPreferences){
        this.pref = pref
    }


}
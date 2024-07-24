package com.example.mvvmsampleproject.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {

    private val TAG = "PreferenceUtil"

    private var pref: SharedPreferences? = null

    private var prefName = ""

    private val PREF_NAME = "USER_PREFERENCE"

    private val PREFIX = "CSV6_"
    private val PREFIX_WEB = PREFIX + "_WEB_"

    val KEY_TEST = PREFIX + "KEY_TEST"

    companion object{

        private var mInatance: PreferenceUtil? = null

        fun create(context: Context): PreferenceUtil {
            synchronized(PreferenceUtil::class.java) {
                if (mInatance == null) {
                    mInatance = PreferenceUtil(context)
                }
            }
            return mInatance!!
        }
    }
    init {
        prefName = PREF_NAME
        pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    fun getPreference(key: String?, nDefault: Int): Int {
        return pref!!.getInt(key, nDefault)
    }

    fun setPreference(key: String?, nValue: Int): Boolean {
        val edit = pref!!.edit()
        edit.putInt(key, nValue)
        return edit.commit()
    }

}
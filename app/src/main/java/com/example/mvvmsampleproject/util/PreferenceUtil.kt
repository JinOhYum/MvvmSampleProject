package com.example.mvvmsampleproject.util

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.ktshow.cs.ndkaes.NdkAes
import javax.inject.Singleton

class PreferenceUtil(context: Context) {

    private val TAG = "PreferenceUtil"

    private var pref: SharedPreferences? = null

    private var prefName = ""

    private val PREF_NAME = "USER_PREFERENCE"

    private val PREFIX = "CSV6_"
    private val PREFIX_WEB = PREFIX + "_WEB_"

    val KEY_TEST = PREFIX + "KEY_TEST"

    private val KEY_COVER_SCREEN_MODELS: String = PREFIX + "KEY_COVER_SCREEN_MODELS"

    // [DR-2019-52394] Android Q OS 대응
    // 서버 통신에 사용할 단말 UUID
    private val KEY_ALTER_UUID: String = PREFIX + "KEY_ALTER_UUID"

    companion object{

        private var mInatance: PreferenceUtil? = null

        fun getInstance(context: Context): PreferenceUtil {
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

    fun getPreference(key: String?, defValue: String?): String? {
        return pref!!.getString(key, defValue)
    }

    fun setPreference(key: String?, strValue: String?): Boolean {
        val edit = pref!!.edit()
        edit.putString(key, strValue)
        return edit.commit()
    }

    fun getPreference(key: String?, bDefault: Boolean): Boolean {
        return pref!!.getBoolean(key, bDefault)
    }

    fun setPreference(key: String?, bValue: Boolean): Boolean {
        val edit = pref!!.edit()
        edit.putBoolean(key, bValue)
        return edit.commit()
    }



    fun setCoverScreenModels(models: String?) {
        setPreference(KEY_COVER_SCREEN_MODELS, models)
    }

    fun getCoverScreenModels(): String {
        return getPreference(KEY_COVER_SCREEN_MODELS, "").toString()
    }

    // [DR-2019-52394] Android Q OS 대응
    // 서버 통신에 사용할 단말 UUID
    fun setAlterUuid(uuid: String?): Boolean {
        var uuid = uuid
        uuid = if (TextUtils.isEmpty(uuid)) "" else uuid
        return setPreference(
            KEY_ALTER_UUID,
            NdkAes.encrypt(uuid, true)
        )
    }

    fun getAlterUuid(): String {
        val uuid = getPreference(KEY_ALTER_UUID, "")
        var plain = ""
        if (!TextUtils.isEmpty(uuid)) {
            plain = NdkAes.decrypt(uuid, true)
        }
        return plain
    }

}
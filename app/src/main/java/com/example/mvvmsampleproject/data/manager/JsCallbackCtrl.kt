package com.example.mvvmsampleproject.data.manager

import android.text.TextUtils
import com.example.mvvmsampleproject.util.LogUtil
import com.google.gson.GsonBuilder

/**
 * javascript bridge 로 전달되는 json 파라미터를 생성, 파싱한다.
 */
object JsCallbackCtrl {
    const val ERROR_SECCESS = "0000"
    const val ERROR_FAIL = "0001"

    // dto -> json string
    fun makeJsonString(src: Any?): String? {
        if (src == null) return null
        var jsonString = ""
        try {
            val builder = GsonBuilder()
            builder.excludeFieldsWithoutExposeAnnotation() // Expose 어노테이션만 시리얼라이즈
            //builder.serializeNulls();   // NULL 포함
            val gson = builder.create()
            jsonString = gson.toJson(src)
        } catch (e: Exception) {
            e.localizedMessage?.let { LogUtil.e(it.toString()) }
        }
        return jsonString
    }

    // json -> dto
    fun <T> parseJson(json: String?, dtoType: Class<T>?): T? {
        var dto: T? = null
        if (TextUtils.isEmpty(json)) {
            return null
        }
        try {
            val builder = GsonBuilder()
            builder.excludeFieldsWithoutExposeAnnotation() // Expose 어노테이션만 시리얼라이즈
            builder.serializeNulls() // NULL 포함
            val gson = builder.create()
            dto = gson.fromJson(json, dtoType)
        } catch (e: Exception) {
            LogUtil.i("callJavaScript_Exception : $e")
        }
        return dto
    }
}

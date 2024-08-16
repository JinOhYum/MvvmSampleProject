package com.example.mvvmsampleproject.util

import android.content.Context
import android.os.Build
import android.text.TextUtils
import com.ktshow.cs.ndkaes.NdkAes
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DataUtils {

    /**
     * isNull : 주어진 스트링이 null 이거나 nullstring(사이즈=0) 인지 체크한다.
     */
    @JvmStatic
    fun isNull(str: Any?): Boolean {
        return isNull(str, false)
    }

    @JvmStatic
    fun isNotNull(str: Any?): Boolean {
        return !isNull(str, false)
    }

    /**
     * isNull : 주어진 스트링이 null 이거나 nullstring(사이즈=0) 인지 체크한다.
     * 좌우여백 제거하고 체크할지 여부
     */
    private fun isNull(str: Any?, checkTrim: Boolean): Boolean {
        if (str != null) {
            val check: Serializable = if (checkTrim) str.toString()
                .trim { it <= ' ' }.length else str.toString().length == 0
        }
        return str == null || (if (checkTrim) str.toString()
            .trim { it <= ' ' }.length else str.toString().length) == 0
    }

    fun checkNull(str: String?): String {
        return checkNull(str, Define.EMPTY)
    }

    /**
     * checkNull : 주어진 스트링이 null 이면 defaultStr 를 반환하고, 다른 경우엔 그냥 str 그대로 반환
     */
    fun checkNull(str: String?, defaultStr: String): String {
        return str ?: defaultStr
    }

    /**
     * checkNullStr : 주어진 스트링이 null 또는 "null" str 이면 defaultStr 를 반환하고, 다른 경우엔 그냥 str 그대로 반환
     */
    fun checkNullStr(str: String?, defaultStr: String): String {
        return if (str == null) defaultStr else if ("null".equals(
                str,
                ignoreCase = true
            )
        ) defaultStr else str
    }

    /**
     * isNullStr : 주어진 스트링이 "null" 과 같은지 체크한다.
     */
    fun isNullStr(str: Any): Boolean {
        return "null" == str
    }

    fun isStrNull(str: String?): Boolean {
        return str == null || "" == str.trim { it <= ' ' }
    }

    fun emptyString(str: String?): String? {
        return if (isStrNull(str)) Define.EMPTY else str
    }

    fun trim(body: String?): String? {
        return body?.trim { it <= ' ' }
    }

    /**
     * encode : 문자열 인코딩
     */
    fun encode(value: String?): String? {
        var value = value ?: return null
        value = value.replace("'", "&#x27;")
        value = value.replace("&", "&amp;")
        value = value.replace("\"", "&quot;")
        value = value.replace("<", "&lt;")
        value = value.replace(">", "&gt;")
        value = value.replace("/", "&#x2F;")
        return value
    }

    /**
     * decode : 문자열 디코딩
     */
    fun decode(value: String?): String? {
        var value = value ?: return null
        value = value.replace("&#x27;", "'")
        value = value.replace("&amp;", "&")
        value = value.replace("&quot;", "\"")
        value = value.replace("&lt;", "<")
        value = value.replace("&gt;", ">")
        value = value.replace("&#x2F;", "/")
        return value
    }

    /**
     * getStringArray : Array 문자열 리소스를 얻는다.
     */
    fun getStringArray(resId: Array<String>): ArrayList<String> {
        val str = ArrayList<String>()
        for (s in resId) {
            str.add(s)
        }
        return str
    }

    /**
     * showLog : Dto 로그 확인
     *
     */
    fun showLog(json: String?) {
        if (isNull(json)) return
        try {
            LogUtil.i("############################################################################################")
//            ParseUtils.parseJSONObject(JSONObject(json))
            LogUtil.i("############################################################################################")
        } catch (e: JSONException) {
            LogUtil.e(e.localizedMessage)
        }
    }

    @Throws(ParseException::class)
    fun getWeekCheckPopup(saveDate: String?): String {
        val getToday = Calendar.getInstance()
        getToday.time = Date() //금일 날짜

//        String s_date = "2020-03-01";
        val date = SimpleDateFormat("yyyy-MM-dd").parse(saveDate)
        val cmpDate = Calendar.getInstance()
        cmpDate.time = date //특정 일자
        val diffSec = (getToday.timeInMillis - cmpDate.timeInMillis) / 1000
        val diffDays = (diffSec / (24 * 60 * 60)).toInt() //일자수 차이
        return diffDays.toString()
    }

    fun getTOday(): String {
        val currentTime = Calendar.getInstance().time
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentTime)
    }

    fun onChekCredentialId(appCid: String, cId: String?, loginId: String): Boolean {
        return if (!TextUtils.isEmpty(cId) && !TextUtils.isEmpty(appCid)) {
            val credentialId = NdkAes.decrypt(cId, true)
            val c_id = credentialId.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            if (c_id.size > 1) {
                loginId == c_id[1] && appCid == c_id[0]
            } else {
                false
            }
        } else {
            false
        }
    }

    fun isNumber(strValue: String?): Boolean {
        return strValue != null && strValue.matches("[-+]?\\d*\\.?\\d+".toRegex())
    }

    fun isCoverScreen(context: Context?): Boolean {
//        SM-F731N|SM-F721N
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val model: String = PreferenceUtil.getInstance(context!!).getCoverScreenModels()
            if ("" != model && isNotNull(model)) {
                val severModel = model.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                val phoneModel = Build.MODEL
                for (m in severModel) {
                    if (phoneModel.contains(m)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    fun isMaskingString(id: String): String {
        var maskingResult = ""
        maskingResult = if (id.length <= 1) {
            ""
        } else if (id.length <= 3) {
            id.replace(".{2}$".toRegex(), "**")
        } else {
            id.replace(".{3}$".toRegex(), "***")
        }
        return maskingResult
    }
}
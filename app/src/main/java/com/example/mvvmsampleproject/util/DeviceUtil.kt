package com.example.mvvmsampleproject.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.PowerManager
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.text.TextUtils
import androidx.core.app.ActivityCompat
import androidx.core.net.ConnectivityManagerCompat
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.net.NetworkInterface
import java.net.SocketException
import java.nio.charset.StandardCharsets
import java.util.Arrays
import java.util.UUID

object DeviceUtil {

    // KT 고객센터 단말기 타입
    fun getOSType(): String {
        var osType = "OS003" // 안드로이드 폰
        if (DifferentResolutionChecker.isGalexyTab()) {
            osType = "OS004" // 안드로이드 패드
        }
        return osType
    }

    // OS version
    fun getOSVersion(): String {
        return Build.VERSION.RELEASE.replace(" ".toRegex(), "")
    }

    // 단말기 제조사
    fun getDeviceBrand(): String {
        return Build.BRAND.replace(" ".toRegex(), "")
    }

    // 통신사
    fun getVendor(context: Context?): String {
        if (context == null) {
            return ""
        }
        var vendor = ""
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        try {
            vendor = telephonyManager.networkOperatorName
        } // [DR-2020-미등록] 마이케이티앱 sparrow 소스진단 취약점 조치
        catch (e: Exception) {
            LogUtil.e(e.localizedMessage)
        }
        return vendor
    }

    fun getAlterDeviceId(context: Context): String {
        if (ObjectUtil.isEmpty(context)) {
            return ""
        }
        var guid = ""
        synchronized(DeviceUtil::class.java) {
            val telManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

            // targetSdkVersion 30 대응 : hangoo
            // - Manifest.permission.READ_PHONE_STATE를 PermissionUtil.getReadPhoneSatePermission() 으로 변경
            if (telManager != null && PermissionUtil.isGrantedReadPhoneSatePermission(context)) {
                try {
                    val deviceId = ""
                    if (TextUtils.isEmpty(deviceId)) {
                        @SuppressLint("HardwareIds") val androidId =
                            Settings.Secure.getString(
                                context.contentResolver,
                                Settings.Secure.ANDROID_ID
                            )
                        if (!TextUtils.isEmpty(androidId) && "9774d56d682e549c" != androidId) {
                            guid =
                                UUID.nameUUIDFromBytes(androidId.toByteArray(StandardCharsets.UTF_8))
                                    .toString()
                        }
                    } else {
                        guid =
                            UUID.nameUUIDFromBytes(deviceId.toByteArray(StandardCharsets.UTF_8))
                                .toString()
                    }
                } catch (e: Exception) {
                    //LogUtil.e( e.getLocalizedMessage());
                    LogUtil.e("Exception : $e")
                }
            }

            // getDeviceId()값이 없고 ANDROID_ID값도 없을 경우 random uuid를 생성한 후 단말에 암호화하여 사용한다
            if (TextUtils.isEmpty(guid)) {
                var uuid: String = PreferenceUtil.getInstance(context).getAlterUuid()
                if (TextUtils.isEmpty(uuid)) {
                    uuid = UUID.randomUUID().toString()
                    PreferenceUtil.getInstance(context).setAlterUuid(uuid)
                }
                guid = uuid
            }
        }
        return guid
    }

    // [DR-2019-52394] Android Q OS 대응
    // 파라미터 , user-agent에 ANDROID_ID 를 추가하여 보내준다 서버에서는 ANDROID_ID를 이용하여 APP_TOKEN을 생성한다
    fun getAlterAndroidId(context: Context): String {
        if (ObjectUtil.isEmpty(context)) return ""
        var guid = ""
        synchronized(DeviceUtil::class.java) {
            val telManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

            // targetSdkVersion 30 대응 : hangoo
            // - Manifest.permission.READ_PHONE_STATE를 PermissionUtil.getReadPhoneSatePermission() 으로 변경
            if (telManager != null && PermissionUtil.isGrantedReadPhoneSatePermission(context)) {
                try {
                    @SuppressLint("HardwareIds") val androidId =
                        Settings.Secure.getString(
                            context.contentResolver,
                            Settings.Secure.ANDROID_ID
                        )
                    if (!TextUtils.isEmpty(androidId) && "9774d56d682e549c" != androidId) {
                        guid =
                            UUID.nameUUIDFromBytes(androidId.toByteArray(StandardCharsets.UTF_8))
                                .toString()
                    }
                } catch (e: Exception) {
                    LogUtil.e(e.localizedMessage)
                }
            }

            // ANDROID_ID값이 없을 경우 random uuid를 생성한 후 단말에 암호화하여 사용한다
            if (TextUtils.isEmpty(guid)) {
                var uuid: String = PreferenceUtil.getInstance(context).getAlterUuid()
                if (TextUtils.isEmpty(uuid)) {
                    uuid = UUID.randomUUID().toString()
                    PreferenceUtil.getInstance(context).setAlterUuid(uuid)
                }
                guid = uuid
            }
        }
        return guid
    }

    // 폰 번호 조회
    //<uses-permission android:name="android.permis₩sion.READ_PHONE_STATE" />
    fun getPhoneNumber(context: Context?): String {
        if (context == null) return ""
        var phoneNumber = ""
        // targetSdkVersion 30 대응 : hangoo
        // - Manifest.permission.READ_PHONE_STATE를 PermissionUtil.getReadPhoneSatePermission() 으로 변경
        val telManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (telManager != null && PermissionUtil.isGrantedReadPhoneSatePermission(context)) {
            phoneNumber = telManager.line1Number
            if (!TextUtils.isEmpty(phoneNumber)) {
                phoneNumber = phoneNumber.replace("+82", "0")
            }
        }
        return phoneNumber
    }

    // 유심 준비 여부
    fun isUsimReady(context: Context?): Boolean {
        if (context == null) return false
        var isReady = false

        // targetSdkVersion 30 대응 : hangoo
        // - Manifest.permission.READ_PHONE_STATE를 PermissionUtil.getReadPhoneSatePermission() 으로 변경
        val telManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (PermissionUtil.isGrantedReadPhoneSatePermission(context)) {
            if (telManager.simState == TelephonyManager.SIM_STATE_READY) {
                isReady = true
            }
        }
        return isReady
    }

    // 유심 해제 여부
    fun isUsimAbsent(context: Context?): Boolean {
        if (context == null) return false
        var isReady = false
        // targetSdkVersion 30 대응 : hangoo
        // - Manifest.permission.READ_PHONE_STATE를 PermissionUtil.getReadPhoneSatePermission() 으로 변경
        if (PermissionUtil.isGrantedReadPhoneSatePermission(context)) {
            val telManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (telManager != null && telManager.simState == TelephonyManager.SIM_STATE_ABSENT) {
                isReady = true
            }
        }
        return isReady
    }

    // SIM 시리얼 번호
    // <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    fun getSimSerialNumber(context: Context?): String {
        if (context == null) return ""
        val serial = ""
        // targetSdkVersion 30 대응 : hangoo
        // - Manifest.permission.READ_PHONE_STATE를 PermissionUtil.getReadPhoneSatePermission() 으로 변경
        if (PermissionUtil.isGrantedReadPhoneSatePermission(context)) {
            val telManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (telManager != null) {
                // [DR-2019-52394] Android Q OS 대응
                try {
                    // Q OS 미만에서만 SimSerialNumber 사용
                } catch (e: Exception) {
                    LogUtil.e(e.localizedMessage)
                }
            }
        }
        return if (TextUtils.isEmpty(serial)) "" else serial
    }

    fun getModelName(): String {
        return Build.MODEL.replace(" ", "")
    }

    @JvmStatic
    // 202304-네트워크상태체크 API교체
    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(
            ConnectivityManager::class.java
        )
        val currentNetwork = connectivityManager.activeNetwork
        LogUtil.d("currentNetwork =$currentNetwork")
        return if (currentNetwork != null) {
            val caps = connectivityManager.getNetworkCapabilities(currentNetwork)
            if (caps != null) { //202306-java.lang.NullPointerException
                val cellConnect = caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                val wifiConnect = caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                val bleConnect = caps.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)
                cellConnect || wifiConnect || bleConnect
            } else {
                false
            }
        } else {
            false
        }
    }


    // Mobile Country Code ( 3자리 숫자 )
    fun getNetworkMcc(context: Context?): String {
        if (context == null) return ""
        var mcc = ""
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                ?: return mcc
        val operator = telephonyManager.networkOperator
        if (!TextUtils.isEmpty(operator)) {
            if (operator.length > 3) {
                mcc = operator.substring(0, 3)
            }
        }
        //        한국 450
//        mcc = "289";
        return mcc
    }

    // 모바일 네트워크 사업자(2 ~ 3자리)
    fun getNetworkMnc(context: Context?): String {
        if (context == null) return ""
        var mnc = ""
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                ?: return ""
        val operator = telephonyManager.networkOperator
        if (!TextUtils.isEmpty(operator)) {
            if (operator.length >= 5) {
                mnc = operator.substring(3)
            }
        }
        return mnc
    }

    // Mobile Country Code 에 해당 하는 ISO (CDMA 네트워크 에서는 신뢰할 수 없음)
    fun getNetworkCountryISO(context: Context): String {
        var iso = ""
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                ?: return ""
        try {
            iso = telephonyManager.networkCountryIso
        } catch (e: Exception) {
            LogUtil.e(e.localizedMessage)
        }
        return iso
    }


    fun isWiFiNetwork(context: Context?): Boolean {
        if (context == null) return false
        var isResult = false
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_NETWORK_STATE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (cm != null) {
                // [DR-2019-06243] 마이 케이티 Target SDK 버전 28로 변경
                val network = cm.activeNetwork
                val capabilities = cm.getNetworkCapabilities(network)
                if (capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    isResult = true
                }
            }
        }
        return isResult
    }

    // 데이터 로밍 사용 여부
    fun isEnabledDataRoaming(context: Context?): Boolean {
        if (context == null) return false
        var nEnabled = 0
        val manager =
            context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (manager != null) {
            try {
//                nEnabled = Settings.Secure.getInt(context.getContentResolver(), Settings.Global.DATA_ROAMING);
                nEnabled =
                    Settings.Global.getInt(context.contentResolver, Settings.Global.DATA_ROAMING)
            } catch (e: SettingNotFoundException) {
                LogUtil.e("Exception : " + e.localizedMessage)
            }
        }
        return nEnabled == 1
    }

    //202304-데이터로밍체크
    fun isDataRoaming(context: Context): Boolean {
        val mTelephony = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mTelephony.isDataRoamingEnabled
        } else true
    }

    // 위치정보 서비스 On/Off 여부
    fun isLocationMode(context: Context?): Boolean {
        if (context == null) return false
        var locationMode = 0
        var locationProviders: String
        try {
            locationMode =
                Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE)
        } catch (e: SettingNotFoundException) {
            LogUtil.e(e.localizedMessage)
        }
        return locationMode > 0
    }

    /**
     * isPowerSaveMode : 절전모드 체크(충전중일 경우 false)
     */
    fun isPowerSaveMode(context: Context): Boolean {
        //절전모드 체크
        if (DataUtils.isNull(context)) return false
        val powerManager =
            context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return DataUtils.isNotNull(powerManager) && powerManager.isPowerSaveMode
    }

    /**
     * isDozeMode : 도즈모드 체크
     */
    fun isDozeMode(context: Context): Boolean {
        if (DataUtils.isNull(context)) return false
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return DataUtils.isNotNull(powerManager) && powerManager.isDeviceIdleMode
    }

    // [DR-2019-28608] [VOC개선] 알림바/위젯 데이터 절약모드 해제버튼 개선
    // 절전모드 셋팅으로 보내기
    fun displayBatterySaverSetting(context: Context) {
        try {
            val intent = Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
            LogUtil.e(e.localizedMessage)
        }
    }

    // 데이터 세이버 체크
    fun isDataSaverMode(context: Context?): Boolean {
        if (context == null) return false
        var result = false
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connMgr != null && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_NETWORK_STATE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (connMgr.isActiveNetworkMetered) {
                when (connMgr.restrictBackgroundStatus) {
                    ConnectivityManager.RESTRICT_BACKGROUND_STATUS_ENABLED -> result = true
                    ConnectivityManager.RESTRICT_BACKGROUND_STATUS_WHITELISTED -> {}
                    ConnectivityManager.RESTRICT_BACKGROUND_STATUS_DISABLED -> {}
                }
            }
        }
        return result
    }

    // [DR-2019-28608] [VOC개선] 알림바/위젯 데이터 절약모드 해제버튼 개선
    // 데이터 세이버 설정화면 이동
    fun displayDataSaverSetting(context: Context?) {
        if (context == null) return
        try {
            val intent = Intent(
                Settings.ACTION_IGNORE_BACKGROUND_DATA_RESTRICTIONS_SETTINGS,
                Uri.parse("package:" + context.packageName)
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
            LogUtil.e(e.localizedMessage)
        }
    }

    // 알림 설정이 IMPORTANCE_MIN 보다 높은지 체크
    fun checkOverNotificationMin(context: Context, channelId: String?): Boolean {
        if (TextUtils.isEmpty(channelId)) {
            return false
        }
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (manager != null) {
            val channel = manager.getNotificationChannel(channelId)
            if (channel != null) {
                return channel.importance > NotificationManager.IMPORTANCE_MIN
            }
        }
        return false
    }

    // 루팅 여부
    fun isRootedDevice(): Boolean {
        var isRooted = false
        try {
            for (pathDir in System.getenv("PATH").split(":".toRegex())
                .dropLastWhile { it.isEmpty() }
                .toTypedArray()) {
                if (File(pathDir, "su").exists()) {
                    return true
                }
            }
        } catch (e: Exception) {
            LogUtil.e(e.localizedMessage)
        }
        val rootPath = Environment.getExternalStorageDirectory().toString()
        val rootFileArray = ArrayList<String>()
        rootFileArray.add("$rootPath/system/bin/su")
        rootFileArray.add("$rootPath/system/xbin/su")
        rootFileArray.add("$rootPath/system/app/SuperUser.apk")
        rootFileArray.add("$rootPath/data/data/com.noshufou.android.su")
        isRooted = try {
            Runtime.getRuntime().exec("su")
            true
        } catch (ignored: Exception) {
            false
        }
        if (!isRooted) {
            try {
                for (path in rootFileArray) {
                    val file = File(path)
                    if (file.exists()) {
                        isRooted = true
                        break
                    }
                }
            } catch (e: Exception) {
                isRooted = false
            }
        }
        return isRooted
    }

    fun checkRootPackages(context: Context): Boolean {
        val rootPackages = arrayOf(
            "com.devadvance.rootcloak",
            "com.devadvance.rootcloakplus",
            "com.koushikdutta.superuser",
            "com.thirdparty.superuser",
            "eu.chainfire.supersu",
            "de.robv.android.xposed.installer",
            "com.saurik.substrate",
            "com.zachspong.temprootremovejb",
            "com.amphoras.hidemyroot",
            "com.amphoras.hidemyrootadfree",
            "com.formyhm.hiderootPremium",
            "com.formyhm.hideroot",
            "com.noshufou.android.su",
            "com.noshufou.android.su.elite",
            "com.yellowes.su",
            "com.topjohnwu.magisk",
            "com.kingroot.kinguser",
            "com.kingo.root",
            "com.smedialink.oneclickroot",
            "com.zhiqupk.root.global",
            "com.alephzain.framaroot"
        )
        val pm = context.packageManager
        if (pm != null) {
            for (pkg in rootPackages) {
                try {
                    pm.getPackageInfo(pkg!!, 0)
                    return true
                } catch (e: PackageManager.NameNotFoundException) {
                    LogUtil.e("NameNotFoundException = $e")
                    e.printStackTrace()
                }
            }
        }
        return false
    }

    fun checkRootPackages_B(): Boolean {
        val strArr = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su",
            "/SYSTEM/APP/SUPERUSER.APK",
            "/SBIN/SU",
            "/SYSTEM/BIN/SU",
            "/SYSTEM/XBIN/SU",
            "/DATA/LOCAL/XBIN/SU",
            "/DATA/LOCAL/BIN/SU",
            "/SYSTEM/SD/XBIN/SU",
            "/SYSTEM/BIN/FAILSAFE/SU",
            "/DATA/LOCAL/SU"
        )
        var i2 = 0
        while (i2 < 18) {
            val str = strArr[i2]
            i2++
            if (File(str).exists()) {
                return true
            }
        }
        return false
    }

    fun hasSupportedAbi(abi: String?): Boolean {
        if (TextUtils.isEmpty(abi)) return false
        var abiList: List<String?> = ArrayList()
        abiList = Arrays.asList(*Build.SUPPORTED_ABIS)
        return abiList.contains(abi)
    }

    fun appRestart(context: Context?) {
        if (context == null) return
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        val mainIntent = Intent.makeRestartActivityTask(intent!!.component)
        context.startActivity(mainIntent)
        System.exit(0)
    }

    /**
     * 백그라운드 데이터 허용 여부 체크
     * 2021-12-13 : Hangoo
     */
    fun isBackgroundDataEnable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val bgData = ConnectivityManagerCompat.getRestrictBackgroundStatus(connectivityManager)
        return bgData == 1 // enabled
        // disabled
    }

    //kcy1000- 듀얼심 체크
    fun isDualSim(pContext: Context): Boolean {
        val telephony = pContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return if (telephony != null) {
            telephony.activeModemCount == 2 //Dual SIM functionality
        } else false
    }


    fun isDualSimInfoCount(context: Context?): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            SubscriptionManager.from(context).activeSubscriptionInfoCount >= 2
        } else false
    }

    // 듀얼심 상태값별로 번호 가져오기
    fun getCarrierInfo(context: Context): List<String> {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val phoneNum: MutableList<String> = ArrayList()
        try {
//                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
//                        && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_NUMBERS) == PackageManager.PERMISSION_GRANTED
//                        && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            // kcy1000-202210 - firebase exception 분석 수정
            val voiceNum = SubscriptionManager.getDefaultVoiceSubscriptionId()
            if (voiceNum < 3) {
                val voicePNum = tm.createForSubscriptionId(voiceNum).line1Number
                if (!TextUtils.isEmpty(voicePNum)) {
                    phoneNum.add(voicePNum.replace("+82", "0"))
                }
            }
            val dataNum = SubscriptionManager.getDefaultDataSubscriptionId()
            if (dataNum < 3) {
                val dataPNum = tm.createForSubscriptionId(dataNum).line1Number
                if (!TextUtils.isEmpty(dataPNum)) {
                    phoneNum.add(dataPNum.replace("+82", "0"))
                }
            }
            val smsNum = SubscriptionManager.getDefaultSmsSubscriptionId()
            if (smsNum < 3) {
                val smsPNum = tm.createForSubscriptionId(smsNum).line1Number
                if (!TextUtils.isEmpty(smsPNum)) {
                    phoneNum.add(smsPNum.replace("+82", "0"))
                }
            }
            //                }
        } catch (e: Exception) {
            // 유심이 없을경우 Exception 발생
            LogUtil.e("Exception = $e")
            phoneNum.add("")
        }
        return phoneNum
    }


    fun getDeviceIP(): JSONArray {
        val jsonArrIP = JSONArray()
        try {
            val en = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf = en.nextElement()
                val jsonObjIP = JSONObject()
                val enumIpAddr = intf.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress = enumIpAddr.nextElement()
                    try {
                        val ipType = inetAddress.isLoopbackAddress
                        if (ipType) {
                            jsonObjIP.put("ip_type", "4")
                        } else {
                            jsonObjIP.put("ip_type", "6")
                        }
                        jsonObjIP.put("ipaddr", inetAddress.hostAddress)
                    } catch (e: JSONException) {
                        throw RuntimeException(e)
                    }
                    jsonArrIP.put(jsonObjIP)
                }
            }
        } catch (e: SocketException) {
            throw RuntimeException(e)
        }
        return jsonArrIP
    }
}
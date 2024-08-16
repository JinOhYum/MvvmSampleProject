package com.example.mvvmsampleproject.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

object PermissionUtil {
    const val PERMISSION_DENIED = -1
    const val PERMISSION_GRANTED = 0
    const val PERMISSION_RATIONALE = 1
    const val PERMISSION_REQUESTED = 2

    // targetSdkVersion 30 대응 : hangoo
    // - targetSdkVersion 30이면 Manifest.permission.READ_PHONE_STATE를
    // - Manifest.permission.READ_PHONE_NUMBERS로 변경해야 하기 때문에 final 키워드 제거

    // targetSdkVersion 30 대응 : hangoo
    // - targetSdkVersion 30이면 Manifest.permission.READ_PHONE_STATE를
    // - Manifest.permission.READ_PHONE_NUMBERS로 변경해야 하기 때문에 final 키워드 제거
    var mReqPermissions = ArrayList<String>()
    var mNeedPermissions = ArrayList<String>()

    var mPermissionLastStatus = PERMISSION_GRANTED
    var mRequestCode = DefineCommon.REQ_PERMISSION_APP

    var mRationaleTitle = 0
    private lateinit var mRationaleDesc: IntArray
    private lateinit var mRationaleButton: IntArray

    var mDenyTitle = 0
    private lateinit var mDenyDesc: IntArray
    private lateinit var mDenyButton: IntArray

    // 거부 항목 존재 시 사용자에게 표시할 메시지
    fun setRationaleMessage(title: Int, desc: IntArray, buttonText: IntArray?) {
        mRationaleTitle = title
        mRationaleDesc = desc
        mRationaleButton = mRationaleButton
    }

    // 최총 거부 시 사용자에게 표시할 메시지
    fun setDenyMessage(title: Int, desc: IntArray, buttonText: IntArray?) {
        mDenyTitle = title
        mDenyDesc = desc
        mDenyButton = mRationaleButton
    }

    /**
     * 앱 필수 권한 목록
     */
    fun getAppPermissionLists(): Array<Array<String>> {
        return arrayOf(
            getReadPhoneSatePermission(),  // 전화
            getReadStoragePermission() // 저장공간
        )
    }

    /**
     * 전화 권한
     */
    fun getReadPhoneSatePermission(): Array<String> {
        return arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_PHONE_NUMBERS
        )
    }

    /**
     * 저장공간 권한
     */
    fun getReadStoragePermission(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES
            )
        } else {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }

    /**
     * 알림 권한(Android 13 이상)
     */
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    fun getNotificationPermission(): Array<String> {
        return arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    }

    /**
     * 블루투스 권한(Android 12 이상)
     */
    @RequiresApi(api = Build.VERSION_CODES.S)
    fun getBluetoothPermission(): Array<String> {
        return arrayOf(Manifest.permission.BLUETOOTH_ADVERTISE)
    }

//    public static String[] getCallLogPermission() {
//        return new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW};
//    }

    //    public static String[] getCallLogPermission() {
    //        return new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW};
    //    }
    /**
     * 퍼미션 상태 체크
     */
    fun getPermissionStatus(activity: Activity?, vararg permissions: String): Int {
        if (permissions == null || permissions.size < 1) {
            return PERMISSION_GRANTED
        }
        if (activity == null || activity.isFinishing || activity.isDestroyed) {
            return PERMISSION_GRANTED
        }
        mNeedPermissions.clear()
        mReqPermissions.clear()

        // 허용되지 않은 권한 목록을 구성
        for (permission in permissions) {
            mReqPermissions.add(permission)
            if (ContextCompat.checkSelfPermission(
                    activity,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                mNeedPermissions.add(permission)
                LogUtil.i("permission 승인안됨")
            } else {
                LogUtil.i("permission 승인됨")
            }
        }
        var showRationale = false
        for (permission in mNeedPermissions) {
            // 이전에 사용자가 권한 승인을 거부했는지 체크한다.
            // 다시 안보기를 클릭했다면 false를 리턴한다.
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showRationale = true
            } else {
                LogUtil.i("requested permission : ")
            }
        }
        if (mNeedPermissions.isEmpty()) {
            LogUtil.i("return : PERMISSION_GRANTED")
            return PERMISSION_GRANTED
        }
        if (showRationale) {
            // 사용자가 거부한 권한이 있는 경우
            // 권한이 필요한 사유를 표시해야 한다.
            mPermissionLastStatus = PERMISSION_RATIONALE
            LogUtil.i("return : PERMISSION_RATIONALE")
            return PERMISSION_RATIONALE
        }
        mPermissionLastStatus = PERMISSION_REQUESTED
        LogUtil.i("return : PERMISSION_REQUESTED")
        return mPermissionLastStatus
    }

    // 특정 권한 허용 요청
    fun requestPermissions(
        activity: Activity?,
        requestCode: Int,
        needPermissions: ArrayList<String>
    ) {
        mRequestCode = requestCode
        ActivityCompat.requestPermissions(
            activity!!,
            needPermissions.toTypedArray<String>(),
            requestCode
        )
    }

    // 특정 권한 허용 요청

    //kcy1000-202210 - 알림권한 동의 여부
    fun getPostNotification(context: Context?): Boolean {
        return NotificationManagerCompat.from(context!!).areNotificationsEnabled()
    }

    // 권한 동의 여부.
    fun isGranted(context: Context, permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    // 앱 필수 권한 동의 여부.수
    fun isGrantedAppPermission(context: Context): Boolean {
        val lists = getAppPermissionLists()
        for (permissions in lists) {
            if (!isGranted(context, permissions)) {
                return false
            }
        }
        return true
    }

    // 전화 권한 동의 여부.
    fun isGrantedReadPhoneSatePermission(context: Context): Boolean {
        return isGranted(context, getReadPhoneSatePermission())
    }

    // 전화 권한 동의 여부.
    fun isGrantedBlueToothPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            isGranted(context, getBluetoothPermission())
        } else {
            true
        }
    }
}
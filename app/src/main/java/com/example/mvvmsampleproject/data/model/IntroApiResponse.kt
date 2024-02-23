package com.example.mvvmsampleproject.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class IntroApiResponse(
    @Expose
    @SerializedName("value")
    val value: ResultValue
) : BaseApiDto() {

    data class ResultValue(
        @Expose
        val resultCd: String,
        @Expose
        val resultDesc: String,
        @Expose
        val resultData: ResultData
    )

    data class ResultData(
        @Expose
        val introCommonData: IntroCommonData,
        @Expose
        val versionInfo: VersionInfo,
        @Expose
        val titleBarUrlList: List<TitleBarUrl>,
        @Expose
        val appSplashResDtoList: List<Intro>,
        @Expose
        val firebaseUseYn: String,
        @Expose
        val subMenu: SubMenu,
        @Expose
        val tabDisplayYn: TabDisplayYn,
        @Expose
        val tabBarUrlList: List<TabBarUrlList>
    )

    data class VersionInfo(
        @Expose
        val version: String,
        @Expose
        val popupInfo: PopupInfo
    )

    data class TitleBarUrl(
        @Expose
        val url: String
    )

    data class PopupInfo(
        @Expose
        val type: String,
        @Expose
        val title: String,
        @Expose
        val contents: String,
        @Expose
        val url: String,
        @Expose
        val linkYn: String,
        @Expose
        val billYn: String
    )

    data class Intro(
        @Expose
        val seq: String,
        @Expose
        val splashImg: String,
        @Expose
        val splashImgAlt: String,
        @Expose
        val splashImgType: String,
        @Expose
        val splashAnimationCd: String,
        @Expose
        val openDate: String,
        @Expose
        val exprDate: String,
        @Expose
        val duration: String,
        @Expose
        val size: Long,
        @Expose
        val imgWidth: String,
        @Expose
        val imgHeight: String
    )

    data class SubMenu(
        @Expose
        val searchUrl: String
    )

    data class IntroCommonData(
        @Expose
        val autoLoginRetryLimit: AutoLoginRetryLimit,
        @Expose
        val appDisplayYn: AppDisplayYn,
        @Expose
        val appSetting: AppSetting,
        @Expose
        val urlList: UrlList
    )

    data class AutoLoginRetryLimit(
        @Expose
        val minute: Int,
        @Expose
        val count: Int,
        @Expose
        val message: String
    )

    data class AppDisplayYn(
        @Expose
        val snsLogin: String,
        @Expose
        val webAccessibilityMark: String,
        @Expose
        val benefitNoticeYn: String,
        @Expose
        val shopNoticeYn: String,
        @Expose
        val attendanceShowYn: String
    )

    data class AppSetting(
        @Expose
        val forceQuitYn: String,
        @Expose
        val introRestartTime: String,
        @Expose
        val andMinimumOs: String,
        @Expose
        val introFinishTime: String,
        @Expose
        val dynatraceTaggingYn: String,
        @Expose
        val coverScreenModels: String,
        @Expose
        val lifeCycleTokenCheckTime: String
    )

    data class UrlList(
        @Expose
        val locationtermurl: String,
        @Expose
        val cancelmemberurl: String
    )

    data class TabDisplayYn(
        @Expose
        val tabMenuHome: String,
        @Expose
        val tabMenuMyBenefit: String,
        @Expose
        val tabMenuList: List<TabMenuList>
    )

    data class TabMenuList(
        @Expose
        val title: String,
        @Expose
        val url: String,
        @Expose
        val iconUrl: String
    )

    data class TabBarUrlList(
        @Expose
        val url: String
    )
}

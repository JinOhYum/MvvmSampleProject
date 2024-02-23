package com.example.mvvmsampleproject.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose

abstract class BaseApiDto {

    @Expose
    var code: String? = null // 응답코드

    @Expose
    var errorDesc: String? = null    // 오류 메시지

    @Expose
    var errorNotice: ErrorNotice? = null // 작업 공지

    data class ErrorNotice(
        @Expose
        var title: String? = null,    // 제목

        @Expose
        var desc: String? = null,     // 내용

        @Expose
        var desc1x1: String? = null,  // 내용(위젯 1x1)

        @Expose
        var desc3x1: String? = null,  // 내용(위젯 3x1)

        @Expose
        var worknotiRefreshTerm: String? = null  // 점검 공지 새로고침 주기(-1 : 갱신 안함)
    ) : Parcelable {

        // 작업 공지 새로고침 주기
        fun getCommonNoticeTerm(): Int {
            return worknotiRefreshTerm?.toIntOrNull() ?: 0
        }

        override fun describeContents(): Int {
            return 0
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeString(this.title)
            dest.writeString(this.desc)
            dest.writeString(this.desc1x1)
            dest.writeString(this.desc3x1)
            dest.writeString(this.worknotiRefreshTerm)
        }

        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
        )

        companion object CREATOR : Parcelable.Creator<ErrorNotice> {
            override fun createFromParcel(parcel: Parcel): ErrorNotice {
                return ErrorNotice(parcel)
            }

            override fun newArray(size: Int): Array<ErrorNotice?> {
                return arrayOfNulls(size)
            }
        }
    }
}


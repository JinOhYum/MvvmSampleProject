package com.example.mvvmsampleproject.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OpenLoginViewJsDto (@Expose
                               @SerializedName("url")
                               val url: String
) {
}
package com.example.mvvmsampleproject.data.model

import com.google.gson.annotations.Expose

data class CloseWindowJsDto(
    @Expose
    var reloadType: String? = null // 종료 후 갱신할 화면
) : BaseJsDto()
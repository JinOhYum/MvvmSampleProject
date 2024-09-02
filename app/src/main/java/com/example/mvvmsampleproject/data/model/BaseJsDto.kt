package com.example.mvvmsampleproject.data.model

import com.google.gson.annotations.Expose

abstract class BaseJsDto{
    @Expose(serialize = true, deserialize = false)
    var resultCd: String? = null
}


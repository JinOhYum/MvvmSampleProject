package com.example.mvvmsampleproject.viewmodel

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmsampleproject.data.model.IntroApiResponse
import com.example.mvvmsampleproject.data.repository.MainApiRepository
import com.google.gson.Gson
import com.ktshow.cs.ndkaes.NdkAes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel //@HiltViewModel Hilt ViewModel
class MainViewModel @Inject constructor(private val repository: MainApiRepository) : ViewModel() {

    private val gson : Gson = Gson()

    private val _introApiResponse = MutableLiveData<IntroApiResponse>()
    val introApiResponse : LiveData<IntroApiResponse> get() = _introApiResponse

    //api 서버통신
    fun onHttpSetAppCtnApi(){
        viewModelScope.launch {
            val response = repository.onHttpIntroApi("02","1440","2851")

            when (response.isSuccessful) {
                true ->{
                    Log.e("여기 MainViewModel","성공 ")
                    val encryptedData = response.body()?.string()
                    val decryptedData = NdkAes.decrypt(encryptedData, true)

                    // Gson을 사용하여 JSON을 IntroApiResponse 객체로 파싱
                    val introApiResponse = gson.fromJson(decryptedData, IntroApiResponse::class.java)
//
//                    // LiveData에 값을 설정
                    _introApiResponse.postValue(introApiResponse)
                }

                false ->{
                    Log.e("여기 MainViewModel","실패 ")
                }
            }

        }
    }

}
package com.example.mvvmsampleproject.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.mvvmsampleproject.R
import com.example.mvvmsampleproject.databinding.ActivityMainBinding
import com.example.mvvmsampleproject.viewmodel.MainViewModel
import com.ktshow.cs.ndkaes.NdkAes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    //'androidx.activity:activity-ktx:1.8.2' 라이브러리를 추가해야 사용가능 액티비티와 뷰모델의 연결을 쉽게 만들어줌
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        onObserve()
        init()
    }

    private fun init(){
        viewModel.onHttpSetAppCtnApi()
    }

    private fun onObserve(){

        //introApiResponse 옵저버 데이터가 변경되면 반응
        viewModel.introApiResponse.observe(this){data ->
            Log.e("여기 UI 변동사항","data "+data.code)
        }
    }
}
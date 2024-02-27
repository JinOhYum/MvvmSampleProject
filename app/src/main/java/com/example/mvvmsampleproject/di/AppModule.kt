package com.example.mvvmsampleproject.di

import android.util.Log
import com.example.mvvmsampleproject.api.ApiService
import com.example.mvvmsampleproject.api.JsBridge
import com.example.mvvmsampleproject.data.repository.MainApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt 에 핵심 Class 의존성 주입 모듈
 * Hilt 는 사용전 의존성 주입 관련 학습 필요
 * @Singleton 이 붙어있는 함수들은 앱이 실행이 되면 수명주기동안 싱글톤으로 유지가 되며 의존성 주입을 통해 객체를 공유하고 재사용 가능
 * **/
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    //주입성을 통해 레트로핏을 싱글톤으로 만들어서 사용
    @Singleton
    @Provides
    fun provideServerApi():ApiService{
        return ApiService.create()
    }


    // onRequestFromJs 메서드 추가
    /**
     * 의존성 주입 용도 이며 실제 AppModule Class 안에서 onRequestFromJs 리스너는 작동하지 않음
     * **/
    @Singleton
    @Provides
    fun provideJsCallback(): JsBridge.JsCallback {
        return object : JsBridge.JsCallback {
            override fun onRequestFromJs(requestId: Int, tag: String?, vararg params: String?) {
            }
        }
    }

    // JsBridge를 제공하는 메서드 추가
    @Singleton
    @Provides
    fun provideJsBridge(callback: JsBridge.JsCallback): JsBridge {
        return JsBridge(callback)
    }

}
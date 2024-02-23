package com.example.mvvmsampleproject.di

import com.example.mvvmsampleproject.api.ApiService
import com.example.mvvmsampleproject.api.JsBridge
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    //주입성을 통해 레트로핏을 싱글톤으로 만들어서 사용
    @Singleton
    @Provides
    fun provideServerApi():ApiService{
        return ApiService.create()
    }

}
package com.example.mvvmsampleproject

import android.util.Log
import com.example.mvvmsampleproject.api.ApiService
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun onHttpIntroApi() {
        val apiService : ApiService = ApiService.getInstance()
        val response = runBlocking { apiService.onHttpIntroApi("02","1440","2851") }

        assertNotNull(response)

        print("여기 " + response.body()!!.string())
    }
}
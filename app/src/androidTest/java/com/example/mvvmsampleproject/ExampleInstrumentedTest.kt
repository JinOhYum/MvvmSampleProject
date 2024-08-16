package com.example.mvvmsampleproject

import android.util.Log
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mvvmsampleproject.api.ApiService
import com.example.mvvmsampleproject.view.MainActivity
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import retrofit2.Response

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

//    @get:Rule
//    var activityRule = ActivityScenarioRule(MainActivity::class.java)
//
//    @Before
//    fun before(){
//        clickView()
//    }
//
//    @After
//    fun after(){
//        clickData()
//    }
//
//    @Test
//    fun clickView() {
//        Espresso.onView(withId(R.id.bt_test)).perform(click())
//    }
//
//    @Test
//    fun clickData(){
//        Espresso.onView(withId(R.id.bt_test)).check(matches(withText("헤이")))
//    }

    @Test
    fun useContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.mvvmsampleproject", appContext.packageName)
    }

}
package com.example.mvvmsampleproject.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mvvmsampleproject.util.PreferenceUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


open class BaseViewModel @Inject protected constructor(private val preferenceUtil: PreferenceUtil) : ViewModel() {

}
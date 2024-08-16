package com.example.mvvmsampleproject.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mvvmsampleproject.util.PreferenceUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

}
package com.haru.android.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    private val _emailLiveData = MutableLiveData<String>()
    val emailLiveData: LiveData<String> by lazy { _emailLiveData }




}
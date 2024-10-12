package com.pg.crm.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _title = MutableLiveData<String>()
    private val _logout = MutableLiveData<Boolean>()

    val title: LiveData<String>
        get() = _title


    val logout: LiveData<Boolean>
        get() = _logout

    fun updateActionBarTitle(title: String) = _title.postValue(title)

    fun checkIsVisible(logout:Boolean)=_logout.postValue(logout)
}

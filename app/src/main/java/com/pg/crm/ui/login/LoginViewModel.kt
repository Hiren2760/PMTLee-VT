package com.pg.crm.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pg.crm.R
import com.pg.crm.api.ApiRepository
import com.pg.crm.base.BaseViewModel
import com.pg.crm.ui.login.data.LoginBean
import com.pg.crm.ui.login.data.LoginFormState
import com.pg.crm.ui.login.data.LoginResult
import com.pg.crm.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor
    (
    private val apiRepository: ApiRepository

) : BaseViewModel() {

    private val _response: MutableLiveData<NetworkResult<LoginResult>> = MutableLiveData()
    val response: LiveData<NetworkResult<LoginResult>> = _response



    fun loginApiCall(loginBean: LoginBean) = viewModelScope.launch {
        _response.value=NetworkResult.Loading()
        apiRepository.login(loginBean).collect {
            _response.value = it
        }
    }

}

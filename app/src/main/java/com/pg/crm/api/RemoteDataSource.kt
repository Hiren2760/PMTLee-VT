package com.pg.crm.api

import com.pg.crm.ui.login.data.LoginBean
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService:  ApiService) {

    suspend fun login(loginBean: LoginBean) =
        apiService.login(loginBean)

}
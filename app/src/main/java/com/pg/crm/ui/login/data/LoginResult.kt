package com.pg.crm.ui.login.data

import com.pg.crm.ui.login.LoggedInUserView

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val Status: String? = null,
    val Message: String? = null,
    val IsAdmin:Boolean?=null,
    val UserId:String?=null,
    val EmployeeId:String?=null,
    val OfficeLocationCode:Int?=null,
    val OrgCode:Int?=null,
    val error: Int? = null,

)
package com.pg.crm.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.pg.crm.PGCRM
import com.pg.crm.ui.login.LoginActivity
import com.pg.crm.utils.NetworkResult
import com.pg.crm.utils.Prefs
import retrofit2.Response

abstract class BaseApiResponse : AppCompatActivity() {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            } else if (response.code() == 401) {
                sessionExpired()
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")


    private fun sessionExpired() {
        //Toast.makeText(this, "Session Expired. Please login again", Toast.LENGTH_SHORT).show()
        Prefs.clear()
        val intent = Intent(PGCRM.instance, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        PGCRM.instance.startActivity(intent)
        // You may need to add FLAG_ACTIVITY_NEW_TASK flag if you're starting from outside of an activity context.
        // Example: MyApp.instance.startActivity(Intent(MyApp.instance, LoginActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK })
    }
}

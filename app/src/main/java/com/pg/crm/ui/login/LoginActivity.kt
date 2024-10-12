package com.pg.crm.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import com.pg.crm.R
import com.pg.crm.base.BaseActivity
import com.pg.crm.databinding.ActivityLoginBinding
import com.pg.crm.ui.login.data.LoginBean
import com.pg.crm.ui.main.MainActivity
import com.pg.crm.utils.AppUtils
import com.pg.crm.utils.Constants
import com.pg.crm.utils.NetworkResult
import com.pg.crm.utils.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    private lateinit var _binding: ActivityLoginBinding
    private val loginViewModel:LoginViewModel by viewModels()
    override val bindingVariable: Int
        get() = BR.loginViewModel
    override val layoutId: Int
        get() =R.layout.activity_login
    override val viewModel: LoginViewModel
        get() = loginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        setupUI()


    }

    private fun setupUI() {
        if (!Prefs.getString(Constants.AUTH_TOKEN).isNullOrEmpty()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
       // _binding.toolbar.toolbarTitle.text=getString(R.string.login)
        checkLoginResponse()
        _binding.loginBT.setOnClickListener {
            if (_binding.username.text!!.isNullOrEmpty()) {
                showMessage(getString(R.string.invalid_username))
            } else if (_binding.password.text!!.isNullOrEmpty() && _binding.password.text!!.toString().length < 6) {
                showMessage(getString(R.string.invalid_password))
            } else {
                loginViewModel.loginApiCall(
                    LoginBean(
                        _binding.username.text.toString(),
                        _binding.password.text.toString(),true
                    )
                )
            }
        }


    }

    private fun checkLoginResponse() {
        loginViewModel.response.observe(this, Observer { response ->
            when (response) {
                is NetworkResult.Success -> {
                    AppUtils.hideDialog()
                    response.data?.let {
                        if(it.Status==Constants.SUCCESS) {
                            Prefs.putString(Constants.AUTH_TOKEN, it.Message)
                            Prefs.putString(Constants.USER_ID, it.UserId)
                            Prefs.putString(Constants.EMPLOYEE_ID, it.EmployeeId)
                            Prefs.putInt(Constants.ORG_OFFICE_CODE, it.OrgCode!!)

                            Prefs.putString(Constants.USER_NAME,_binding.username.text.toString())
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()

                        }else{
                            showMessage(it.Message!!)
                        }

                    }

                }

                is NetworkResult.Error -> {
                 AppUtils.hideDialog()
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    AppUtils.showLoadingDialog(this)
                }
            }
        })

    }



}
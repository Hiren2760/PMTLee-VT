package com.pg.crm.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.pg.crm.R
import com.pg.crm.databinding.ActivityMainBinding
import com.pg.crm.ui.login.LoginActivity
import com.pg.crm.utils.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

   //     val username =   binding.mainToolbar.toolbarTitle
       binding.mainToolbar.toolbarTitle.text=getString(R.string.dashBoard)
        binding.mainToolbar.logout.isVisible=true;



       viewModel.title.observe(this, Observer {
           binding.mainToolbar.toolbarTitle.text=it
        })

        viewModel.logout.observe(this) {
            binding.mainToolbar.logout.isVisible = it
        }

        binding.mainToolbar.logout.setOnClickListener{

            Prefs.clear()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.mainToolbar.backImg.setOnClickListener{
            onBackPressed()
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
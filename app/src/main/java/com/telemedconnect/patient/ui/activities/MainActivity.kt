package com.telemedconnect.patient.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.telemedconnect.patient.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val decorView = window.decorView
        val uiOptions = (View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        decorView.systemUiVisibility = uiOptions

        println("stored token: $accessToken")

        lifecycleScope.launch {
            val accessToken = withContext(Dispatchers.IO){
                getAccessToken()
            }

            val user = withContext(Dispatchers.IO){
                getUser()
            }

            if (accessToken == null || user == null) {
                startActivity(Intent(applicationContext, OnboardingActivity::class.java))
                finish()
            } else {
                println("stored user: $user")
                if (user.notComplete()) {
                    startActivity(Intent(applicationContext, InsuranceActivity::class.java))
                    finish()
                }else {
                    startActivity(Intent(applicationContext, DashboardActivity::class.java))
                    finish()
                }
            }
        }
    }
}
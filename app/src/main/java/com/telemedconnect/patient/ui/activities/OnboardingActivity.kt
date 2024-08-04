package com.telemedconnect.patient.ui.activities

import android.content.Intent
import android.os.Bundle
import com.telemedconnect.patient.databinding.ActivityOnboardingBinding
import com.telemedconnect.patient.ui.adapters.OnboardingViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.telemedconnect.patient.utlis.Constants.OPTION
import com.telemedconnect.patient.utlis.Constants.SIGN_IN
import com.telemedconnect.patient.utlis.Constants.SIGN_UP

class OnboardingActivity : BaseActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the activity reference to the binding
        binding.activity = this

        // Set up ViewPager2 with the adapter
        binding.viewPager.isSaveFromParentEnabled = true
        binding.viewPager.adapter = OnboardingViewPagerAdapter(this)

        TabLayoutMediator(binding.pageIndicator, binding.viewPager) { _, _ -> }.attach()
    }

    fun signIn(){
        val intent = Intent(this, SignInUpActivity::class.java)
        intent.putExtra(OPTION, SIGN_IN)
        startActivity(intent)
        finish()
    }

    fun createAccount(){
        val intent = Intent(this, SignInUpActivity::class.java)
        intent.putExtra(OPTION, SIGN_UP)
        startActivity(intent)
        finish()
    }
}
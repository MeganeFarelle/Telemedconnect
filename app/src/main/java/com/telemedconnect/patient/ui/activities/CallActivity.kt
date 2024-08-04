package com.telemedconnect.patient.ui.activities

import android.os.Bundle
import com.telemedconnect.patient.App
import com.telemedconnect.patient.R
import com.telemedconnect.patient.databinding.ActivityCallBinding
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallConfig
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallFragment

class CallActivity : BaseActivity() {

    private lateinit var binding: ActivityCallBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addCallFragment()
    }

    private fun addCallFragment(){
        val callID = intent.getStringExtra("call_id")
        val userID = intent.getStringExtra("user_id")
        val userName = intent.getStringExtra("user_name")

        println("Zego $callID, $userID, $userName")

        val config = ZegoUIKitPrebuiltCallConfig.oneOnOneVideoCall()
        val fragment = ZegoUIKitPrebuiltCallFragment.newInstance(
            App.APP_ID, App.APP_SIGN, userID!!, userName!!, callID!!, config
        )

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commitNow()
    }

}
package com.telemedconnect.patient.ui.activities

import android.content.Intent
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.telemedconnect.patient.api.models.ChangeRoleRequest
import com.telemedconnect.patient.databinding.ActivityBridgeBinding
import com.telemedconnect.patient.ui.adapters.BridgeViewPagerAdapter
import com.telemedconnect.patient.utlis.Constants.ADD_PRO_INFO
import com.telemedconnect.patient.utlis.Constants.OPERATION
import com.telemedconnect.patient.utlis.Constants.ROLE


class BridgeActivity : BaseActivity() {

    private lateinit var binding: ActivityBridgeBinding
    private var operation = ADD_PRO_INFO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBridgeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the activity reference to the binding
        binding.activity = this

        operation = intent.getStringExtra(OPERATION)?: ADD_PRO_INFO

        // Set up ViewPager2 with the adapter
        binding.viewPager.isSaveFromParentEnabled = true
        binding.viewPager.adapter = BridgeViewPagerAdapter(this)

        TabLayoutMediator(binding.pageIndicator, binding.viewPager) { _, _ -> }.attach()
    }

    override fun onRoleChanged() {
        val intent = Intent(this, ProfessionalInfoActivity::class.java)
        intent.putExtra(ROLE, binding.viewPager.currentItem)
        intent.putExtra(OPERATION, operation)
        startActivity(intent)
        finish()
    }

    fun onSelect(){

        when(binding.viewPager.currentItem){
            0->{
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            }
            else-> {
                val roleChangeRequest = ChangeRoleRequest(new_role = binding.viewPager.currentItem)
                changeRole(roleChangeRequest)
            }
        }
    }

}
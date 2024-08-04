package com.telemedconnect.patient.ui.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.telemedconnect.patient.R
import com.telemedconnect.patient.databinding.ActivityDashboardBinding
import com.telemedconnect.patient.databinding.LayoutDashboardTabBinding
import com.telemedconnect.patient.ui.adapters.DashboardViewPagerAdapter
import com.telemedconnect.patient.ui.view_models.DashboardViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zegocloud.zimkit.services.ZIMKit
import im.zego.zim.enums.ZIMErrorCode

class DashboardActivity : BaseActivity() {

    private lateinit var binding: ActivityDashboardBinding

    private lateinit var viewModel : DashboardViewModel
    private lateinit var pagerAdapter: DashboardViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        viewModel.accessToken = accessToken

        pagerAdapter = DashboardViewPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter

        val tabTitles = arrayOf(
            R.string.home,
            R.string.search,
            R.string.calender,
            R.string.message,
            R.string.profile
        )

        val tabIcons = arrayOf(
            R.drawable.ic_home,
            R.drawable.ic_search,
            R.drawable.ic_calender,
            R.drawable.ic_message,
            R.drawable.avatar_profile
        )

        // Set a listener to update tab text color on selection
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.view.isSelected = true
                tab.customView?.findViewById<TextView>(R.id.tabText)?.apply{
                    setTextColor(ContextCompat.getColor(this@DashboardActivity, R.color.blue))
                }

                if(tab.position != 4) {
                    tab.customView?.findViewById<ImageView>(R.id.tabIcon)?.apply {
                        setColorFilter(ContextCompat.getColor(this@DashboardActivity, R.color.blue))
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.view.isSelected = false
                tab.customView?.findViewById<TextView>(R.id.tabText)?.apply{
                    setTextColor(ContextCompat.getColor(this@DashboardActivity, R.color.black))
                }

                if(tab.position != 4) {
                    tab.customView?.findViewById<ImageView>(R.id.tabIcon)?.apply {
                        setColorFilter(ContextCompat.getColor(this@DashboardActivity, R.color.black))
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            val tabBinding = LayoutDashboardTabBinding.inflate(layoutInflater)
            tabBinding.tabIcon.setImageResource(tabIcons[position])
            tabBinding.tabText.text = resources.getText(tabTitles[position])
            tab.customView = tabBinding.root
        }.attach()

        viewModel.name.set(user?.first_name)
        viewModel.user = user

        val userAvatar = ""
        val userName = user?.first_name?: ""
        val userId = user?.user_id?: ""

        ZIMKit.connectUser(userId, userName, userAvatar) { errorInfo ->
            if (errorInfo.code == ZIMErrorCode.SUCCESS) {
                viewModel.messagingReady.set(true)
                pagerAdapter.onMessagingReady()
            } else {
                viewModel.messagingError.set(false)
            }
        }

        viewModel.loadAppointments()
        viewModel.fetchDoctorsBySpeciality()
    }

    fun goToCalender(){
        binding.viewPager.currentItem = 2
    }
}
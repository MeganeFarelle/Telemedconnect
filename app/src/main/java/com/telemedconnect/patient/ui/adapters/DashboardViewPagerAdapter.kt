package com.telemedconnect.patient.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.telemedconnect.patient.ui.activities.BaseActivity
import com.telemedconnect.patient.ui.fragments.CalenderFragment
import com.telemedconnect.patient.ui.fragments.HomeFragment
import com.telemedconnect.patient.ui.fragments.MessagingFragment
import com.telemedconnect.patient.ui.fragments.OnboardingFragment
import com.telemedconnect.patient.ui.fragments.ProfileFragment
import com.telemedconnect.patient.ui.fragments.SearchFragment
import com.telemedconnect.patient.ui.fragments.TemporalMessagingFragment


class DashboardViewPagerAdapter(activity: BaseActivity) : FragmentStateAdapter(activity) {

    private val fragments = mutableListOf(
        HomeFragment(),
        SearchFragment(),
        CalenderFragment(),
        TemporalMessagingFragment(),
        ProfileFragment()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun onMessagingReady(){
        fragments[3] = MessagingFragment()
    }
}
package com.telemedconnect.patient.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.telemedconnect.patient.lists.OnboardingItemsList
import com.telemedconnect.patient.ui.activities.BaseActivity
import com.telemedconnect.patient.ui.fragments.OnboardingFragment

class OnboardingViewPagerAdapter(activity: BaseActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = OnboardingItemsList.onboardingItems.size

    override fun createFragment(position: Int): Fragment = OnboardingFragment.newInstance(position)

}
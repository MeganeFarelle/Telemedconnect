package com.telemedconnect.patient.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.telemedconnect.patient.lists.BridgeItemsList
import com.telemedconnect.patient.ui.activities.BaseActivity
import com.telemedconnect.patient.ui.fragments.BridgeFragment

class BridgeViewPagerAdapter(activity: BaseActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = BridgeItemsList.bridgeItems.size

    override fun createFragment(position: Int): Fragment = BridgeFragment.newInstance(position)

}
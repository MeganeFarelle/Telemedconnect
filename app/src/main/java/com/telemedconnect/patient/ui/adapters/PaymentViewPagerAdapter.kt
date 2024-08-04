package com.telemedconnect.patient.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.telemedconnect.patient.ui.activities.BaseActivity
import com.telemedconnect.patient.ui.fragments.ChoosePaymentMethodFragment
import com.telemedconnect.patient.ui.fragments.PaymentSuccessFragment
import com.telemedconnect.patient.ui.fragments.SavePaymentFragment


class PaymentViewPagerAdapter(activity: BaseActivity) : FragmentStateAdapter(activity) {

    private val fragments = listOf(
        ChoosePaymentMethodFragment(),
        SavePaymentFragment(),
        PaymentSuccessFragment()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}
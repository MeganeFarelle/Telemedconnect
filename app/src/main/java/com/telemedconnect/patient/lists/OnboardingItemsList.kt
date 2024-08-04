package com.telemedconnect.patient.lists

import com.telemedconnect.patient.R
import com.telemedconnect.patient.data.models.OnboardingItem

object OnboardingItemsList {
    var onboardingItems = listOf(
        OnboardingItem(
            1,
            R.drawable.bg_angle_corners,
            R.drawable.ic_onboarding_image_1,
            R.string.onboarding_title_1,
            R.string.onboarding_desc_1
        ),

        OnboardingItem(
            2,
            R.drawable.bg_star_corners,
            R.drawable.ic_onboarding_image_2,
            R.string.onboarding_title_2,
            R.string.onboarding_desc_2
        ),
    )
}
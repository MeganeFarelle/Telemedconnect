package com.telemedconnect.patient.lists

import com.telemedconnect.patient.R
import com.telemedconnect.patient.data.models.Country

object CountriesList {
    var countries = listOf(
        Country("Ghana", "GH", R.drawable.flag_gh, "+233"),
        Country("Mauritius", "MU", R.drawable.flag_mu, "+230"),
        Country("Nigeria", "NG", R.drawable.flag_ng, "+234"),
        Country("United Kingdom", "UK", R.drawable.flag_uk, "+44"),
        Country("United States", "US", R.drawable.flag_us, "+1")
    )
}
package com.telemedconnect.patient.ui.view_models

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.telemedconnect.patient.api.Api
import com.telemedconnect.patient.data.entities.Transaction
import com.telemedconnect.patient.data.models.Country
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChooseCountryViewModel(application: Application) : AndroidViewModel(application) {
    val searchBoxVisibility = ObservableField<Int>(View.GONE)
    val countryListVisibility = ObservableField<Int>(View.GONE)
    val noResultsVisibility = ObservableField<Int>(View.GONE)
    var isSearchVisible = false

    fun toggleSearch () {
        if (isSearchVisible) {
            searchBoxVisibility.set(View.GONE)
            isSearchVisible = false
        } else {
            searchBoxVisibility.set(View.VISIBLE)
            isSearchVisible = true
        }
    }
}
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TransactionsViewModel(application: Application) : AndroidViewModel(application) {

    val processingBarVisibility = ObservableField<Int>(View.VISIBLE)
    val noTransactionsVisibility = ObservableField<Int>(View.GONE)
    val transactionsVisibility = ObservableField<Int>(View.GONE)
    val searchIconVisibility = ObservableField<Int>(View.GONE)
    val searchBoxVisibility = ObservableField<Int>(View.GONE)
    val noResultsVisibility = ObservableField<Int>(View.GONE)

    private var isSearchVisible = false

    private val _transactions = MutableLiveData<List<Transaction>>()
    val transactions: LiveData<List<Transaction>> get() = _transactions

    init {
        loadTransactions()
    }

    private fun loadTransactions(){
        val context = getApplication<Application>().applicationContext
        viewModelScope.launch {
            delay(2000)
            _transactions.value = Api.getTransactions(context)
        }
    }

    fun toggleSearch () {
        if (isSearchVisible) {
            searchBoxVisibility.set(View.GONE)
        } else {
            searchBoxVisibility.set(View.VISIBLE)
        }

        isSearchVisible =! isSearchVisible
    }
}
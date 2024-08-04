package com.telemedconnect.patient.ui.activities


import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.telemedconnect.patient.data.entities.PaymentMethod
import com.telemedconnect.patient.databinding.ActivityPaymentMethodsBinding
import com.telemedconnect.patient.ui.adapters.PaymentViewPagerAdapter
import com.telemedconnect.patient.ui.view_models.PaymentMethodsViewModel

class PaymentMethodsActivity : BaseActivity() {

    private lateinit var binding: ActivityPaymentMethodsBinding
    lateinit var viewModel: PaymentMethodsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentMethodsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(PaymentMethodsViewModel::class.java)

        binding.activity = this
        binding.viewModel = viewModel
        binding.viewPager.adapter = PaymentViewPagerAdapter(this)
        binding.viewPager.isUserInputEnabled = false

        onBackPressedDispatcher.addCallback(this, callback)
    }

    fun onStoredPaymentMethodClicked(item : PaymentMethod){
        binding.viewPager.currentItem = 1
    }

    fun onOtherPaymentMethodClicked(item : PaymentMethod){
        binding.viewPager.currentItem = 1
    }

    fun backPressed(){
        viewModel.activeMethod.set(PaymentMethod())
        if (binding.viewPager.currentItem != 0){
            binding.viewPager.currentItem = 0
        }else{
            this.finish()
        }
    }

    val callback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            backPressed()
        }
    }

}
package com.telemedconnect.patient.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.telemedconnect.patient.databinding.FragmentPaymentSuccessfulBinding
import com.telemedconnect.patient.ui.activities.PaymentMethodsActivity
import com.telemedconnect.patient.ui.activities.PersonalInformationActivity
import com.telemedconnect.patient.ui.activities.TransactionsActivity
import com.telemedconnect.patient.ui.view_models.PaymentMethodsViewModel

class PaymentSuccessFragment : Fragment() {

    private var _binding: FragmentPaymentSuccessfulBinding?= null
    private val binding get() = _binding!!
    private val viewModel: PaymentMethodsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentSuccessfulBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onEditProfile(){
        startActivity(Intent(requireContext(), PersonalInformationActivity::class.java))
    }

    fun onViewTransactions(){
        startActivity(Intent(requireContext(), TransactionsActivity::class.java))
    }

    fun onViewPaymentMethods(){
        startActivity(Intent(requireContext(), PaymentMethodsActivity::class.java))
    }

}
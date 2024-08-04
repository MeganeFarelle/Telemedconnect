package com.telemedconnect.patient.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.telemedconnect.patient.databinding.FragmentSavePaymentsBinding
import com.telemedconnect.patient.ui.activities.PaymentMethodsActivity
import com.telemedconnect.patient.ui.utils.TextTransformMethod
import com.telemedconnect.patient.ui.view_models.PaymentMethodsViewModel

class SavePaymentFragment : Fragment() {

    private var _binding: FragmentSavePaymentsBinding?= null
    private val binding get() = _binding!!
    private val viewModel: PaymentMethodsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =FragmentSavePaymentsBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.accountNoTextview.transformationMethod = TextTransformMethod(TextTransformMethod.ACCOUNT_NUMBER)

        binding.backButton.setOnClickListener {
            (activity as PaymentMethodsActivity).backPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
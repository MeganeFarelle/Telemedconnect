package com.telemedconnect.patient.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.telemedconnect.patient.databinding.FragmentProfileBinding
import com.telemedconnect.patient.ui.activities.AccountPlanActivity
import com.telemedconnect.patient.ui.activities.BridgeActivity
import com.telemedconnect.patient.ui.activities.PaymentMethodsActivity
import com.telemedconnect.patient.ui.activities.PersonalInformationActivity
import com.telemedconnect.patient.ui.activities.TransactionsActivity
import com.telemedconnect.patient.ui.view_models.DashboardViewModel
import com.telemedconnect.patient.utlis.Constants.CHANGE_ROLE
import com.telemedconnect.patient.utlis.Constants.EDIT_PROFILE
import com.telemedconnect.patient.utlis.Constants.OPERATION

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding?= null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
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
        val intent = Intent(requireContext(), PersonalInformationActivity::class.java)
        intent.putExtra(OPERATION, EDIT_PROFILE)
        startActivity(intent)
    }

    fun onViewTransactions(){
        startActivity(Intent(requireContext(), TransactionsActivity::class.java))
    }

    fun onViewPaymentMethods(){
        startActivity(Intent(requireContext(), PaymentMethodsActivity::class.java))
    }

    fun onViewAccountsPlan(){
        startActivity(Intent(requireContext(), AccountPlanActivity::class.java))
    }

    fun onChangeRole(){
        val intent = Intent(requireContext(), BridgeActivity::class.java)
        intent.putExtra(OPERATION, CHANGE_ROLE)
        startActivity(intent)
    }

}
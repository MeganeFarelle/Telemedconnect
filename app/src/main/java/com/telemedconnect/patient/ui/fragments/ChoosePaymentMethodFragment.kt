package com.telemedconnect.patient.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.telemedconnect.patient.data.entities.PaymentMethod
import com.telemedconnect.patient.databinding.FragmentChoosePaymentMethodBinding
import com.telemedconnect.patient.lists.PaymentMethodsList
import com.telemedconnect.patient.ui.activities.PaymentMethodsActivity
import com.telemedconnect.patient.ui.adapters.PaymentMethodsAdapter
import com.telemedconnect.patient.ui.utils.SpaceItemDecoration
import com.telemedconnect.patient.ui.view_models.PaymentMethodsViewModel

class ChoosePaymentMethodFragment : Fragment() {

    private var _binding: FragmentChoosePaymentMethodBinding?= null
    private val binding get() = _binding!!
    private val viewModel: PaymentMethodsViewModel by activityViewModels()

    private lateinit var storedPaymentMethodsAdapter : PaymentMethodsAdapter
    private lateinit var otherPaymentMethodsAdapter : PaymentMethodsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChoosePaymentMethodBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val otherPaymentMethods = PaymentMethodsList.payment_methods.toMutableList()
        otherPaymentMethodsAdapter = PaymentMethodsAdapter(requireContext(), items = otherPaymentMethods)
        otherPaymentMethodsAdapter.setOnItemClickedListener(object : PaymentMethodsAdapter.OnItemClickListener{
            override fun onItemClicked(item: PaymentMethod) {
                viewModel.activeMethod.set(item)
                (activity as PaymentMethodsActivity).onOtherPaymentMethodClicked(item)
            }
        })

        binding.otherMethodsRecyclerView.apply {
            adapter = otherPaymentMethodsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(SpaceItemDecoration(30, 0))
        }

        storedPaymentMethodsAdapter = PaymentMethodsAdapter(requireContext(), type = 1)
        storedPaymentMethodsAdapter.setOnItemClickedListener(object : PaymentMethodsAdapter.OnItemClickListener{
            override fun onItemClicked(item: PaymentMethod) {
                viewModel.activeMethod.set(item)
                (activity as PaymentMethodsActivity).onStoredPaymentMethodClicked(item)
            }
        })

        binding.storedMethodsRecyclerView.apply {
            adapter = storedPaymentMethodsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        binding.viewModel?.paymentMethods?.observe(viewLifecycleOwner) { data ->
            storedPaymentMethodsAdapter.setData(data)
            viewModel.processingBarVisibility.set(View.GONE)

            if(data.isEmpty()){
                viewModel.saveMethodsVisibility.set(View.GONE)
                viewModel.noSaveMethodVisibility.set(View.VISIBLE)
            }else{
                viewModel.noSaveMethodVisibility.set(View.GONE)
                viewModel.saveMethodsVisibility.set(View.VISIBLE)
            }
        }

        binding.backButton.setOnClickListener {
            (activity as PaymentMethodsActivity).backPressed()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
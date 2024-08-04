package com.telemedconnect.patient.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.telemedconnect.patient.R
import com.telemedconnect.patient.lists.DashboardActionsList
import com.telemedconnect.patient.databinding.FragmentHomeBinding
import com.telemedconnect.patient.demo.DoctorActivity
import com.telemedconnect.patient.ui.activities.DashboardActivity
import com.telemedconnect.patient.ui.adapters.AppointmentsAdapter
import com.telemedconnect.patient.ui.adapters.DashboardActionsAdapter
import com.telemedconnect.patient.ui.utils.SpaceItemDecoration
import com.telemedconnect.patient.ui.view_models.DashboardViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding ?= null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by activityViewModels()

    private lateinit var generalNeedsAdapter : DashboardActionsAdapter
    private lateinit var specialNeedsAdapter : DashboardActionsAdapter
    private lateinit var appointmentsAdapter : AppointmentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        generalNeedsAdapter = DashboardActionsAdapter(
            requireContext(), DashboardActionsList.items_general_needs.toMutableList()
        )

        specialNeedsAdapter = DashboardActionsAdapter(
            requireContext(), DashboardActionsList.items_special_needs.toMutableList()
        )

        val itemDecoration = SpaceItemDecoration(50, 5)

        binding.generalNeedsRecyclerView.apply {
            adapter = generalNeedsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(itemDecoration)
        }

        binding.generalNeedsRecyclerView.setOnClickListener {
            activity?.startActivity(Intent(requireContext(), DoctorActivity::class.java))
        }

        binding.specialNeedsRecyclerView.setOnClickListener {
            activity?.startActivity(Intent(requireContext(), DoctorActivity::class.java))
        }

        binding.specialNeedsRecyclerView.apply {
            adapter = specialNeedsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(itemDecoration)
        }

        appointmentsAdapter = AppointmentsAdapter(requireContext())
        binding.appointmentsRecyclerView.apply {
            adapter = appointmentsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(itemDecoration)
        }

        viewModel.appointments.observe(viewLifecycleOwner, Observer {data ->
            appointmentsAdapter.setData(data)
            viewModel.processingBarVisibility.set(View.GONE)

            if(data.isEmpty()){
                viewModel.appointmentVisibility.set(View.GONE)
                viewModel.noAppointments.set(View.VISIBLE)
            }else{
                viewModel.noAppointments.set(View.GONE)
                viewModel.appointmentVisibility.set(View.VISIBLE)
            }
        })

        specialNeedsToggle()
        generalNeedsToggle()
        appointmentsToggle()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun generalNeedsToggle(){
        generalNeedsAdapter.toggle()
        if(generalNeedsAdapter.isExpanded){
            binding.genNeedsMore.text = resources.getText(R.string.see_less)
        }else{
            binding.genNeedsMore.text = resources.getText(R.string.see_more_actions)
        }
    }

    fun specialNeedsToggle(){
        specialNeedsAdapter.toggle()
        if(specialNeedsAdapter.isExpanded){
            binding.specNeedsMore.text = resources.getText(R.string.see_less)
        }else{
            binding.specNeedsMore.text = resources.getText(R.string.see_more_actions)
        }
    }

    fun appointmentsToggle(){
        appointmentsAdapter.toggle()
        if(appointmentsAdapter.isExpanded){
            binding.appsNeedsMore.text = resources.getText(R.string.see_less)
        }else{
            binding.appsNeedsMore.text = resources.getText(R.string.see_all)
        }
    }

    fun goToCalender(){
        (activity as DashboardActivity).goToCalender()
    }

}
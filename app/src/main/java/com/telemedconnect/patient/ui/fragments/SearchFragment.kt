package com.telemedconnect.patient.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.telemedconnect.patient.data.entities.Account
import com.telemedconnect.patient.data.entities.Doctor
import com.telemedconnect.patient.lists.DashboardActionsList
import com.telemedconnect.patient.databinding.FragmentSearchBinding
import com.telemedconnect.patient.data.models.DashboardAction
import com.telemedconnect.patient.ui.activities.DashboardActivity
import com.telemedconnect.patient.ui.adapters.DashboardActionsAdapter
import com.telemedconnect.patient.ui.adapters.DoctorsAdapter
import com.telemedconnect.patient.ui.utils.SpaceItemDecoration
import com.telemedconnect.patient.ui.view_models.DashboardViewModel


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding ?= null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by activityViewModels()

    private lateinit var itemsAdapter : DashboardActionsAdapter
    private lateinit var doctorsAdapter: DoctorsAdapter

    lateinit var user : Account

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items = mutableListOf<DashboardAction>()
        items.apply {
            addAll(DashboardActionsList.items_general_needs)
            addAll(DashboardActionsList.items_special_needs)
        }

        val itemDecoration = SpaceItemDecoration(40, 40, 1, 2)

        itemsAdapter = DashboardActionsAdapter(requireContext(), items, 1)
        binding.recyclerView.adapter = itemsAdapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.addItemDecoration(itemDecoration)

        binding.searchLayout.isEndIconVisible = !binding.searchBox.text.isNullOrEmpty()

        binding.searchBox.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                print("search ${s.toString()}")
                activity?.runOnUiThread {
                    binding.searchLayout.isEndIconVisible = !s.isNullOrEmpty()
                    itemsAdapter.filterData(s.toString())
                    viewModel.fetchDoctorsBySpeciality(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        val itemDeco = SpaceItemDecoration(40, 0, 0, 0)
        doctorsAdapter = DoctorsAdapter(requireContext(), object : DoctorsAdapter.OnItemClickListener{
            override fun onItemClick(item: Doctor) {
                (activity as DashboardActivity).showBookAppointmentDialog(item)
            }
        })

        binding.doctorsRecyclerView.apply {
            adapter = doctorsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(itemDeco)
        }

        viewModel.doctors.observe(viewLifecycleOwner, Observer {data ->
            doctorsAdapter.setData(data)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
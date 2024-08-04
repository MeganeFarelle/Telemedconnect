package com.telemedconnect.patient.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.telemedconnect.patient.data.entities.Appointment
import com.telemedconnect.patient.databinding.FragmentCalenderBinding
import com.telemedconnect.patient.ui.activities.CallActivity
import com.telemedconnect.patient.ui.adapters.CalenderItemsAdapter
import com.telemedconnect.patient.ui.utils.SpaceItemDecoration
import com.telemedconnect.patient.ui.view_models.DashboardViewModel
import com.zegocloud.zimkit.common.ZIMKitRouter
import com.zegocloud.zimkit.common.enums.ZIMKitConversationType

class CalenderFragment : Fragment() {

    private lateinit var binding: FragmentCalenderBinding
    private val viewModel: DashboardViewModel by activityViewModels()
    private lateinit var itemsAdapter : CalenderItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalenderBinding.inflate(inflater, container, false)

        binding.fragment = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemDecoration = SpaceItemDecoration(20, 0)

        itemsAdapter = CalenderItemsAdapter()
        itemsAdapter.setOnItemClickListener(object : CalenderItemsAdapter.OnItemClickListener{

            override fun onItemClicked(item: Appointment) {

            }

            override fun onMessage(item: Appointment) {
                ZIMKitRouter.toMessageActivity(
                    requireContext(),
                    "telemedconnect-${item.invitee_id}",
                    ZIMKitConversationType.ZIMKitConversationTypePeer
                )
            }

            override fun onCall(item: Appointment) {
                val intent = Intent(requireContext(), CallActivity::class.java)
                intent.putExtra("call_id", item.id)
                intent.putExtra("user_id", item.organiser_id)
                intent.putExtra("user_name", viewModel.name.get())
                requireContext().startActivity(intent)

                println("call clicked")
            }

        })

        binding.itemsRecyclerView.apply {
            adapter = itemsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(itemDecoration)
        }

        viewModel.appointments.observe(viewLifecycleOwner, Observer {data ->
            itemsAdapter.updateAppointments(data)
            viewModel.processingBarVisibility.set(View.GONE)
        })

    }

}
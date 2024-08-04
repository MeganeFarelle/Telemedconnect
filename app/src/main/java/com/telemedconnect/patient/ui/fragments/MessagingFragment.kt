package com.telemedconnect.patient.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.telemedconnect.patient.databinding.FragmentMessagingBinding
import com.telemedconnect.patient.ui.view_models.DashboardViewModel

class MessagingFragment : Fragment() {

    private lateinit var binding: FragmentMessagingBinding

    private val viewModel: DashboardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessagingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pos = arguments?.getInt(POSITION_ARG)
        pos?.let {

        }
    }

    companion object{
        var POSITION_ARG = "position_arg"
        @JvmStatic
        fun newInstance(position: Int) = MessagingFragment().apply {
            arguments = Bundle().apply {
                putInt(POSITION_ARG, position)
            }
        }
    }
}
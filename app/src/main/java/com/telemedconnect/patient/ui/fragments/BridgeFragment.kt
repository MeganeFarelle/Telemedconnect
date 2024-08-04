package com.telemedconnect.patient.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.telemedconnect.patient.databinding.FragmentBridgeBinding
import com.telemedconnect.patient.lists.BridgeItemsList

class BridgeFragment : Fragment() {

    private lateinit var binding: FragmentBridgeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBridgeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pos = arguments?.getInt(POSITION_ARG)
        pos?.let {
            val item = BridgeItemsList.bridgeItems[pos]
            binding.image.setImageDrawable(ResourcesCompat.getDrawable(resources, item.image, null))
            binding.title.text = getString(item.title)
            binding.desc.text = getString(item.desc)
        }
    }

    companion object{
        var POSITION_ARG = "position_arg"
        @JvmStatic
        fun newInstance(position: Int) = BridgeFragment().apply {
            arguments = Bundle().apply {
                putInt(POSITION_ARG, position)
            }
        }
    }
}
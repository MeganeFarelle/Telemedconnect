package com.telemedconnect.patient.ui.fragments

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.telemedconnect.patient.R
import com.telemedconnect.patient.databinding.FragmentTermsOfUseBinding

class TermsOfUseFragment : Fragment() {
    private var _binding: FragmentTermsOfUseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTermsOfUseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.termsOfUseText.text = Html.fromHtml(getString(R.string.terms_of_use_text), Html.FROM_HTML_MODE_LEGACY)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
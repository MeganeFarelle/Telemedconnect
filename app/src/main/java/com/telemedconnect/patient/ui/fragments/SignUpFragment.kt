package com.telemedconnect.patient.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.telemedconnect.patient.api.models.AuthRequest
import com.telemedconnect.patient.databinding.FragmentSignUpBinding
import com.telemedconnect.patient.ui.activities.SignInUpActivity
import com.telemedconnect.patient.ui.utils.BaseInterface
import com.telemedconnect.patient.ui.view_models.SignInUpViewModel

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var baseActivity: SignInUpActivity

    private val viewModel: SignInUpViewModel by activityViewModels()

    interface ActionListener : BaseInterface{
        fun onPrivacyPolicy()
        fun onTermsOfUse()
        fun onGotoSignIn()
    }

    private var listener: ActionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Ensure the parent activity implements the listener interface
        if (context is ActionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement SignUpFragment ActionListener")
        }

        baseActivity = (activity as SignInUpActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.viewModel = viewModel
        binding.actionListener = listener
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun requestSignUp(){

        val authRequestObject = AuthRequest(
            email = viewModel.email.get()!!,
            password = viewModel.password.get()!!
        )

        baseActivity.requestSignUp(authRequestObject)
    }
}
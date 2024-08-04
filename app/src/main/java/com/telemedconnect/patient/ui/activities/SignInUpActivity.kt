package com.telemedconnect.patient.ui.activities

import PrivacyPolicyFragment
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.telemedconnect.patient.R
import com.telemedconnect.patient.databinding.ActivitySignInUpBinding
import com.telemedconnect.patient.ui.fragments.SignInFragment
import com.telemedconnect.patient.ui.fragments.SignUpFragment
import com.telemedconnect.patient.ui.fragments.TermsOfUseFragment
import com.telemedconnect.patient.ui.view_models.SignInUpViewModel
import com.telemedconnect.patient.utlis.Constants.NEW_PROFILE
import com.telemedconnect.patient.utlis.Constants.OPERATION
import com.telemedconnect.patient.utlis.Constants.OPTION
import com.telemedconnect.patient.utlis.Constants.SIGN_IN
import com.telemedconnect.patient.utlis.Constants.SIGN_UP


class SignInUpActivity : BaseActivity(), SignUpFragment.ActionListener,
    SignInFragment.ActionListener {

    private lateinit var binding: ActivitySignInUpBinding
    private lateinit var viewModel : SignInUpViewModel

    private var operation = SIGN_IN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SignInUpViewModel::class.java)

        val intent = intent
        if(intent != null && intent.hasExtra(OPTION)){
            operation = intent.getStringExtra(OPTION)?: SIGN_IN
            when(operation){
                SIGN_UP ->{
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragmentContainer, SignUpFragment())
                    }
                }
                SIGN_IN ->{
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragmentContainer, SignInFragment())
                    }
                }
            }
        }
    }

    override fun onPrivacyPolicy() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragmentContainer, PrivacyPolicyFragment())
            addToBackStack(null)
        }
    }

    override fun onTermsOfUse() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragmentContainer, TermsOfUseFragment())
            addToBackStack(null)
        }
    }

    override fun onGotoSignIn() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragmentContainer, SignInFragment())
        }

        operation = SIGN_IN
    }

    override fun onGotoSignUp() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragmentContainer, SignUpFragment())
        }

        operation = SIGN_UP
    }

    override fun onForgotPassword() {

    }

    override fun onVcValidated() {
        refreshSession()
    }

    override fun onSessionRefreshed() {
        getAccount()
    }

    override fun onAccountReady() {
        when(operation) {
            SIGN_UP -> {
                val intent = Intent(applicationContext, PersonalInformationActivity::class.java)
                intent.putExtra(OPERATION, NEW_PROFILE)
                startActivity(intent)
                finish()
            }

            SIGN_IN -> {
                if(user!!.notComplete()){
                    val intent = Intent(applicationContext, PersonalInformationActivity::class.java)
                    intent.putExtra(OPERATION, NEW_PROFILE)
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(applicationContext, BridgeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
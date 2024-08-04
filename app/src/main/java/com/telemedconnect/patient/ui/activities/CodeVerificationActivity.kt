package com.telemedconnect.patient.ui.activities


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.ObservableField
import androidx.lifecycle.lifecycleScope
import com.telemedconnect.patient.api.RetrofitClient
import com.telemedconnect.patient.api.models.ResendVcRequest
import com.telemedconnect.patient.api.models.ResendVcResponse
import com.telemedconnect.patient.api.models.ValidateVcRequest
import com.telemedconnect.patient.api.models.ValidateVcResponse
import com.telemedconnect.patient.databinding.ActivityCodeVerificationBinding
import com.telemedconnect.patient.utlis.Constants.EMAIL
import com.telemedconnect.patient.utlis.Constants.ACCESS_TOKEN
import com.telemedconnect.patient.utlis.Constants.VC_TOKEN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class CodeVerificationActivity : BaseActivity() {

    private lateinit var binding: ActivityCodeVerificationBinding
    private var otpFields: List<EditText>? = null

    private var extras: Bundle? = null
    private var email: String? = null
    private var vc_token: String? = null
    private var access_token: String? = null

    private var countDownTimer : CountDownTimer? = null
    val time = ObservableField<Int>(30)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCodeVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activity = this

        extras = intent?.extras
        email = intent?.getStringExtra(EMAIL)
        vc_token = intent?.getStringExtra(VC_TOKEN)
        access_token = intent?.getStringExtra(ACCESS_TOKEN)

        binding.email.text = email

        otpFields = listOf(
            binding.otpDigit1,
            binding.otpDigit2,
            binding.otpDigit3,
            binding.otpDigit4
        )

        otpFields!!.forEachIndexed {index, editText ->

            addFocusChangedListener(editText)

            editText.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
                    // Handle delete (backspace) key press here
                    if (index > 0 && editText.text.isNullOrEmpty()) {
                        otpFields!![index - 1].requestFocus()
                        otpFields!![index - 1].text.clear()
                    }else{
                        editText.text.clear()
                    }
                    true
                } else {
                    false
                }
            }

            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1) {
                        if (index < otpFields!!.size - 1) {
                            otpFields!![index + 1].requestFocus()
                        }
                    }
                    focusFirstEmpty()
                }
            })
        }

        countDownTimer = object : CountDownTimer(30000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                time.set((millisUntilFinished/1000).toInt())
            }

            override fun onFinish() {
                binding.time.visibility = View.GONE
                binding.resendCode.visibility = View.VISIBLE
            }
        }

        resetTimer()

        onBackPressedDispatcher.addCallback(this){
            finish()
        }
    }

    private fun resetTimer(){
        countDownTimer?.cancel()
        countDownTimer?.start()

        binding.resendCode.visibility = View.GONE
        binding.time.visibility = View.VISIBLE
    }

    private fun focusFirstEmpty(){
        for (editText in otpFields!!) {
            if (editText.text.toString().isEmpty()) {
                editText.onFocusChangeListener = null
                editText.requestFocus()
                addFocusChangedListener(editText)
                break
            }
        }
    }

    private fun addFocusChangedListener(editText: EditText){
        editText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus){
                focusFirstEmpty()
            }
        }
    }

    private fun onSuccess(accessToken : String? = null){
        val returnIntent = Intent()
        extras?.putString(ACCESS_TOKEN, accessToken)
        returnIntent.putExtras(extras!!)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    fun resendCode(){

        val resendVcRequest = ResendVcRequest(
            email = email!!,
            vc_token = vc_token!!
        )

        val call = RetrofitClient.createAuthService(accessToken).resendVc(resendVcRequest)

        call.enqueue(object : Callback<ResendVcResponse> {
            override fun onResponse(call: Call<ResendVcResponse>, response: Response<ResendVcResponse>) {

                hideLoadingDialog()
                var vcResponse = response.body()

                if (response.isSuccessful) {
                    vc_token = vcResponse?.vc_token
                }else{
                    vcResponse = ResendVcResponse.fromJson(response.errorBody()!!.string())
                }

                Toast.makeText(
                    applicationContext,
                    "${vcResponse?.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onFailure(call: Call<ResendVcResponse>, t: Throwable) {
                Timber.tag("sign-in-error").v(t.message.toString())
                hideLoadingDialog()
            }

        })

        resetTimer()
    }

    fun onVerify(){

        showLoadingDialog()

        val validateVcRequest = ValidateVcRequest(
            vc_code = otpFields!!.joinToString("") { it.text.toString() },
            vc_token = vc_token!!
        )

        val call = RetrofitClient.createAuthService(accessToken).verifyVc(validateVcRequest)

        call.enqueue(object : Callback<ValidateVcResponse> {
            override fun onResponse(call: Call<ValidateVcResponse>, response: Response<ValidateVcResponse>) {

                hideLoadingDialog()
                var vcResponse = response.body()

                if (response.isSuccessful) {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO){
                            saveAccessToken(vcResponse?.access_token!!)
                        }
                        showAccessToken("vc")
                        onSuccess(vcResponse?.access_token)
                        finish()
                    }
                } else {
                    vcResponse = ValidateVcResponse.fromJson(response.errorBody()!!.string())
                    println("error ${response.errorBody()!!.string()}")

                    Toast.makeText(
                        applicationContext,
                        "${vcResponse.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onFailure(call: Call<ValidateVcResponse>, t: Throwable) {
                Timber.tag("sign-in-error").v(t.message.toString())

                Toast.makeText(
                    applicationContext,
                    t.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
                hideLoadingDialog()
            }

        })
    }

}
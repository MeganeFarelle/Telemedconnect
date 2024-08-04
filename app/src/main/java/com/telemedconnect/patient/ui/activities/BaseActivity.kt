package com.telemedconnect.patient.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.telemedconnect.patient.R
import com.telemedconnect.patient.api.RetrofitClient
import com.telemedconnect.patient.api.models.AccountResponse
import com.telemedconnect.patient.api.models.AppointmentResponse
import com.telemedconnect.patient.api.models.AuthRequest
import com.telemedconnect.patient.api.models.AuthResponse
import com.telemedconnect.patient.api.models.BaseResponse
import com.telemedconnect.patient.api.models.ChangeRoleRequest
import com.telemedconnect.patient.api.models.ChangeRoleResponse
import com.telemedconnect.patient.api.models.GetProInfoResponse
import com.telemedconnect.patient.api.models.RefreshSessionRequest
import com.telemedconnect.patient.api.models.RefreshSessionResponse
import com.telemedconnect.patient.data.entities.Account
import com.telemedconnect.patient.data.entities.Appointment
import com.telemedconnect.patient.data.entities.Doctor
import com.telemedconnect.patient.data.entities.ProInfo
import com.telemedconnect.patient.data.helpers.PreferencesHelper
import com.telemedconnect.patient.data.local.AppDatabase
import com.telemedconnect.patient.data.models.Country
import com.telemedconnect.patient.utlis.Constants.ACCESS_TOKEN
import com.telemedconnect.patient.utlis.Constants.EMAIL
import com.telemedconnect.patient.utlis.Constants.OPERATION
import com.telemedconnect.patient.utlis.Constants.SIGN_IN
import com.telemedconnect.patient.utlis.Constants.SIGN_UP
import com.telemedconnect.patient.utlis.Constants.VC_TOKEN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.Locale

/**
 * This is the base activity for the app
 */

open class BaseActivity : AppCompatActivity() {

    private var loadingDialog: Dialog? = null
    private lateinit var context: Context
    private lateinit var preferencesHelper: PreferencesHelper

    companion object {
        var user: Account? = null
        var accessToken: String? = null
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this

        // Hide the navigation bar and enter full-screen mode
        val decorView = window.decorView
        val uiOptions = (
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                )

        decorView.systemUiVisibility = uiOptions

        val db = AppDatabase.getDatabase(this)
        val preferenceDao = db.preferenceDao()
        preferencesHelper = PreferencesHelper(preferenceDao)

        loadingDialog = Dialog(this, android.R.style.Theme_Translucent_NoTitleBar).apply {
            val view = LayoutInflater.from(this@BaseActivity).inflate(R.layout.layout_loading_dialog, null)
            setContentView(view)

            // Optional: Set the dialog to be non-cancelable
            setCancelable(true)

            // Set the dialog to fill the screen
            window?.let { window ->
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
                window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                window.setDimAmount(0.5f)
            }
        }



    }

    override fun finish() {
        hideLoadingDialog()
        super.finish()
    }

    private val otpVerificationLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
        if(result.resultCode == Activity.RESULT_OK){
            lifecycleScope.launch {
                withContext(Dispatchers.IO){
                    saveAccessToken(result.data?.getStringExtra(ACCESS_TOKEN)!!)
                }
                onVcValidated()
            }
        }
    }

    private val chooseCountryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val country: Country? = data?.getParcelableExtra("country")
            if (country != null){
                onCountrySelected(country)
            }
        }
    }

    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions->
            if(permissions[Manifest.permission.CAMERA] == true && permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] == true){
                cameraLauncher.launch(null)
            }else{
                Toast.makeText(this, "Camera permission is required.", Toast.LENGTH_SHORT).show()
            }
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()){ bitmap ->
        if(bitmap != null){onImageReady(bitmap)}
    }

    fun showAccessToken(stage: String?= null) {
        println("access token ($stage): $accessToken")
    }

    fun startCamera(){
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED ->{
                cameraLauncher.launch(null)
            }else ->{
                cameraPermissionLauncher.launch(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            }
        }
    }

    open fun chooseCountry(){
        val intent = Intent(this, ChooseCountryActivity::class.java)
        chooseCountryLauncher.launch(intent)
    }

    open fun validateVC(bundle: Bundle){
        val intent = Intent(this, CodeVerificationActivity::class.java)
        intent.putExtras(bundle)
        otpVerificationLauncher.launch(intent)
    }

    suspend fun saveUser(user_: Account){
        user = user_
        preferencesHelper.saveAccount("user", user_)
    }

    suspend fun saveAccessToken(token: String){
        accessToken = token
        preferencesHelper.saveString("access_token", token)
    }

    suspend fun getUser(): Account?{
        user = preferencesHelper.getAccount("user")
        return user
    }

    suspend fun getAccessToken() : String?{
        accessToken = preferencesHelper.getString("access_token")
        return accessToken
    }

    open fun onImageReady(imageBitmap: Bitmap){}

    open fun onCountrySelected(county: Country){}

    open fun onVcValidated(){}

    open fun onSessionRefreshed(){}

    open fun onAccountReady(){}

    open fun onAccountUpdated(){}

    open fun onRoleChanged(){}

    open fun onProfessionalInfoChanged(){}

    open fun onChangeDashBoard(dashboard: Int?){}

    open fun showLoadingDialog(){
        loadingDialog?.show()
    }

    open fun hideLoadingDialog(){
        loadingDialog?.hide()
    }

    open fun showBookAppointmentDialog(item : Doctor){

        var date : String = ""
        var state_time : String = ""
        var end_time : String = ""

        Dialog(this, R.style.FullScreenDialog).apply {
            val view = LayoutInflater.from(this@BaseActivity).inflate(R.layout.layout_book_appointment, null)
            setContentView(view)

            val details = findViewById<TextView>(R.id.details)
            details.text = Html.fromHtml(item.generateHtml(), Html.FROM_HTML_MODE_LEGACY)
            details.movementMethod = LinkMovementMethod.getInstance()

            val dateTV = findViewById<TextView>(R.id.date)
            dateTV.setOnClickListener{
                // Get the current date
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                // Create a DatePickerDialog and set the selected date in the TextView
                val datePickerDialog = DatePickerDialog(
                    context,
                    { _, selectedYear, selectedMonth, selectedDay ->
                        // Update the TextView with the selected date
                        val formattedDate = "${selectedYear}-${selectedMonth + 1}-${selectedDay}"
                        dateTV.text = formattedDate
                        date = formattedDate
                    },
                    year, month, day
                )
                datePickerDialog.show()

            }

            val startTimeTV = findViewById<TextView>(R.id.start_time)
            startTimeTV.setOnClickListener{

                val calendar = Calendar.getInstance()
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                val minute = calendar.get(Calendar.MINUTE)

                val timePickerDialog = TimePickerDialog(context, { _, selectedHour, selectedMinute ->
                    val selectedTime = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, selectedHour)
                        set(Calendar.MINUTE, selectedMinute)
                    }
                    val formattedTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(selectedTime.time)
                    startTimeTV.text = formattedTime
                    state_time = formattedTime

                }, hour, minute, true)

                timePickerDialog.show()
            }

            val endTimeTV = findViewById<TextView>(R.id.end_time)
            endTimeTV.setOnClickListener{

                val calendar = Calendar.getInstance()
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                val minute = calendar.get(Calendar.MINUTE)

                val timePickerDialog = TimePickerDialog(context, { _, selectedHour, selectedMinute ->
                    val selectedTime = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, selectedHour)
                        set(Calendar.MINUTE, selectedMinute)
                    }
                    val formattedTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(selectedTime.time)
                    endTimeTV.text = formattedTime
                    end_time = formattedTime

                }, hour, minute, true)

                timePickerDialog.show()
            }

            val pp = findViewById<EditText>(R.id.purpose)

            val proceed = findViewById<TextView>(R.id.proceed)
            proceed.setOnClickListener{
                val appointment = Appointment()
                appointment.invitee_id = item.user_id
                appointment.start_time = "$date'T${state_time}:00'Z'"
                appointment.end_time = "$date'T${end_time}:00'Z'"
                appointment.purpose = pp.text.toString()
                appointment.mode = "Online"
                appointment.location = "Zoom"
                appointment.coordinates = ""
                bookAppointment(appointment)

                dismiss()
            }

            // Optional: Set the dialog to be non-cancelable
            setCancelable(true)

            // Set the dialog to fill the screen
            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        }.show()

    }

    open fun requestSignUp(authRequestObject: AuthRequest){

        val call = RetrofitClient.createAuthService().signupAccount(authRequestObject)

        call.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {

                hideLoadingDialog()
                var authResponse = response.body()

                if (response.isSuccessful){
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO){
                            saveAccessToken(authResponse?.access_token!!)
                        }
                        showAccessToken("signup")
                        validateVC(
                            Bundle().apply {
                                putString(OPERATION, SIGN_UP)
                                putString(EMAIL, authRequestObject.email)
                                putString(VC_TOKEN, authResponse?.vc_token)
                                putString(ACCESS_TOKEN, authResponse?.access_token)
                            }
                        )
                    }
                }else{
                    authResponse =AuthResponse.fromJson(response.errorBody()!!.string())

                    Toast.makeText(
                        applicationContext,
                        "${authResponse?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Signup failed", Toast.LENGTH_SHORT).show()
                Timber.tag("sign-up-error").v(t.message.toString())
                hideLoadingDialog()
            }

        })
    }


    open fun requestSignIn(authRequestObject: AuthRequest){

        showLoadingDialog()

        val call = RetrofitClient.createAuthService().signInAccount(authRequestObject)

        call.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {

                hideLoadingDialog()
                var authResponse = response.body()

                if (response.isSuccessful) {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO){
                            saveAccessToken(authResponse?.access_token!!)
                        }
                        showAccessToken("signin")
                        validateVC(
                            Bundle().apply {
                                putString(OPERATION, SIGN_IN)
                                putString(EMAIL, authRequestObject.email)
                                putString(VC_TOKEN, authResponse?.vc_token)
                                putString(ACCESS_TOKEN, authResponse?.access_token)
                            }
                        )
                    }
                }else{
                    authResponse = AuthResponse.fromJson(response.errorBody()!!.string())

                    Toast.makeText(
                        applicationContext,
                        "${authResponse.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Signin failed", Toast.LENGTH_SHORT).show()
                Timber.tag("sign-in-error").v(t.message.toString())
                hideLoadingDialog()
            }

        })
    }

    open fun refreshSession(){

        showLoadingDialog()

        val refreshRequest = RefreshSessionRequest()

        val call = RetrofitClient.createAuthService(accessToken).refreshSession(refreshRequest)

        call.enqueue(object : Callback<RefreshSessionResponse> {
            override fun onResponse(call: Call<RefreshSessionResponse>, response: Response<RefreshSessionResponse>) {

                hideLoadingDialog()
                var refreshResponse = response.body()

                if (response.isSuccessful) {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO){
                            saveAccessToken(refreshResponse!!.access_token!!)
                        }
                        showAccessToken("refresh")
                        onSessionRefreshed()
                    }
                }else{
                    refreshResponse = RefreshSessionResponse.fromJson(response.errorBody()!!.string())

                    Toast.makeText(
                        applicationContext,
                        "${refreshResponse.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onFailure(call: Call<RefreshSessionResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Token Refresh Failed", Toast.LENGTH_SHORT).show()
                Timber.tag("Token Refresh Error").v(t.message.toString())
                hideLoadingDialog()
            }

        })
    }

    open fun getAccount(){

        showLoadingDialog()

        val call = RetrofitClient.createAccountService(accessToken).getAccount()

        call.enqueue(object : Callback<AccountResponse> {
            override fun onResponse(call: Call<AccountResponse>, response: Response<AccountResponse>) {

                hideLoadingDialog()
                var accountResponse = response.body()

                if (response.isSuccessful) {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO){
                            saveUser(accountResponse?.account_details!!)
                        }
                        onAccountReady()
                    }
                }else{
                    accountResponse = AccountResponse.fromJson(response.errorBody()!!.string())
                }

                Toast.makeText(
                    applicationContext,
                    "${accountResponse?.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onFailure(call: Call<AccountResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Token Refresh Failed", Toast.LENGTH_SHORT).show()
                Timber.tag("Token Refresh Error").v(t.message.toString())
                hideLoadingDialog()
            }

        })
    }


    open fun updateAccount(account : Account){

        showLoadingDialog()

        val call = RetrofitClient.createAccountService(accessToken).updateAccount(account)

        call.enqueue(object : Callback<AccountResponse> {
            override fun onResponse(call: Call<AccountResponse>, response: Response<AccountResponse>) {

                hideLoadingDialog()
                var accountResponse = response.body()

                if (response.isSuccessful) {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO){
                            saveUser(accountResponse?.account_details!!)
                        }
                        onAccountUpdated()
                    }
                }else{
                    accountResponse = AccountResponse.fromJson(response.errorBody()!!.string())

                    Toast.makeText(
                        applicationContext,
                        "${accountResponse.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onFailure(call: Call<AccountResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Token Refresh Failed", Toast.LENGTH_SHORT).show()
                Timber.tag("Token Refresh Error").v(t.message.toString())
                hideLoadingDialog()
            }
        })
    }

    open fun bookAppointment(requestBody: Appointment) {

        showLoadingDialog()
        val call = RetrofitClient.createAccountService(accessToken).bookAppointment(requestBody)

        call.enqueue(object : Callback<AppointmentResponse> {
            override fun onResponse(
                call: Call<AppointmentResponse>,
                response: Response<AppointmentResponse>
            ) {
                hideLoadingDialog()
                var appointmentResponse = response.body()
                if (response.isSuccessful) {
                    println("Message: ${appointmentResponse?.message}")
                   Toast.makeText(context, "Appointment booked successfully", Toast.LENGTH_SHORT).show()
                } else {
                    // Handle the error response
                    appointmentResponse = AppointmentResponse.fromJson(response.errorBody()!!.string())
                    Toast.makeText(context, appointmentResponse.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AppointmentResponse>, t: Throwable) {
                hideLoadingDialog()
                Toast.makeText(context, "Appointment booking failed!!!", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }

    open fun changeRole(requestBody: ChangeRoleRequest) {

        if (requestBody.new_role == user!!.role){
            Toast.makeText(context, "The new Role is the same as the current Role", Toast.LENGTH_SHORT).show()
            onChangeDashBoard(requestBody.new_role)
            return
        }

        showLoadingDialog()
        val call = RetrofitClient.createAccountService(accessToken).changeRole(requestBody)

        call.enqueue(object : Callback<ChangeRoleResponse> {
            override fun onResponse(
                call: Call<ChangeRoleResponse>,
                response: Response<ChangeRoleResponse>
            ) {
                hideLoadingDialog()
                var changeRoleResponse = response.body()
                if (response.isSuccessful) {
                    user = changeRoleResponse?.account_details
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO){
                            saveUser(user!!)
                            onRoleChanged()
                        }
                    }
                    println("Message: ${changeRoleResponse?.message}")
                } else {
                    // Handle the error response
                    changeRoleResponse = ChangeRoleResponse.fromJson(response.errorBody()!!.string())
                    Toast.makeText(context, changeRoleResponse.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ChangeRoleResponse>, t: Throwable) {
                hideLoadingDialog()
                Toast.makeText(context, "Role Change failed!!", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }

    open fun getProfessionalInfo() {

        showLoadingDialog()
        val call = RetrofitClient.createAccountService(accessToken).getProInfo()

        call.enqueue(object : Callback<GetProInfoResponse> {
            override fun onResponse(
                call: Call<GetProInfoResponse>,
                response: Response<GetProInfoResponse>
            ) {
                hideLoadingDialog()
                var getProInfoResponse = response.body()
                if (response.isSuccessful) {
                    onProfessionalInfoChanged()
                    println("Message: ${getProInfoResponse?.message}")
                } else {
                    // Handle the error response
                    getProInfoResponse = GetProInfoResponse.fromJson(response.errorBody()!!.string())
                    Toast.makeText(context, getProInfoResponse.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetProInfoResponse>, t: Throwable) {
                hideLoadingDialog()
                Toast.makeText(context, "get pro info failed!!", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }

    open fun updateProfessionalInfo(requestBody: ProInfo) {

        showLoadingDialog()
        val call = RetrofitClient.createAccountService(accessToken).addProInfo(requestBody)

        call.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(
                call: Call<BaseResponse>,
                response: Response<BaseResponse>
            ) {
                hideLoadingDialog()
                var updateProInfo = response.body()
                if (response.isSuccessful) {
                    onProfessionalInfoChanged()
                    Toast.makeText(context, "Professional Info Updated", Toast.LENGTH_SHORT).show()
                    println("Message: ${updateProInfo?.message}")
                } else {
                    // Handle the error response
                    updateProInfo = BaseResponse.fromJson(response.errorBody()!!.string())
                    Toast.makeText(context, updateProInfo.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                hideLoadingDialog()
                Toast.makeText(context, "updating pro info failed!!", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }

}
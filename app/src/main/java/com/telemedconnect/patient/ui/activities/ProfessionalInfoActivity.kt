package com.telemedconnect.patient.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.telemedconnect.patient.data.entities.ProInfo
import com.telemedconnect.patient.databinding.ActivityProfessionalInfoBinding
import com.telemedconnect.patient.ui.view_models.ProfessionalInfoViewModel
import com.telemedconnect.patient.utlis.Constants.ROLE

class ProfessionalInfoActivity : BaseActivity() {

    private lateinit var viewModel : ProfessionalInfoViewModel
    private lateinit var binding: ActivityProfessionalInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfessionalInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ProfessionalInfoViewModel::class.java)
        binding.viewModel = viewModel
        binding.activity = this

        getProfessionalInfo()
    }

    override fun onProfessionalInfoChanged() {
        if(user!!.role == 3){
            startActivity(Intent(this, SpecializationActivity::class.java))
            finish()
        }else{
            val intent = Intent(this, DashboardActivity::class.java)
            intent.putExtra(ROLE, user!!.role)
            finish()
        }
    }

    fun onContinue(){
        val proInfo = ProInfo()
        proInfo.licence = viewModel.licence.get()
        proInfo.affiliation = viewModel.affiliation.get()
        proInfo.years_practicing = Integer.parseInt(viewModel.yearsOfPractice.get()!!)

        updateProfessionalInfo(proInfo)

    }
}
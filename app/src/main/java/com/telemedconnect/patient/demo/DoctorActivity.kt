package com.telemedconnect.patient.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.telemedconnect.patient.R
import com.telemedconnect.patient.ui.activities.BaseActivity

class DoctorActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_doctor)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val doctors = listOf(
            Doctor("Dr. John Smith", "Cardiologist"),
            Doctor("Dr. Emily Johnson", "Neurologist"),
            Doctor("Dr. Michael Williams", "Orthopedic Surgeon"),
            Doctor("Dr. Jessica Brown", "Pediatrician"),
            Doctor("Dr. David Jones", "Dermatologist"),
            Doctor("Dr. Sarah Garcia", "Endocrinologist"),
            Doctor("Dr. James Martinez", "Gastroenterologist"),
            Doctor("Dr. Mary Hernandez", "Oncologist"),
            Doctor("Dr. Robert Lopez", "Ophthalmologist"),
            Doctor("Dr. Linda Gonzalez", "Psychiatrist")
        )

        val adapter = DoctorAdapter(doctors)
        recyclerView.adapter = adapter
    }
}
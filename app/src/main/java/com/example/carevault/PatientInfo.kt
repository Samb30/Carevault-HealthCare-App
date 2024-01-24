package com.example.carevault

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

class PatientInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_info)
        val back1 = findViewById<View>(R.id.backpatient)
        val nextb = findViewById<View>(R.id.nextButton1)
        nextb.setOnClickListener {
            val intent = Intent(this, booking::class.java)
            startActivity(intent)

        }

        back1.setOnClickListener {
            onBackPressed()
        }

        val genderSpinner: Spinner = findViewById(R.id.genderSpinner)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.gender_options,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = adapter

        genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }



    }
}
package com.example.carevault

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class PatientInfo : AppCompatActivity() {
    private lateinit var ename:EditText
    private lateinit var eage:EditText
    private lateinit var eprob:EditText
    private lateinit var pbar:ProgressBar
    private  var db= Firebase.firestore
    private lateinit var date: String
    private lateinit var time: String
    private lateinit var appointmentsRefPath: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_info)
        val back1 = findViewById<View>(R.id.backpatient)
        val nextb = findViewById<View>(R.id.nextButton1)
        val genderSpinner: Spinner = findViewById(R.id.genderSpinner)
        ename=findViewById(R.id.editTextFullName)
        eage=findViewById(R.id.age)
        eprob=findViewById(R.id.problemgiven)
        pbar=findViewById(R.id.pbar)
        val intent = intent
        if (intent != null) {
            appointmentsRefPath = intent.getStringExtra("appointmentsRefPath") ?: ""
            date = intent.getStringExtra("date") ?: ""
            time = intent.getStringExtra("time") ?: ""
        }

        nextb.setOnClickListener {


            val sname=ename.text.toString().trim()
            val sage=eage.text.toString().trim()
            val sprob=eprob.text.toString().trim()
            val selectedGender = genderSpinner.selectedItem.toString()
            val appointmentsRefPath1 = "Users/${FirebaseAuth.getInstance().currentUser!!.uid}/Appointments"





            val userid=FirebaseAuth.getInstance().currentUser!!.uid
            val appointmentsRef = db.document(appointmentsRefPath)
                .collection("Patient Details")
            appointmentsRef.get().addOnSuccessListener { snapshot ->
                val patientNumber = snapshot.size() + 1 // Calculate the next patient number
                val patientDocumentId = "patient$patientNumber" // Generate the document ID

                val patientDetails = hashMapOf(
                    "Full Name" to sname,
                    "Age" to sage,
                    "Problem" to sprob,
                    "Gender" to selectedGender
                )

                appointmentsRef.document(patientDocumentId)
                    .set(patientDetails)
                    .addOnSuccessListener {
                        pbar.visibility = View.VISIBLE
                        Toast.makeText(this, "added patient info", Toast.LENGTH_SHORT).show()
                        ename.text.clear()
                        eage.text.clear()
                        eprob.text.clear()

                        val intent = Intent(this, booking::class.java)
                        intent.putExtra("appointmentsRefPath", appointmentsRefPath1)
                        intent.putExtra("date", date)
                        intent.putExtra("time", time)

                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed ", Toast.LENGTH_SHORT).show()
                    }
            }


        }

        back1.setOnClickListener {
            onBackPressed()
        }



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
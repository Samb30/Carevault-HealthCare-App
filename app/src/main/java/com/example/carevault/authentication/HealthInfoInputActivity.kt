package com.example.carevault.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carevault.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HealthInfoInputActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_healthinfo)

        if (auth.currentUser == null) {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
            return
        }
        val userId = auth.currentUser?.uid

        val hl_sub = findViewById<TextView>(R.id.textView5)

        hl_sub.setOnClickListener {

            val Name = findViewById<TextView>(R.id.textView6).text.toString()
            val DOB = findViewById<TextView>(R.id.textView116).text.toString()
            val Gender = findViewById<TextView>(R.id.textView16).text.toString()

            val contactperson = findViewById<TextView>(R.id.textView8).text.toString()
            val relationship = findViewById<TextView>(R.id.textView18).text.toString()
            val contactnumber = findViewById<TextView>(R.id.textView17).text.toString()

            val disease1 = findViewById<TextView>(R.id.textView20).text.toString()
            val disease2 = findViewById<TextView>(R.id.textView21).text.toString()
            val disease3 = findViewById<TextView>(R.id.textView22).text.toString()

            userId?.let {
                val userMedicalDetailsRef = db.collection("Users").document(userId).collection("Health Information")

                val medicaldata = hashMapOf(
                    "Name" to Name,
                    "Date of Birth" to DOB,
                    "Gender" to Gender,

                    "Contact Person" to contactperson,
                    "Relationship" to relationship,
                    "Contact Number" to contactnumber,

                    "Disease 1" to disease1,
                    "Disease 2" to disease2,
                    "Disease 3" to disease3
                )

            userMedicalDetailsRef.document("details").set(medicaldata)
                .addOnSuccessListener {
                    Toast.makeText(
                        applicationContext,
                        "Medical details saved successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        applicationContext,
                        "Error saving medical details: $e",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}
}
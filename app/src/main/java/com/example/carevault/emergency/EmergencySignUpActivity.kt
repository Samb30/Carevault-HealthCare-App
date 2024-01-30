package com.example.carevault.emergency

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carevault.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Objects

class EmergencySignUpActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_sign_up)

        val backButton: ImageButton = findViewById(R.id.imageButton2)

        backButton.setOnClickListener {
            val intent = Intent(this@EmergencySignUpActivity, EmergencyLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val signUpButton: TextView = findViewById(R.id.textView5)

        signUpButton.setOnClickListener {
            if (validateInputs()) {
                val email = findViewById<EditText>(R.id.textView11).text.toString()
                val password = findViewById<EditText>(R.id.textView3).text.toString()

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val name = findViewById<EditText>(R.id.textView6).text.toString()
                            val mobileNumber = findViewById<EditText>(R.id.textView15).text.toString()

                            val userId = Objects.requireNonNull<FirebaseUser>(auth.currentUser).uid

                            val userData = hashMapOf(
                                "Name" to name,
                                "Email" to email,
                                "MobileNumber" to mobileNumber,
                                "UserId" to userId
                            )

                            db.collection("Users").document(user?.uid ?: "")
                                .set(userData)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        applicationContext,
                                        "User registered successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startActivity(Intent(this, EmergencyLoginActivity::class.java))
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(
                                        applicationContext,
                                        "Error saving user data: $e",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        } else {
                            Toast.makeText(
                                baseContext, "Registration failed. ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }
    private fun validateInputs(): Boolean {
        val nameEditText: EditText = findViewById(R.id.textView6)
        val emailEditText: EditText = findViewById(R.id.textView11)
        val passwordEditText: EditText = findViewById(R.id.textView3)
        val mobileNumberEditText: EditText = findViewById(R.id.textView15)

        val name = nameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val mobileNumber = mobileNumberEditText.text.toString().trim()

        if (name.isEmpty()) {
            nameEditText.error = "Name is required"
            return false
        }

        if (email.isEmpty()) {
            emailEditText.error = "Email is required"
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Enter a valid email address"
            return false
        }

        if (password.isEmpty()) {
            passwordEditText.error = "Password is required"
            return false
        }

        if (password.length < 8) {
            passwordEditText.error = "Password must be at least 8 characters long"
            return false
        }

        if (mobileNumber.isEmpty()) {
            mobileNumberEditText.error = "Mobile number is required"
            return false
        }

        if (!Patterns.PHONE.matcher(mobileNumber).matches()) {
            mobileNumberEditText.error = "Enter a valid mobile number"
            return false
        }

        return true
    }
}
package com.example.carevault

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Objects

class RegisterActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val spinner: Spinner = findViewById(R.id.textView14)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.country_array,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Do nothing here
            }
        }

        val backButton: ImageButton = findViewById(R.id.imageButton2)
        backButton.setOnClickListener {
            // Navigate to WelcomeActivity
            val intent = Intent(this@RegisterActivity, WelcomeActivity::class.java)
            startActivity(intent)

            // Close the current activity
            finish()
        }

        val signUpButton: TextView = findViewById(R.id.textView5)
        signUpButton.setOnClickListener {
            if (validateInputs()) {
                val email = findViewById<EditText>(R.id.textView11).text.toString()
                val password = findViewById<EditText>(R.id.textView3).text.toString()

                // Firebase Authentication: Create user with email and password
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // User registration successful
                            val user = auth.currentUser
                            val name = findViewById<EditText>(R.id.textView6).text.toString()
                            val mobileNumber = findViewById<EditText>(R.id.textView15).text.toString()
                            val country = spinner.selectedItem.toString()

                            val userId = Objects.requireNonNull<FirebaseUser>(auth.currentUser).uid

                            val userData = hashMapOf(
                                "Name" to name,
                                "Email" to email,
                                "MobileNumber" to mobileNumber,
                                "Country" to country,
                                "UserId" to userId
                            )

                            db.collection("Users").document(user?.uid ?: "")
                                .set(userData)
                                .addOnSuccessListener {
                                    // Handle success if needed
                                    Toast.makeText(
                                        applicationContext,
                                        "User registered successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    // Move to the next activity
                                    startActivity(Intent(this, HealthinfoActivity::class.java))
                                }
                                .addOnFailureListener { e ->
                                    // Handle failure if needed
                                    Toast.makeText(
                                        applicationContext,
                                        "Error saving user data: $e",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        } else {
                            // If registration fails, display a message to the user.
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
        val checkBox: CheckBox = findViewById(R.id.checkBox)

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

        if (password.length < 6) {
            passwordEditText.error = "Password must be at least 6 characters long"
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

        if (!checkBox.isChecked) {
            Toast.makeText(applicationContext, "Please agree to the terms", Toast.LENGTH_SHORT)
                .show()
            return false
        }

        return true
    }
}
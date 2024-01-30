package com.example.carevault.emergency

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carevault.R
import com.example.carevault.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth

class EmergencyLoginActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_login)

        val backButton = findViewById<ImageButton>(R.id.imageButton2)
        val loginButton = findViewById<TextView>(R.id.textView5)
        val emailEditText = findViewById<EditText>(R.id.textView6)
        val passwordEditText = findViewById<EditText>(R.id.textView3)
        val signuptext = findViewById<TextView>(R.id.textView7)

        passwordEditText.setOnTouchListener { _, event ->
            val DRAWABLE_RIGHT = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= passwordEditText.right - passwordEditText.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                    togglePasswordVisibility(passwordEditText)
                    return@setOnTouchListener true
                }
            }
            false
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "Login successful",
                                Toast.LENGTH_SHORT
                            ).show()

                            startActivity(Intent(this, EmergencyActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(
                                baseContext,
                                "Authentication failed. ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Please enter both email and password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        signuptext.setOnClickListener {
            startActivity(Intent(this, EmergencySignUpActivity::class.java))
        }

        backButton.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
        }
    }

    private fun togglePasswordVisibility(editText: EditText) {
        val selection = editText.selectionEnd
        if (editText.transformationMethod == PasswordTransformationMethod.getInstance()) {
            editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
        }
        editText.setSelection(selection)
    }
}
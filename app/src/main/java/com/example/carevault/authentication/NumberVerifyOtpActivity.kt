package com.example.carevault.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carevault.MainFragment
import com.example.carevault.R
import com.example.carevault.databinding.ActivityVerifyOtpBinding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.auth
import java.util.concurrent.TimeUnit

class NumberVerifyOtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyOtpBinding
    private var storeVerificationId: String? = ""
    private var number: String? = ""
    private lateinit var auth: FirebaseAuth
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val backButton = findViewById<ImageButton>(R.id.imageButton2)
        val resendButton = binding.textView23

        storeVerificationId = intent.getStringExtra("storedVerificationId")
        number = intent.getStringExtra("number")

        binding.textView1115.setOnClickListener {
            verifyPhoneNumberWithCode(storeVerificationId,binding.textView4556.text.toString())
        }

        backButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        resendButton.setOnClickListener {
            resendVerificationCode("+91$number")
        }
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("Success", "signInWithCredential:success")

                    val user = task.result?.user
                    val intent = Intent(this@NumberVerifyOtpActivity, MainFragment::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Log.w("Failed", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Log.w("Failed", "Invalid Code", task.exception)
                    }
                }
            }
    }

    private fun resendVerificationCode(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91$phoneNumber")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .setForceResendingToken(resendToken)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.w("Failed", "onVerificationFailed", e)
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            Log.d("Resend", "onCodeSent:$verificationId")
            storeVerificationId = verificationId
            resendToken = token
            Toast.makeText(this@NumberVerifyOtpActivity, "OTP Resent", Toast.LENGTH_SHORT).show()
        }
    }
}
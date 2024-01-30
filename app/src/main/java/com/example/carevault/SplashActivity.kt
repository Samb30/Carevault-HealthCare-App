package com.example.carevault

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startNextActivity()
    }
    private fun startNextActivity() {
        if (!isDestroyed) {
            val currentUser = FirebaseAuth.getInstance().currentUser
            val intent: Intent = if (currentUser != null) {
                Intent(this@SplashActivity, MainFragment::class.java)
            } else {
                Intent(this@SplashActivity, Onboard1Activity::class.java)
            }
            startActivity(intent)
            finish()
        }
    }
}
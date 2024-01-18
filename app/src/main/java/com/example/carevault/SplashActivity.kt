package com.example.carevault

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startActivity(Intent(this, Onboard1Activity::class.java))
        finish()
    }
//    private fun startNextActivity() {
//        if (!isDestroyed) {
//            val currentUser = FirebaseAuth.getInstance().currentUser
//            val intent: Intent = if (currentUser != null) {
//                Intent(this@SplashActivity, MainFragment::class.java)
//            } else {
//                Intent(this@SplashActivity, LoginActivity::class.java)
//            }
//            startActivity(intent)
//        }
//    }
}
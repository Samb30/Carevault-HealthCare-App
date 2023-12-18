package com.example.carevault

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val backbt = findViewById<ImageButton>(R.id.imageButton2)
        val log_sb = findViewById<TextView>(R.id.textView5)

        log_sb.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }

        backbt.setOnClickListener{startActivity(Intent(this, WelcomeActivity::class.java))}
    }
}
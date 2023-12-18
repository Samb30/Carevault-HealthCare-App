package com.example.carevault

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val reg_sub = findViewById<TextView>(R.id.textView5)

        reg_sub.setOnClickListener { startActivity(Intent(this, HealthinfoActivity::class.java)) }
    }
}
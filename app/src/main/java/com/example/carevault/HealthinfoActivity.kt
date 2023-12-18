package com.example.carevault

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class HealthinfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_healthinfo)

        val hl_sub = findViewById<TextView>(R.id.textView5)

        hl_sub.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }
    }
}

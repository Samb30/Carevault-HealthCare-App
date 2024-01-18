package com.example.carevault

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Onboard2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboard2)

        val welcomebut = findViewById<ImageView>(R.id.imageView4)

        welcomebut.setOnClickListener{startActivity(Intent(this, WelcomeActivity::class.java))
        finish()}
    }
}
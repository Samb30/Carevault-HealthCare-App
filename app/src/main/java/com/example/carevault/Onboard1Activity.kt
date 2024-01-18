package com.example.carevault

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
class Onboard1Activity : AppCompatActivity() {

    private lateinit var skipbutton : TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_onboard1)

        skipbutton = findViewById(R.id.textView1)

        val onboard2 = findViewById<ImageView>(R.id.imageView4)

        skipbutton.setOnClickListener { startActivity(Intent(this, WelcomeActivity::class.java))
            finish() }

        onboard2.setOnClickListener{startActivity(Intent(this, Onboard2Activity::class.java))
            finish()}
    }
}


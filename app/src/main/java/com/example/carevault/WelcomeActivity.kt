package com.example.carevault

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val login = findViewById<TextView>(R.id.textView5)
        val signup = findViewById<TextView>(R.id.textView6)
//        val emergency = findViewById<TextView>(R.id.textView4)

        login.setOnClickListener{startActivity(Intent(this, LoginActivity::class.java))}

        signup.setOnClickListener{startActivity(Intent(this, RegisterActivity::class.java))}

//        emergency.setOnClickListener{startActivity(Intent(this, EmergencyActivity::class.java))}

    }

}
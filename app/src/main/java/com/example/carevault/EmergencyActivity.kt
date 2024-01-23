package com.example.carevault

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EmergencyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency)

        val forButton11: ImageButton = findViewById(R.id.img11)
        val forButton12: TextView = findViewById(R.id.my_profile11)
        val forButton21: ImageButton = findViewById(R.id.imageBu2)
        val forButton22: TextView = findViewById(R.id.my_pr1)
        val forButton31: ImageButton = findViewById(R.id.ima2)
        val forButton32: TextView = findViewById(R.id.m1)
        val ExitButton: TextView = findViewById(R.id.my_pro13)

        forButton11.setOnClickListener {
            val intent = Intent(this@EmergencyActivity, EmergencyCallActivity::class.java)
            startActivity(intent)
            finish()
        }

        forButton12.setOnClickListener {
            val intent = Intent(this@EmergencyActivity, EmergencyCallActivity::class.java)
            startActivity(intent)
            finish()
        }

        forButton21.setOnClickListener {
            val intent = Intent(this@EmergencyActivity, EmergencyAmbulanceActivity::class.java)
            startActivity(intent)
            finish()
        }

        forButton22.setOnClickListener {
            val intent = Intent(this@EmergencyActivity, EmergencyAmbulanceActivity::class.java)
            startActivity(intent)
            finish()
        }

        forButton31.setOnClickListener {
            val intent = Intent(this@EmergencyActivity, EmergencyProcedureActivity::class.java)
            startActivity(intent)
            finish()
        }

        forButton32.setOnClickListener {
            val intent = Intent(this@EmergencyActivity, EmergencyProcedureActivity::class.java)
            startActivity(intent)
            finish()
        }

        ExitButton.setOnClickListener {
            val intent = Intent(this@EmergencyActivity, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
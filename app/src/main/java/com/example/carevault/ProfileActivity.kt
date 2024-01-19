package com.example.carevault

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val currentUser = auth.currentUser
        val EditButton: TextView = findViewById(R.id.my_profile13)
        val forButton1: ImageButton = findViewById(R.id.imageButton2)
        val forButton2: ImageButton = findViewById(R.id.imageButton3)
        val forButton3: ImageButton = findViewById(R.id.imageButton4)
        val forButton4: ImageButton = findViewById(R.id.imageButton5)
        val LogoutButton: TextView = findViewById(R.id.my_pro13)

        if (currentUser != null) {
            val userId = currentUser.uid
            val userDocument = firestore.collection("Users").document(userId)

            userDocument.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val name = documentSnapshot.getString("Name") ?: ""
                    val email = documentSnapshot.getString("Email") ?: ""

                    val nameTextView: TextView = findViewById(R.id.my_profile11)
                    val emailTextView: TextView = findViewById(R.id.my_profile12)

                    nameTextView.text = name
                    emailTextView.text = email
                }
            }.addOnFailureListener { e ->
            }
        }

        EditButton.setOnClickListener {
            val intent = Intent(this@ProfileActivity, EditProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        forButton1.setOnClickListener {
            val intent = Intent(this@ProfileActivity, HealthRecordActivity::class.java)
            startActivity(intent)
            finish()
        }

        forButton2.setOnClickListener {
            val intent = Intent(this@ProfileActivity, HelpSupportActivity::class.java)
            startActivity(intent)
            finish()
        }

        forButton3.setOnClickListener {
            val intent = Intent(this@ProfileActivity, AppFeedbackActivity::class.java)
            startActivity(intent)
            finish()
        }

        forButton4.setOnClickListener {
            val intent = Intent(this@ProfileActivity, AppSettingsActivity::class.java)
            startActivity(intent)
            finish()
        }

        LogoutButton.setOnClickListener {

            auth.signOut()

            val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
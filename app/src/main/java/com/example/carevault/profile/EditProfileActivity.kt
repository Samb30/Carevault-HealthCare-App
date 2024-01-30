package com.example.carevault.profile

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carevault.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
class EditProfileActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var mobileNumberEditText: EditText

    private lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        db = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()

        nameEditText = findViewById(R.id.my_profil)
        mobileNumberEditText = findViewById(R.id.my_profil13)

        fetchAndSetUserData()

        val saveButton: TextView = findViewById(R.id.textView5)
        val backButton: ImageButton = findViewById(R.id.imageButton112)

        saveButton.setOnClickListener {
            val editedName = nameEditText.text.toString()
            val editedMobileNumber = mobileNumberEditText.text.toString()

            if (isValidData(editedName, editedMobileNumber)) {
                saveEditedData(editedName, editedMobileNumber)
            } else {
            }
        }

        backButton.setOnClickListener {
            onBackPressed()
        }
    }
    private fun fetchAndSetUserData() {
        val userId = mAuth.currentUser?.uid
        val userRef: DocumentReference = db.collection("Users").document(userId!!)

        userRef.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document!!.exists()) {
                        val name = document.getString("Name")
                        val mobileNumber = document.getString("MobileNumber")

                        nameEditText.setText(name)
                        mobileNumberEditText.setText(mobileNumber)
                    } else {
                        Toast.makeText(
                            this@EditProfileActivity,
                            "User data not found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@EditProfileActivity,
                        "Error fetching user data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
    private fun saveEditedData(editedName: String, editedMobileNumber: String) {
        val userId = mAuth.currentUser?.uid
        val userRef: DocumentReference = db.collection("Users").document(userId!!)

        userRef.update(
                "Name", editedName,
                "MobileNumber", editedMobileNumber
            )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this@EditProfileActivity,
                            "Profile updated successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@EditProfileActivity,
                            "Error updating profile",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
    }
    private fun isValidData(
        name: String,
        mobileNumber: String,
    ): Boolean {
        val isMobileNumberValid = mobileNumber.length == 10

        if (name.isEmpty()) {
            nameEditText.error = "Name is Required"
            return false
        }

        if (!isMobileNumberValid) {
            mobileNumberEditText.error = "Enter a valid mobile number"
            return false
        }

        return true
    }
}
package com.example.carevault

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EmergencyCallActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private val PERMISSION_REQUEST_CODE = 123
    private val LOCATION_REQUEST_CODE = 456

    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private lateinit var numberview: TextView
    private var lastKnownLocation: Location = Location(LocationManager.NETWORK_PROVIDER)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_call)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val currentUser = auth.currentUser
        val backButton: ImageButton = findViewById(R.id.imageButton2)
        val call1Button: TextView = findViewById(R.id.my_profile13)
        val call2Button: TextView = findViewById(R.id.my_profi13)

        numberview = findViewById(R.id.my_profile12)

        if (currentUser != null) {
            val userId = currentUser.uid
            val userDocument = firestore.collection("Users").document(userId).collection("Health Information").document("details")

            userDocument.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val name = documentSnapshot.getString("Contact Person") ?: ""
                    val number = documentSnapshot.getString("Contact Number") ?: ""

                    val nameTextView: TextView = findViewById(R.id.my_profile11)
                    val numberview: TextView = findViewById(R.id.my_profile12)

                    nameTextView.text = name
                    numberview.text = number
                }
            }.addOnFailureListener {
            }
        }

        backButton.setOnClickListener {
            val intent = Intent(this@EmergencyCallActivity, EmergencyActivity::class.java)
            startActivity(intent)
            finish()
        }

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                lastKnownLocation = location
                println("Location updated: ${location.latitude}, ${location.longitude}")
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

            override fun onProviderEnabled(provider: String) {}

            override fun onProviderDisabled(provider: String) {}
        }

        call1Button.setOnClickListener {
            if (checkPermissions()) {
                startLocationUpdates()

                val contactNumber = numberview.text.toString()

                callEmergency(contactNumber, lastKnownLocation)

                sendEmergencyText(contactNumber, lastKnownLocation)
            }
            else {
                requestPermissions()
            }
        }

        call2Button.setOnClickListener {
            val intent = Intent(this@EmergencyCallActivity, HelpSupportActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun checkPermissions(): Boolean {
        val callPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED

        val smsPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.SEND_SMS
        ) == PackageManager.PERMISSION_GRANTED

        val locationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return callPermission && smsPermission && locationPermission
    }
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_REQUEST_CODE
        )
    }

    private fun callEmergency(contactNumber: String, location: Location) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$contactNumber")

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startActivity(callIntent)
        }
    }

    private fun sendEmergencyText(contactNumber: String, location: Location?) {
        val message = ("Emergency situation. Please contact immediately.\n" +
                location?.let {
                    "\nLocation: ${it.latitude}, ${it.longitude}"
                })

            try {
                val smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(contactNumber, null, message, null, null)
                Toast.makeText(this, "Emergency text sent", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Failed to send emergency text", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
    }
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0f,
                locationListener
            )
        } else {
            requestPermissions()
        }
    }
    private fun stopLocationUpdates() {
        locationManager.removeUpdates(locationListener)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (checkPermissions()) {
                val contactNumber = numberview.text.toString()
                callEmergency(contactNumber, lastKnownLocation)
                sendEmergencyText(contactNumber, lastKnownLocation)
                startLocationUpdates()
            } else {
                Toast.makeText(this, "Permissions not granted", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onStop() {
        super.onStop()
        stopLocationUpdates()
    }
}
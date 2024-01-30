package com.example.carevault.emergency

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.carevault.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.io.IOException
import java.util.Locale
class EmergencyCallActivity : AppCompatActivity() {
    var greeting: String = "Hello"
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    val REQUEST_CODE = 1
    private val PERMISSION_REQUEST_CODE = 123
    private val LOCATION_REQUEST_CODE = 456
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private lateinit var numberview: TextView
    private lateinit var callnumber: TextView
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
        callnumber = findViewById(R.id.textView15)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
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
                getLastLocation()
                val contactNumber = numberview.text.toString()

                callEmergency(contactNumber, lastKnownLocation)
            }
            else {
                requestPermissions()
            }
        }

        call2Button.setOnClickListener {
            if (checkPermissions()) {
                getLastLocation2()
                val contactNumber2 = callnumber.text.toString()

                callEmergency(contactNumber2, lastKnownLocation)
            }
            else {
                requestPermissions()
            }
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

    private fun stopLocationUpdates() {
        locationManager.removeUpdates(locationListener)
    }

    private fun askPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            } else {
                Toast.makeText(this, "Please provide the required permission", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    private fun getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        try {
                            val geocoder = Geocoder(this, Locale.getDefault())
                            val addresses = geocoder.getFromLocation(
                                location.latitude, location.longitude, 1
                            )
                            val contactNumber = numberview.text.toString()
                            greeting = addresses?.get(0)?.locality.toString()
                            val temp1=addresses?.get(0)?.latitude.toString()
                            val temp2=addresses?.get(0)?.longitude.toString()
                            val temp3=addresses?.get(0)?.postalCode.toString()
                            val temp4=addresses?.get(0)?.adminArea.toString()
                            val concatenatedMessage = "Locality: $greeting\nlatitude: $temp1\nlongitude: $temp2\npostalCode: $temp3\nadminArea: $temp4"
                            sendEmergencyText1(contactNumber,concatenatedMessage)
                            Log.d("", "")
                            Toast.makeText(
                                this,
                                concatenatedMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                    Log.d("", "hhf")
                }
        } else {
            askPermission()
        }
    }

    private fun getLastLocation2() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        try {
                            val geocoder = Geocoder(this, Locale.getDefault())
                            val addresses = geocoder.getFromLocation(
                                location.latitude, location.longitude, 1
                            )
                            val contactNumber = callnumber.text.toString()
                            greeting = addresses?.get(0)?.locality.toString()
                            val temp1=addresses?.get(0)?.latitude.toString()
                            val temp2=addresses?.get(0)?.longitude.toString()
                            val temp3=addresses?.get(0)?.postalCode.toString()
                            val temp4=addresses?.get(0)?.adminArea.toString()
                            val concatenatedMessage = "Locality: $greeting\nlatitude: $temp1\nlongitude: $temp2\npostalCode: $temp3\nadminArea: $temp4"
                            sendEmergencyText1(contactNumber,concatenatedMessage)
                            Log.d("", "")
                            Toast.makeText(
                                this,
                                concatenatedMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                    Log.d("", "hhf")
                }
        } else {
            askPermission()
        }
    }

    private fun sendEmergencyText1(contactNumber: String, message: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(contactNumber, null,
                "Emergency situation. Please contact immediately.\n $message", null, null)
            Toast.makeText(this, "Emergency text sent", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to send emergency text", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    override fun onStop() {
        super.onStop()
        stopLocationUpdates()
    }
}
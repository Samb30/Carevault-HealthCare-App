package com.example.carevault

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

//import com.example.doctorhome.databinding.ActivityTopambulanceBinding
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.storage.StorageReference

class TopambulanceActivity : AppCompatActivity() {
//    private  lateinit var binding:ActivityTopambulanceBinding
//    private lateinit var dbref:DatabaseReference
//    private lateinit var storageReference: StorageReference
//    private lateinit var dialog:Dialog
//    private lateinit var user: User
//    private lateinit var uid:String
//    private lateinit var auth:FirebaseAuth
    private lateinit var ambulance:EditText
    private lateinit var docname:TextView
    private lateinit var doctitle:TextView



    //    private lateinit var binding :ActivityTopambulanceBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topambulance)
//        binding=ActivityTopambulanceBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        val back1: ImageButton = findViewById(R.id.backbutton3)
        val searchb: ImageButton = findViewById(R.id.searchambulance)
         ambulance = findViewById(R.id.ambulancename)
        docname=findViewById(R.id.tvname)
        doctitle=findViewById(R.id.tvname2)




        back1.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
//        searchb.setOnClickListener {
//            val ambuname:String=ambulance.text.toString()
//            if(ambuname.isNotEmpty()){
//                readdata(ambuname)
//            }
//        }


    }

//    private fun readdata(ambuname: String) {
//
//        dbref=FirebaseDatabase.getInstance().getReference("users")
//        dbref.child(ambuname).get().addOnSuccessListener {
//            if (it.exists()){
//                val name=it.child("name").value
//                val title=it.child("title").value
//              Toast.makeText(this,"success",Toast.LENGTH_SHORT).show()
//                ambulance.text.clear()
//                docname.text=name.toString()
//                doctitle.text=title.toString()
//
//
//            }
//        }
//
//
//    }


}
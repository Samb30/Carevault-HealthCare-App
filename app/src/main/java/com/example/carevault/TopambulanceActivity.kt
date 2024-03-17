package com.example.carevault

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

//import com.example.doctorhome.databinding.ActivityTopambulanceBinding
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.storage.StorageReference

class TopambulanceActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var ulist:ArrayList<User>
    private lateinit var db: FirebaseFirestore
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

        rv=findViewById(R.id.recycler)
        rv.layoutManager=LinearLayoutManager(this)
        ulist= arrayListOf()
        db=FirebaseFirestore.getInstance()
        db.collection("Hospitals").get().addOnSuccessListener{
            if(!it.isEmpty){
                for(data in it.documents){
                    val user: User? =data.toObject(User::class.java)
                    if(user!=null){
                        ulist.add(user)
                    }
                }
                rv.adapter=Myadapter1(ulist)
            }
        }
            .addOnFailureListener{
                Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
            }

        back1.setOnClickListener { startActivity(Intent(this, MainFragment::class.java)) }



    }




}
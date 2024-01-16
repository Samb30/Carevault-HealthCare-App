package com.example.carevault;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class booking extends AppCompatActivity {
    ImageButton back1;
    Button booker;
    Dialog popper;
    AlertDialog.Builder builddialog;
    AlertDialog alertDialog;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        back1=findViewById(R.id.backbutton2);
        booker=findViewById(R.id.bookbuttton);
        linearLayout=findViewById(R.id.doctor_detail);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                if (currentUser != null) {
                    String userId = currentUser.getUid();
                    DocumentReference db = FirebaseFirestore.getInstance().collection("Hospitals").document(userId);

                    db.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    // Fetch the value of the "hosname" field
                                    String hosname = document.getString("Hospital Name");
                                    if (hosname != null) {
                                        // Do something with the hosname
                                        Toast.makeText(booking.this, "Hospital Name: " + hosname, Toast.LENGTH_SHORT).show();
                                    } else {
                                        // The "hosname" field is null
                                        Toast.makeText(booking.this, "Hospital Name not available", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // The document doesn't exist
                                    Toast.makeText(booking.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // An error occurred
                                Exception e = task.getException();
                                Toast.makeText(booking.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // Handle the case where currentUser is null
                    Toast.makeText(booking.this, "User not logged in", Toast.LENGTH_SHORT).show();
                }

            }
        });
        booker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note=new Note();
                note.setAppoint("first appointment");
                saveNote(note);
              //showAlertdialog(R.layout.popup);
            }
        });





        back1.setOnClickListener(v -> {
            onBackPressed();



        });

    }
    public void showAlertdialog(int mylayout){
       builddialog =new AlertDialog.Builder(this);
       View lay=getLayoutInflater().inflate(mylayout,null);
       Button dialogb=lay.findViewById(R.id.doneb);
       builddialog.setView(lay);
       alertDialog=builddialog.create();
       alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       //alertDialog.show();
       dialogb.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               alertDialog.dismiss();
           }
       });

    }
    void saveNote(Note note) {
        DocumentReference documentReference;
        {
            documentReference=Utility.getCollectionReference().document();
        }
        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(booking.this, "Note added", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(booking.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
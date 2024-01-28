package com.example.carevault;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class booking extends AppCompatActivity {
    ImageButton back1;
    private String appointmentsRefPath;
    Button booker;
    Dialog popper;
    AlertDialog.Builder builddialog;
    AlertDialog alertDialog;
    private FirebaseFirestore db;
    private TextView dayText;
    private TextView timeText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        back1=findViewById(R.id.backbutton2);
        booker=findViewById(R.id.bookbuttton);
        db = FirebaseFirestore.getInstance();
        dayText = findViewById(R.id.dayText);
        timeText = findViewById(R.id.timeText);
        Intent inte = getIntent();
        if (inte != null) {
            appointmentsRefPath = inte.getStringExtra("appointmentsRefPath");
            if (appointmentsRefPath == null) {
                appointmentsRefPath = "";
            }
        }

//        fetchAndDisplayAppointments();
        booker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              showAlertdialog(R.layout.popup);
            }
        });





        back1.setOnClickListener(v -> {
            Intent intent = new Intent(booking.this, MainFragment.class);

            startActivity(intent);



        });

    }
    public void showAlertdialog(int mylayout){
       builddialog =new AlertDialog.Builder(this);
       View lay=getLayoutInflater().inflate(mylayout,null);
       Button dialogb=lay.findViewById(R.id.doneb);
       builddialog.setView(lay);
       alertDialog=builddialog.create();
       alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       alertDialog.show();
       dialogb.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               alertDialog.dismiss();
           }
       });

    }
    private void fetchAndDisplayAppointments() {
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference appointmentsRef = db.document(appointmentsRefPath);

        appointmentsRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                // Document exists, fetch and display data
                String day = documentSnapshot.getString("DOA");
                String time = documentSnapshot.getString("Time");

                // Display data in TextViews
                dayText.setText(day);
                timeText.setText(time);
            } else {
                // Document does not exist
                Toast.makeText(this, "No appointments found", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            // Handle failure
            Toast.makeText(this, "Failed to fetch appointments", Toast.LENGTH_SHORT).show();
        });
    }
}
package com.example.carevault;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;



public class MainActivity extends AppCompatActivity  {
    Button dctrall;
    ImageButton expanddoc;
    ImageButton notificat;
    ImageButton hosp;
    ImageButton ambulance;
    ImageButton articles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dctrall=findViewById(R.id.topdoctors);
        expanddoc=findViewById(R.id.Doctor);
        notificat=findViewById(R.id.notifyb);
        hosp=findViewById(R.id.hospital);
        ambulance=findViewById(R.id.ambulance);
        articles=findViewById(R.id.article);



        expanddoc.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, doctordetailMainActivity3.class);
            startActivity(intent);
        });



        dctrall.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);

            startActivity(intent);



        });
        hosp.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TophospitalActivity.class);
            startActivity(intent);



        });
        ambulance.setOnClickListener(v -> {
            Intent intent = new Intent(this, TopambulanceActivity.class);
            startActivity(intent);



        });
        articles.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, booking.class);

            startActivity(intent);

        });

    }
}
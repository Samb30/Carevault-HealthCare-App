package com.example.carevault;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

public class TophospitalActivity extends AppCompatActivity {
    ImageButton back1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tophospital);
        back1=findViewById(R.id.backbutton4);

        back1.setOnClickListener(v -> {
            onBackPressed();



        });
    }
}
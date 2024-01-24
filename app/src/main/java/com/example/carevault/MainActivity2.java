package com.example.carevault;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity2 extends AppCompatActivity {
    ImageButton back1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        back1=findViewById(R.id.backbutton);

        back1.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, MainFragment.class);
            // now by putExtra method put the value in key, value pair key is
            // message_key by this key we will receive the value, and put the string

            // start the Intent
            startActivity(intent);



        });

    }
}
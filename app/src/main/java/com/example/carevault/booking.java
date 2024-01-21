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

public class booking extends AppCompatActivity {
    ImageButton back1;
    Button booker;
    Dialog popper;
    AlertDialog.Builder builddialog;
    AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        back1=findViewById(R.id.backbutton2);
        booker=findViewById(R.id.bookbuttton);
        booker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              showAlertdialog(R.layout.popup);
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
       alertDialog.show();
       dialogb.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               alertDialog.dismiss();
           }
       });

    }

}
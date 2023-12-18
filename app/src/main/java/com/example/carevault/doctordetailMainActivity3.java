package com.example.carevault;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class doctordetailMainActivity3 extends AppCompatActivity {
    private TextView descriptionTextView;
    private TextView readMoreTextView;
    private boolean isExpanded = false;
    ImageButton back2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctordetail_main3);
        descriptionTextView=findViewById(R.id.aboutdoctr);
        readMoreTextView=findViewById(R.id.textmore);
        back2=findViewById(R.id.backbutton2);


        back2.setOnClickListener(v -> {
            Intent intent = new Intent(doctordetailMainActivity3.this, MainActivity.class);

            startActivity(intent);



        });

        readMoreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    // Collapse the description
                    descriptionTextView.setMaxLines(3);
                    readMoreTextView.setText("Read More");
                } else {
                    // Expand the description
                    descriptionTextView.setMaxLines(Integer.MAX_VALUE);
                    readMoreTextView.setText("Read Less");
                }
                isExpanded = !isExpanded;
            }
        });
    }
}
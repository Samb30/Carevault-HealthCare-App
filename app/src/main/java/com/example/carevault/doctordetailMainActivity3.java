package com.example.carevault;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.carevault.Adapters.Note2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class doctordetailMainActivity3 extends AppCompatActivity {
    private TextView descriptionTextView;
    private TextView readMoreTextView;
    private  TextView textdt;
    private  TextView stim;
    private Button btn;
    private Button tbtn;
    private boolean isExpanded = false;
    ImageButton back2;
    Button nextbt;
    private TextView edate;
    private TextView etime;


//    private ProgressBar pbar;
    private FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctordetail_main3);
        descriptionTextView=findViewById(R.id.aboutdoctr);
        readMoreTextView=findViewById(R.id.textmore);
        back2=findViewById(R.id.backbutton2);
        nextbt=findViewById(R.id.nextButton);
        textdt=findViewById(R.id.dt);
        btn=findViewById(R.id.btndatettime);
        tbtn=findViewById(R.id.timer);
        stim=findViewById(R.id.stime);
        edate=findViewById(R.id.dt);
        etime=findViewById(R.id.stime);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datetime();
            }
        });
        tbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timershow();
            }
        });

        nextbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedatetimeinfo();
            }
        });
        db = FirebaseFirestore.getInstance();



        back2.setOnClickListener(v -> {
            Intent intent = new Intent(doctordetailMainActivity3.this, MainFragment.class);

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

    private void datetime(){
        DatePickerDialog  dia=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yr, int month, int day) {
                textdt.setText(String.valueOf(yr)+"."+String.valueOf(month+1)+"."+String.valueOf(day));

            }
        }, 2024, 0, 0);
        dia.show();
    }
    private void timershow(){
        TimePickerDialog dialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hr, int min) {
                stim.setText(String.valueOf(hr)+"."+String.valueOf(min));
            }
        }, 15, 00, true);
        dialog.show();
    }
    private void savedatetimeinfo() {
        String sdat = edate.getText().toString().trim();
        String sti = etime.getText().toString().trim();


        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference appointmentsRef = db.collection("Users").document(userid)
                .collection("Appointments").document();


        Map<String, Object> patientDetails = new HashMap<>();
        patientDetails.put("DOA", sdat);
        patientDetails.put("Time", sti);

        Note2 note2 = new Note2();
        note2.setTime(sti);
        note2.setDateM(sdat);

        DocumentReference documentReference;
        {
            documentReference = Utility.getCollectionReferenceForBooking().document();
        }
        documentReference.set(note2).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(doctordetailMainActivity3.this, "Note added", Toast.LENGTH_SHORT).show();
                    edate.setText("");
                    etime.setText("");


                    Intent intent = new Intent(doctordetailMainActivity3.this, PatientInfo.class);
                    intent.putExtra("appointmentsRefPath", documentReference.getPath());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(doctordetailMainActivity3.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
//        appointmentsRef.set(patientDetails)
//                .addOnSuccessListener(aVoid -> {
//
//                    Toast.makeText(doctordetailMainActivity3.this, "Added date time  info", Toast.LENGTH_SHORT).show();
//                    edate.setText("");
//                    etime.setText("");
//
//
//
//                    Intent intent = new Intent(doctordetailMainActivity3.this, PatientInfo.class);
//                    intent.putExtra("appointmentsRefPath", appointmentsRef.getPath());
//                    startActivity(intent);
//                })
//                .addOnFailureListener(e -> Toast.makeText(doctordetailMainActivity3.this, "Failed", Toast.LENGTH_SHORT).show());
//    }

}
package com.example.carevault.Booking;

import static com.google.firebase.appcheck.internal.util.Logger.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carevault.Adapters.modelPatient;
import com.example.carevault.MainFragment;
import com.example.carevault.R;
import com.example.carevault.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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
        booker=findViewById(R.id.textView5);
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
                String date = getIntent().getStringExtra("date");
                String slot = getIntent().getStringExtra("slot");
                String Pname = getIntent().getStringExtra("Pname");
                String Page = getIntent().getStringExtra("Page");
                String Pproblem = getIntent().getStringExtra("Pproblem");
                String dname=getIntent().getStringExtra("dname");
                String temp=getIntent().getStringExtra("docId");
                String category=getIntent().getStringExtra("category");
                modelPatient modelPatient=new modelPatient();
                modelPatient.setName(Pname);
                modelPatient.setAge(Page);
                modelPatient.setProblem(Pproblem);
                modelPatient.setTime(slot);
                modelPatient.setDate(date);
                modelPatient.setCategory(category);
                modelPatient.setDname(dname);
                modelPatient.setDocid(temp);
                Toast.makeText(booking.this, date, Toast.LENGTH_SHORT).show();
                func(slot,date,temp);
            }
        });
        back1.setOnClickListener(v -> {
            Intent intent = new Intent(booking.this, MainFragment.class);
            startActivity(intent);
        });

    }
    private  void func(String s,String te,String temp){
        FirebaseFirestore.getInstance().collection("Doctors")
                .document(temp).collection("Date")
                .whereEqualTo("date", te) // Replace "field" with your field name and value with the value you're looking for
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ProgressDialog Dialog = new ProgressDialog(booking.this);
                                Toast.makeText(booking.this, "Booked2", Toast.LENGTH_SHORT).show();
                                Dialog.setMessage("please wait a moment..");
                                Dialog.show();
                                String documentId = document.getId();
                                updateDocumentField(documentId,s,temp); // position is the position of grid item clicked
                            }
                        } else {
                            // Handle errors
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    private void updateDocumentField(String docId,String selectedSeat,String temp) {

        Toast.makeText(this, ""+docId, Toast.LENGTH_SHORT).show();
        FirebaseUser curr = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Doctors")
                .document(temp).collection("Date").document(docId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    ProgressDialog Dialog = new ProgressDialog(booking.this);
                    Dialog.setMessage("please wait a moment..");
                    Dialog.show();
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> data = new HashMap<>();
                        Map<String, Boolean> times = (Map<String, Boolean>) document.get("times");
                        if (times != null) {
                            String selectedTime = "17:00"; // Implement this method
                            //Toast.makeText(temp.this, "="+selectedTime, Toast.LENGTH_SHORT).show();
                            boolean flag=times.get(selectedSeat);
                            if(flag==false){
                                showAlertdialog(R.layout.popup2);
                                return;
                            }
                            times.put(selectedSeat, false);
                            data.put("times", times);
                            docRef.update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        modelPatient modelPatient=new modelPatient();
                                        solve(modelPatient);
                                        DocumentReference documentReference;
                                        documentReference= Utility.getCollectionReferenceForBooking().document();
                                        documentReference.set(modelPatient).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                }
                                                else{
                                                    Toast.makeText(booking.this, "Failed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        showAlertdialog(R.layout.popup);
                                    } else {
                                        // Handle errors
                                        Toast.makeText(booking.this, "Failed to update time", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    } else {
                        // Document does not exist
                        Toast.makeText(booking.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                    Dialog.dismiss();
                } else {
                    // Task failed with an exception
                    Toast.makeText(booking.this, "Failed to fetch document", Toast.LENGTH_SHORT).show();
                }
            }
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
               Intent intent = new Intent(booking.this, MainFragment.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(intent);
               finish();
           }
       });
    }
    void solve(modelPatient modelPatient){
        String date = getIntent().getStringExtra("date");
        String slot = getIntent().getStringExtra("slot");
        String[] dateComponents = date.split("-");
        int year = Integer.parseInt(dateComponents[0]);
        int month = Integer.parseInt(dateComponents[1]) - 1;
        int day = Integer.parseInt(dateComponents[2]);
        int hour=Integer.parseInt(slot.substring(0,2));
        // Create a Calendar object with the parsed date
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, 0, 0);
        long milliseconds = calendar.getTimeInMillis();
        String Pname = getIntent().getStringExtra("Pname");
        String Page = getIntent().getStringExtra("Page");
        String Pproblem = getIntent().getStringExtra("Pproblem");
        String dname=getIntent().getStringExtra("dname");
        String temp=getIntent().getStringExtra("docId");
        String category=getIntent().getStringExtra("category");
        modelPatient.setName(Pname);
        modelPatient.setAge(Page);
        modelPatient.setProblem(Pproblem);
        modelPatient.setTime(slot);
        modelPatient.setDate(date);
        modelPatient.setCategory(category);
        modelPatient.setDname(dname);
        modelPatient.setDocid(temp);
        modelPatient.setStime(milliseconds);
    }
}
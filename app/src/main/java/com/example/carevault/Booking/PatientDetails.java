package com.example.carevault.Booking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.carevault.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PatientDetails extends AppCompatActivity {
    EditText name,age,problem;
    Switch switch2;
    Button button;
    Spinner genderSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);
        name=findViewById(R.id.editTextFullName);
        age=findViewById(R.id.age);
        problem=findViewById(R.id.problemgiven);
        button=findViewById(R.id.nextButton1);
        switch2=findViewById(R.id.switch2);
        genderSpinner=findViewById(R.id.genderSpinner);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
        if(switch2.isChecked()){
            solve();
            name.setEnabled(false);
            age.setEnabled(false);
            genderSpinner.setEnabled(false);
        }
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Switch is ON, perform something
                    solve();
                    name.setEnabled(false);
                    age.setEnabled(false);
                    genderSpinner.setEnabled(false);
                } else {
                    name.setText("");
                    age.setText("");
                    genderSpinner.setSelection(0);
                    name.setEnabled(true);
                    age.setEnabled(true);
                    genderSpinner.setEnabled(true);
                    // Switch is OFF
                    // You can optionally perform some action when the switch is turned OFF
                }
            }
        });
    }
    private void save(){
        String title=name.getText().toString();
        String content=age.getText().toString();
        String location1=problem.getText().toString();
        if(title.isEmpty() || title==null){
            name.setError("Please Enter Title Name :)");
            return;
        }
        if(location1.isEmpty() || location1==null){
            problem.setError("Please Enter problem :)");
            return;
        }
        if(content.isEmpty() || content==null){
            age.setError("Please Enter age :)");
            return;
        }
        Intent intent = new Intent(PatientDetails.this, booking.class);
        String date = getIntent().getStringExtra("date");
        String slot = getIntent().getStringExtra("slot");
        String dname=getIntent().getStringExtra("dname");
        String temp=getIntent().getStringExtra("docId");
        String category=getIntent().getStringExtra("category");
        intent.putExtra("date",date);
        intent.putExtra("slot",slot);
        intent.putExtra("Pname",title);
        intent.putExtra("Page",content);
        intent.putExtra("Pproblem",location1);
        intent.putExtra("dname",dname);
        intent.putExtra("category",category);
        intent.putExtra("docId",temp);
        startActivity(intent);
    }
    void solve(){
        FirebaseUser curr = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore.getInstance().collection("Users").document(curr.getUid())
                .collection("Health Information").document("details").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Document exists, retrieve fields
                                String field1 = document.getString("Name");
                                String field2 = document.getString("Date of Birth");
                                String field=document.getString("Gender");
                                if(field.equals("male"))
                                    genderSpinner.setSelection(1);
                                else genderSpinner.setSelection(2);
                                name.setText(field1);
                                age.setText(field2);
                                // Continue retrieving other fields as needed
                                // You can then use the retrieved fields as required
                            } else {
                            }
                        } else {
                        }
                    }
                });
    }
}
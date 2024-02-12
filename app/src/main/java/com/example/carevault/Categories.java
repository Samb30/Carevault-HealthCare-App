package com.example.carevault;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carevault.Adapters.Adapter;
import com.example.carevault.Adapters.ModelCategory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Categories extends AppCompatActivity {
    private GridView mGridView;
    private MyAdapter mAdapter;
    ImageButton back;
    private String[] mNames = {"Dentist", "Heart Disease", "Cancer", "Diabetes","Asthma",
            "Arthritis",
            "Obesity",
            "Stroke",
            "Osteoporosis",
            "Epilepsy"}; // Example names
    private int[] mImages = {R.drawable.tooth, R.drawable.heart, R.drawable.cancer, R.drawable.diabetes,
            R.drawable.asthma, R.drawable.arthritis, R.drawable.obesity, R.drawable.stroke
    ,R.drawable.osteoporosis, R.drawable.epilepsy}; // Example images
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        mGridView = findViewById(R.id.grid_view);
        back=findViewById(R.id.back);
        mAdapter = new MyAdapter(this, mNames, mImages);
        mGridView.setAdapter(mAdapter);
        TextView text=findViewById(R.id.text);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedSeat = mNames[position];
                Toast.makeText(Categories.this, "="+selectedSeat, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Categories.this, DoctorsActivity.class);
                intent.putExtra("category",selectedSeat);
                startActivity(intent);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
//        text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ModelCategory modelCategory=new ModelCategory();
//                for(int i=0;i<mNames.length;i++){
//                    modelCategory.setName("Matt Henry");
//                    modelCategory.setCategory(mNames[i]);
//                    DocumentReference documentReference;
//                    String s=mNames[i];
//                    documentReference=Utility.getDoctorDetails().document();
//                    documentReference.set(modelCategory).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if(task.isSuccessful()){
//                                String documentId = documentReference.getId();
//                                solve(documentId,s);
//                                Toast.makeText(Categories.this, "Note added", Toast.LENGTH_SHORT).show();
//                            }
//                            else{
//                                Toast.makeText(Categories.this, "Failed", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                }
//            }
//        });
    }
    void solve(String docId,String category){
        ModelDoc modelDoc=new ModelDoc();
        modelDoc.setName("Will");
        modelDoc.setDate("2024-2-14");
        modelDoc.setClinic("XYZ");
        modelDoc.setCategory(category);
        HashMap<String, Boolean> timeSlotsMap = new HashMap<>();

        int startHour = 9;
        int endHour = 17;
        for (int hour = startHour; hour <= endHour; hour++) {
            String time = String.format("%02d:00", hour);
            timeSlotsMap.put(time, true);
        }
        modelDoc.setTimes(timeSlotsMap);
        DocumentReference documentReference;
        documentReference= FirebaseFirestore.getInstance().collection("Doctors")
                .document(docId).collection("Date").document();
        documentReference.set(modelDoc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                }
                else{
                    Toast.makeText(Categories.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
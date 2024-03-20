package com.example.carevault.Booking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carevault.Adapters.ModelDoc;
import com.example.carevault.Adapters.MyAdapter;
import com.example.carevault.DoctorsActivity;
import com.example.carevault.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Categories extends AppCompatActivity {
    ImageButton back;
    private GridView mGridView;
    private MyAdapter mAdapter;
    private final String[] mNames = {"General", "Dentist", "Otology", "Heart", "Intestine", "Eye", "Pediatric", "Herbal", "Cancer", "Diabetes", "Asthma", "Ortho"};
    private final int[] mImages = {R.drawable.general, R.drawable.dentist, R.drawable.otology, R.drawable.hearticon, R.drawable.intestine, R.drawable.optho, R.drawable.pediatric, R.drawable.herbal, R.drawable.cancericon, R.drawable.diabetesicon, R.drawable.lungicon, R.drawable.orthoicon}; // Example images

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        mGridView = findViewById(R.id.grid_view);
        back = findViewById(R.id.back);
        mAdapter = new MyAdapter(this, mNames, mImages);
        mGridView.setAdapter(mAdapter);
        TextView text = findViewById(R.id.text);
        mGridView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedSeat = mNames[position];
            Toast.makeText(Categories.this, "=" + selectedSeat, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Categories.this, DoctorsActivity.class);
            intent.putExtra("category", selectedSeat);
            startActivity(intent);

        });
        back.setOnClickListener(view -> onBackPressed());
    }

    void solve(String docId, String category) {
        ModelDoc modelDoc = new ModelDoc();
        modelDoc.setName("Matt Henry");
        modelDoc.setDate("2024-2-25");
        modelDoc.setClinic("Asan Medical Center (AMC)");
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
        documentReference = FirebaseFirestore.getInstance().collection("Doctors").document(docId).collection("Date").document();
        documentReference.set(modelDoc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                } else {
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
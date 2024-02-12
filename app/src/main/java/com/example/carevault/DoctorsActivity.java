package com.example.carevault;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.carevault.Adapters.DoctorAdapter;
import com.example.carevault.Adapters.ModelCategory;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class DoctorsActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    DoctorAdapter noteAdapter;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);
        mRecyclerView=findViewById(R.id.recyclerview);
        String slot = getIntent().getStringExtra("category");
        TextView text=findViewById(R.id.text);
        back=findViewById(R.id.back);
        text.setText(slot);
        setupRecyclerView1(slot);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    void setupRecyclerView1(String slot){
        Query query=Utility.getDoctorDetails().whereEqualTo("category",slot);
        FirestoreRecyclerOptions<ModelCategory> options=new FirestoreRecyclerOptions.Builder<ModelCategory>()
                .setQuery(query,ModelCategory.class).build();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter =new DoctorAdapter(options,this);
        mRecyclerView.setAdapter(noteAdapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        noteAdapter.stopListening();
    }
    @Override
    public void onResume() {
        super.onResume();
        noteAdapter.notifyDataSetChanged();
    }
    @Override
    public void onBackPressed() {
        // Call super.onBackPressed() to execute the default back button behavior
        super.onBackPressed();
    }
}
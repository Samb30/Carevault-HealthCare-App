package com.example.carevault.tab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.carevault.Adapters.UpcomingAdapter;
import com.example.carevault.Adapters.modelPatient;
import com.example.carevault.R;
import com.example.carevault.Utility;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import java.util.Calendar;

public class UpcomingFragment extends Fragment {
    RecyclerView recyclerView;
    ImageButton menu;
    UpcomingAdapter noteAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_upcoming, container, false);
        recyclerView=view.findViewById(R.id.recyclerview);
        setupRecyclerView();
        return view;
    }
    void setupRecyclerView(){
//        int year = 2024;
//        int month = 1;
//        int day = 13;
//        int hour=18;
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(year, month, day, hour, 0, 0);
//        long milliseconds = calendar.getTimeInMillis();
        Calendar calendar1 = Calendar.getInstance();
        long time=calendar1.getTimeInMillis();
        Query query= Utility.getCollectionReferenceForBooking().whereGreaterThan("stime",time);
        FirestoreRecyclerOptions<modelPatient> options=new FirestoreRecyclerOptions.Builder<modelPatient>()
                .setQuery(query, modelPatient.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        noteAdapter =new UpcomingAdapter(options,requireContext());
        recyclerView.setAdapter(noteAdapter);
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
}
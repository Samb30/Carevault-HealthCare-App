package com.example.carevault.tab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.carevault.Adapters.Adapter;
import com.example.carevault.Adapters.Note1;
import com.example.carevault.Adapters.Note2;
import com.example.carevault.Adapters.UpcomingAdapter;
import com.example.carevault.R;
import com.example.carevault.Utility;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

public class UpcomingFragment extends Fragment {
    FloatingActionButton plus;
    RecyclerView recyclerView;
    ImageButton menu;
    UpcomingAdapter noteAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_upcoming, container, false);
        recyclerView=view.findViewById(R.id.recyclerview);
        setupRecyclerView();
        return view;
    }
    void setupRecyclerView(){
        Query query= Utility.getCollectionReferenceForBooking();
        FirestoreRecyclerOptions<Note2> options=new FirestoreRecyclerOptions.Builder<Note2>()
                .setQuery(query,Note2.class).build();
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
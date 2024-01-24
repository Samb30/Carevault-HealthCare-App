package com.example.carevault.fragments;

import android.content.Intent;
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
import com.example.carevault.AppointmentReminders;
import com.example.carevault.R;
import com.example.carevault.Utility;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalenderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalenderFragment extends Fragment {
    FloatingActionButton plus;
    RecyclerView recyclerView;
    ImageButton menu;
    Adapter noteAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_calender, container, false);
        plus=view.findViewById(R.id.notebuton);
        recyclerView=view.findViewById(R.id.recyclerview);
        plus.setOnClickListener((v) -> startActivity(new Intent(getContext(), AppointmentReminders.class)));
//        menu.setOnClickListener((v)->show());
        setupRecyclerView();
        return view;
    }
    void setupRecyclerView(){
        Query query= Utility.getCollectionReferenceForNotes().orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Note1> options=new FirestoreRecyclerOptions.Builder<Note1>()
                .setQuery(query,Note1.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        noteAdapter =new Adapter(options,requireContext());
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
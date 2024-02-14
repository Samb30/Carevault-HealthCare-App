package com.example.carevault.fragments;

import static android.content.ContentValues.TAG;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.airbnb.lottie.LottieAnimationView;
import com.example.carevault.Adapters.Adapter;
import com.example.carevault.Adapters.Note1;
import com.example.carevault.Alarms.AppointmentReminders;
import com.example.carevault.R;
import com.example.carevault.Utility;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class CalenderFragment extends Fragment {
    FloatingActionButton plus;
    RecyclerView recyclerView;
    ImageButton menu;
    Adapter noteAdapter;
    LottieAnimationView lottie;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_calender, container, false);
        plus=view.findViewById(R.id.notebuton);
        lottie=view.findViewById(R.id.lottie);
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
        ArrayList<String> al1=new ArrayList<>();
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                String s2=(String)document.get("title");
                al1.add(s2);
            }
            if(al1!=null && !al1.isEmpty()){
                lottie.setVisibility(View.INVISIBLE);
            }
            else{
                lottie.setVisibility(View.VISIBLE);
                lottie.addAnimatorListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        // Animation started
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        // Animation ended, restart it
                        lottie.playAnimation();
                    }
                    @Override
                    public void onAnimationCancel(Animator animator) {
                        // Animation cancelled
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                        // Animation repeated
                    }
                });
                lottie.playAnimation();
                System.out.println(al1);
            }
        }).addOnFailureListener(e -> {
            Log.w(TAG, "Error getting documents.", e);
        });
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
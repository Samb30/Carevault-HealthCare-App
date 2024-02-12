package com.example.carevault.tab;

import static android.content.ContentValues.TAG;

import android.animation.Animator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.carevault.Adapters.UpcomingAdapter;
import com.example.carevault.Adapters.modelPatient;
import com.example.carevault.R;
import com.example.carevault.Utility;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CompletedFragment extends Fragment {
    RecyclerView recyclerView;
    ImageButton menu;
    UpcomingAdapter noteAdapter;
    LottieAnimationView lottie;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_completed, container, false);
        recyclerView=view.findViewById(R.id.recyclerview);
        lottie=view.findViewById(R.id.lottie);
        setupRecyclerView();
        return view;
    }
    void setupRecyclerView(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        Query query= Utility.getCollectionReferenceForBooking().whereLessThanOrEqualTo("date","2024-2-14");
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            ArrayList<String> al1=new ArrayList<>();
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                String s2=(String)document.get("time");
                Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY); // Get current hour (0-23)
                String temp=currentHour+":00";   // Get current minute (0-59)
                if(currentHour<10)
                    temp="0"+temp;
                s2="17:00";
                temp="05:00";
                if((s2.charAt(0)=='0' && temp.charAt(0)=='1') || !(s2.charAt(0)=='1' && temp.charAt(0)=='0') || s2.compareTo(temp)>0)
                    al1.add(s2);
                System.out.println(al1);
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
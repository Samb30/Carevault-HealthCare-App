package com.example.carevault.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.carevault.MainActivity2;
import com.example.carevault.R;
import com.example.carevault.TopambulanceActivity;
import com.example.carevault.TophospitalActivity;
import com.example.carevault.booking;
import com.example.carevault.doctordetailMainActivity3;

public class HomeFragment extends Fragment {

    Button dctrall;
    ImageButton expanddoc;
    ImageButton notificat;
    ImageButton hosp;
    ImageButton ambulance;
    ImageButton articles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_home, container, false);
        dctrall=view.findViewById(R.id.topdoctors);
        expanddoc=view.findViewById(R.id.Doctor);
        notificat=view.findViewById(R.id.notifyb);
        hosp=view.findViewById(R.id.hospital);
        ambulance=view.findViewById(R.id.ambulance);
        articles=view.findViewById(R.id.article);

        expanddoc.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), doctordetailMainActivity3.class);
            startActivity(intent);
        });



        dctrall.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MainActivity2.class);

            startActivity(intent);



        });
        hosp.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), TophospitalActivity.class);
            startActivity(intent);



        });
        ambulance.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), TopambulanceActivity.class);
            startActivity(intent);



        });
        articles.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), booking.class);

            startActivity(intent);

        });
        return view;
    }
}
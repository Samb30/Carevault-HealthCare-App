package com.example.carevault.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.carevault.Adapters.GridAdapter;
import com.example.carevault.Booking.Categories;
import com.example.carevault.R;
import com.example.carevault.TopambulanceActivity;
import com.example.carevault.TophospitalActivity;
import com.example.carevault.emergency.EmergencyActivity;
import com.example.carevault.emergency.EmergencyActivityCopy;

public class HomeFragment extends Fragment {
    private GridView gridView;
    private String[] itemNames = {"General", "Dentist", "Otology", "Heart", "Intestine", "Eye", "Pediatric", "Herbal"};
    private int[] itemIcons = {R.drawable.general, R.drawable.dentist, R.drawable.otology,R.drawable.hearticon, R.drawable.intestine, R.drawable.optho, R.drawable.pediatric, R.drawable.herbal};
    ImageButton doc, hospitals, ambulance, emergency;
    TextView see1, see2, see3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        doc = view.findViewById(R.id.Doctor);
        hospitals = view.findViewById(R.id.article);
        ambulance = view.findViewById(R.id.hospital);
        emergency = view.findViewById(R.id.ambulance);

        see1 = view.findViewById(R.id.related1);
        see2 = view.findViewById(R.id.relatedarticles1);
        see3 = view.findViewById(R.id.rela1);

        gridView = view.findViewById(R.id.gridView);
        GridAdapter adapter = new GridAdapter(requireContext(), itemNames, itemIcons);
        gridView.setAdapter(adapter);

        doc.setOnClickListener(view1 -> {
            Intent intent = new Intent(requireContext(), Categories.class);
            startActivity(intent);
        });

        hospitals.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), TophospitalActivity.class);
            startActivity(intent);
        });

        ambulance.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), TopambulanceActivity.class);
            startActivity(intent);
        });

        emergency.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), EmergencyActivityCopy.class);
            startActivity(intent);
        });

//        see1.setOnClickListener(v -> {
//            Intent intent = new Intent(getContext(), DoctorsFragment.class);
//            startActivity(intent);
//        });
//
//        see3.setOnClickListener(v -> {
//            Intent intent = new Intent(getContext(), DoctorsFragment.class);
//            startActivity(intent);
//        });

        see2.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), Categories.class);
            startActivity(intent);
        });

        return view;
    }
}
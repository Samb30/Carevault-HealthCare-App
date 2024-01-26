package com.example.carevault.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.carevault.Articles.Health;
import com.example.carevault.MainActivity2;
import com.example.carevault.R;
import com.example.carevault.TopambulanceActivity;
import com.example.carevault.TophospitalActivity;
import com.example.carevault.booking;
import com.example.carevault.doctordetailMainActivity3;

public class HomeFragment extends Fragment {

    Button dctrall,relatedarticles1;
    ImageButton expanddoc;
    ImageButton notificat;
    ImageView advertiseimg;
    ImageButton hosp;
    ImageButton ambulance;
    ImageButton articles;
    LinearLayout sample1,sample2,sample3,sample4,sample5,sample6,sample7,sample8;

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
        sample1=view.findViewById(R.id.sample1);
        sample2=view.findViewById(R.id.sample2);
        sample3=view.findViewById(R.id.sample3);
        sample4=view.findViewById(R.id.sample4);
        advertiseimg=view.findViewById(R.id.advertiseimg);
        relatedarticles1=view.findViewById(R.id.relatedarticles1);
        relatedarticles1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), Health.class);
                startActivity(intent);
            }
        });
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
        sample1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String websiteUrl = "https://www.thehindu.com/news/national/telangana/state-records-five-new-covid-19-cases/article67759822.ece";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(requireContext(), Uri.parse(websiteUrl));
            }
        });
        sample2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String websiteUrl = "https://www.thehindu.com/news/national/telangana/state-records-five-new-covid-19-cases/article67759822.ece";

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(requireContext(), Uri.parse(websiteUrl));
            }
        });
        sample3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String websiteUrl = "https://www.thehindu.com/news/national/telangana/state-records-five-new-covid-19-cases/article67759822.ece";

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(requireContext(), Uri.parse(websiteUrl));
            }
        });
        sample4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String websiteUrl = "https://www.thehindu.com/news/national/telangana/state-records-five-new-covid-19-cases/article67759822.ece";

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();

                customTabsIntent.launchUrl(requireContext(), Uri.parse(websiteUrl));
            }
        });
        advertiseimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String websiteUrl = "https://www.thehindu.com/news/national/telangana/state-records-five-new-covid-19-cases/article67759822.ece";

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();

                customTabsIntent.launchUrl(requireContext(), Uri.parse(websiteUrl));
            }
        });
        return view;
    }
}
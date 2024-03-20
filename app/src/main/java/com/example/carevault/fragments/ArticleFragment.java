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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.carevault.Articles.Covid;
import com.example.carevault.Articles.Diet;
import com.example.carevault.Articles.RelatedArticles;
import com.example.carevault.Articles.Fitness;
import com.example.carevault.Articles.TrendArticle;
import com.example.carevault.R;

public class ArticleFragment extends Fragment {
    Button covid,diet,fitness;
    TextView trend, seeall;
    LinearLayout sample1,sample2,sample3,sample4,sample5,sample6,sample7,sample8;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_article, container, false);
        trend=view.findViewById(R.id.trend);
        covid=view.findViewById(R.id.covid);
        seeall=view.findViewById(R.id.seeall);
        diet=view.findViewById(R.id.diet);
        fitness=view.findViewById(R.id.fitness);
        sample1=view.findViewById(R.id.sample1);
        sample2=view.findViewById(R.id.sample2);
        sample3=view.findViewById(R.id.sample3);
        sample4=view.findViewById(R.id.sample4);
        sample5=view.findViewById(R.id.sample5);
        sample6=view.findViewById(R.id.sample6);
        sample7=view.findViewById(R.id.sample7);
        sample8=view.findViewById(R.id.sample8);
        trend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), TrendArticle.class);
                startActivity(intent);
            }
        });
        covid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), Covid.class);
                startActivity(intent);
            }
        });
        diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), Diet.class);
                startActivity(intent);
            }
        });
        fitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), Fitness.class);
                startActivity(intent);
            }
        });
        seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), RelatedArticles.class);
                startActivity(intent);
            }
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
        sample5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String websiteUrl = "https://www.thehindu.com/news/national/telangana/state-records-five-new-covid-19-cases/article67759822.ece";

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();

                customTabsIntent.launchUrl(requireContext(), Uri.parse(websiteUrl));
            }
        });
        sample6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace "https://example.com" with the actual URL you want to open
                String websiteUrl = "https://www.thehindu.com/news/national/telangana/state-records-five-new-covid-19-cases/article67759822.ece";

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();

                // Ensure you have the necessary checks for null and a browser supporting custom tabs
                customTabsIntent.launchUrl(requireContext(), Uri.parse(websiteUrl));
            }
        });
        sample7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace "https://example.com" with the actual URL you want to open
                String websiteUrl = "https://www.thehindu.com/news/national/telangana/state-records-five-new-covid-19-cases/article67759822.ece";

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();

                // Ensure you have the necessary checks for null and a browser supporting custom tabs
                customTabsIntent.launchUrl(requireContext(), Uri.parse(websiteUrl));
            }
        });
        sample8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace "https://example.com" with the actual URL you want to open
                String websiteUrl = "https://www.thehindu.com/news/national/telangana/state-records-five-new-covid-19-cases/article67759822.ece";

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();

                // Ensure you have the necessary checks for null and a browser supporting custom tabs
                customTabsIntent.launchUrl(requireContext(), Uri.parse(websiteUrl));
            }
        });
        return view;
    }
}
package com.example.carevault.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.carevault.profile.AppFeedbackActivity;
import com.example.carevault.profile.AppSettingsActivity;
import com.example.carevault.profile.EditProfileActivity;
import com.example.carevault.profile.HealthRecordActivity;
import com.example.carevault.profile.HelpSupportActivity;
import com.example.carevault.authentication.LoginActivity;
import com.example.carevault.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserFragment extends Fragment {
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_user, container, false);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        TextView EditButton = view.findViewById(R.id.my_profile13);
        ImageButton forButton1 = view.findViewById(R.id.imageButton2);
        ImageButton forButton2 = view.findViewById(R.id.imageButton3);
        ImageButton forButton3 = view.findViewById(R.id.imageButton4);
        ImageButton forButton4 = view.findViewById(R.id.imageButton5);
        TextView LogoutButton = view.findViewById(R.id.my_pro13);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        FirebaseAuth currentUser = auth;
        if (currentUser != null) {
            String userId = currentUser.getCurrentUser().getUid();
            DocumentReference documentReference=FirebaseFirestore.getInstance().collection("Users").document(userId);
           // FirebaseFirestore userDocument = firestore.collection("Users").document(userId);

            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    String name = documentSnapshot.getString("Name") != null ? documentSnapshot.getString("Name") : "";
                    String email = documentSnapshot.getString("Email") != null ? documentSnapshot.getString("Email") : "";

                    TextView nameTextView = view.findViewById(R.id.my_profile11);
                    TextView emailTextView = view.findViewById(R.id.my_profile12);

                    nameTextView.setText(name);
                    emailTextView.setText(email);
                }
            }).addOnFailureListener(e -> {
                // Handle failure
            });
        }

        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        forButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), HealthRecordActivity.class);
                startActivity(intent);
            }
        });

        forButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), HelpSupportActivity.class);
                startActivity(intent);
            }
        });

        forButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), AppFeedbackActivity.class);
                startActivity(intent);
            }
        });

        forButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), AppSettingsActivity.class);
                startActivity(intent);
            }
        });

        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(requireContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        return view;
    }
}
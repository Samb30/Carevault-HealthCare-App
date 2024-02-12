package com.example.carevault;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Utility {
    public static CollectionReference getCollectionReferenceForBooking(){
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("Users")
                .document(currentUser.getUid()).collection("Appointments");
    }
    public static CollectionReference getCollectionReferenceForNotes() {
        FirebaseUser curr = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("Alarms")
                .document(curr.getUid()).collection("myalarms");
    }
    public static CollectionReference getDoctorDetails(){
        FirebaseUser curr = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("Doctors");
    }
    public static CollectionReference getDateDetails(){
        FirebaseUser curr = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("Doctors")
                .document("7aUnHURTlw6cGoqUM2Ny").collection("Date");
    }
}

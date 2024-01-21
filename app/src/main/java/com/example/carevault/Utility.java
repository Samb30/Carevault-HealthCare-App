package com.example.carevault;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Utility {
    static CollectionReference getCollectionReference(){
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("Users")
                .document(currentUser.getUid()).collection("Appointments");
    }
    static CollectionReference getCollectionReferenceForNotes() {
        FirebaseUser curr = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("notes")
                .document("2").collection("mynotes");
    }
}

package com.example.carevault.Adapters;

import static com.google.firebase.appcheck.internal.util.Logger.TAG;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carevault.R;
import com.example.carevault.Utility;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class UpcomingAdapter extends FirestoreRecyclerAdapter<modelPatient,UpcomingAdapter.NoteViewholder2> {
    Context context;
    public UpcomingAdapter(@NonNull FirestoreRecyclerOptions<modelPatient> options, Context context) {
        super(options);
        this.context= context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewholder2 holder, int position, @NonNull modelPatient note){
        holder.Date.setText(note.date + " |");
        holder.time.setText(note.time);
        holder.docWork.setText(note.category+" |");
        holder.docName.setText(note.dName);
        holder.cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showConfirmationDialog(position,note.date,note.time,note.docId);
                String docId=getSnapshots().getSnapshot(position).getId();
            }
        });
    }

    @NonNull
    @Override
    public NoteViewholder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.bookingrecycler,parent,false);
        return new NoteViewholder2(view);
    }

    class NoteViewholder2 extends RecyclerView.ViewHolder {
        TextView Date,time,docName,docWork;
        Button cancel;
        Switch aSwitch;
        public NoteViewholder2(@NonNull View itemView) {
            super(itemView);
            Date=itemView.findViewById(R.id.DOA);
            time=itemView.findViewById(R.id.time);
            docName=itemView.findViewById(R.id.docName);
            docWork=itemView.findViewById(R.id.docWork);
            cancel=itemView.findViewById(R.id.cancel);
        }
    }
    private void showConfirmationDialog(int position,String s,String time,String docId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirm Cancelation");
        builder.setMessage("Are you sure you want to cancel the booking?");

        // Add the buttons
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked Yes button
                deleteItem(position,s,time,docId);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked No button
                dialog.dismiss();
            }
        });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteItem(int position,String s,String time,String docId2) {
        String docId = getSnapshots().getSnapshot(position).getId();
        Toast.makeText(context, ""+docId, Toast.LENGTH_SHORT).show();
        DocumentReference documentReference;
        documentReference= Utility.getCollectionReferenceForBooking().document(docId);
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    func(time,s,docId2);
                }
                else{
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void updateDocumentField(String docId,String selectedSeat,String temp) {
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Doctors")
                .document(temp).collection("Date").document(docId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ProgressDialog Dialog = new ProgressDialog(context);
                        Dialog.setMessage("please wait a moment..");
                        Dialog.show();
                        Map<String, Object> data = new HashMap<>();
                        Map<String, Boolean> times = (Map<String, Boolean>) document.get("times");
                        if (times != null) {
                            times.put(selectedSeat, true);
                            data.put("times", times);

                            docRef.update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Dialog.dismiss();
                                    } else {
                                        Toast.makeText(context, "Failed to update time", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    } else {
                        Toast.makeText(context, "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Failed to fetch document", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private  void func(String s,String te,String temp){
        FirebaseFirestore.getInstance().collection("Doctors")
                .document(temp).collection("Date")
                .whereEqualTo("date", te) // Replace "field" with your field name and value with the value you're looking for
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String documentId = document.getId();
                                updateDocumentField(documentId,s,temp); // position is the position of grid item clicked
                            }
                        } else {
                            // Handle errors
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}

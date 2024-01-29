package com.example.carevault.Adapters;

import android.app.AlertDialog;
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
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class UpcomingAdapter extends FirestoreRecyclerAdapter<Note2,UpcomingAdapter.NoteViewholder2> {
    Context context;
    public UpcomingAdapter(@NonNull FirestoreRecyclerOptions<Note2> options, Context context) {
        super(options);
        this.context= context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewholder2 holder, int position, @NonNull Note2 note){
        holder.Date.setText(note.DOA);
       // Toast.makeText(context, ""+note.DOA, Toast.LENGTH_SHORT).show();
        holder.time.setText(note.Time);
        holder.cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //showConfirmationDialog(position);
//                String docId=getSnapshots().getSnapshot(position).getId();
//                Toast.makeText(context, "uid"+docId, Toast.LENGTH_SHORT).show();
//                getSnapshots().getSnapshot(position).getReference().delete()
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                // Item deleted successfully
//                                notifyItemRemoved(position);
//                                notifyItemRangeChanged(position, getItemCount());
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                // Handle failure to delete item
//                                Log.e("Adapter", "Error deleting item", e);
//                            }
//                        });
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
        TextView Date,time;
        Button cancel;
        Switch aSwitch;
        public NoteViewholder2(@NonNull View itemView) {
            super(itemView);
            Date=itemView.findViewById(R.id.DOA);
            time=itemView.findViewById(R.id.time);
            cancel=itemView.findViewById(R.id.cancel);
        }
    }
    private void showConfirmationDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirm Cancelation");
        builder.setMessage("Are you sure you want to cancel the booking?");

        // Add the buttons
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked Yes button
                deleteItem(position);
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

    private void deleteItem(int position) {
        String docId = getSnapshots().getSnapshot(position).getId();

        // Delete item from Firestore
        getSnapshots().getSnapshot(position).getReference().delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Item deleted successfully
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, getItemCount());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure to delete item
                        Log.e("Adapter", "Error deleting item", e );
                    }
                });
    }
}

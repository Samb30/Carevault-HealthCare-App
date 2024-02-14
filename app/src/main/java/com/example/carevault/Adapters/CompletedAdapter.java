package com.example.carevault.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carevault.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CompletedAdapter extends FirestoreRecyclerAdapter<modelPatient,CompletedAdapter.NoteViewholder4> {
    Context context;
    public CompletedAdapter(@NonNull FirestoreRecyclerOptions<modelPatient> options, Context context) {
        super(options);
        this.context= context;
    }

    @Override
    protected void onBindViewHolder(@NonNull CompletedAdapter.NoteViewholder4 holder, int position, @NonNull modelPatient note) {
        holder.Date.setText(note.date + " |");
        holder.time.setText(note.time);
        holder.docWork.setText(note.category+" |");
        holder.docName.setText(note.dName);

    }

    @NonNull
    @Override
    public NoteViewholder4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.completedrecycler,parent,false);
        return new NoteViewholder4(view);
    }
    class NoteViewholder4 extends RecyclerView.ViewHolder {
        TextView Date,time,docName,docWork;
        Button cancel;
        Switch aSwitch;
        public NoteViewholder4(@NonNull View itemView) {
            super(itemView);
            Date=itemView.findViewById(R.id.DOA);
            time=itemView.findViewById(R.id.time);
            docName=itemView.findViewById(R.id.docName);
            docWork=itemView.findViewById(R.id.docWork);
            cancel=itemView.findViewById(R.id.cancel);
        }
    }
}

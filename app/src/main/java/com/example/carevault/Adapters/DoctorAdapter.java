package com.example.carevault.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carevault.Booking.DateActivity;
import com.example.carevault.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class DoctorAdapter extends FirestoreRecyclerAdapter<ModelCategory,DoctorAdapter.NoteViewholder3> {
    Context context;
    public DoctorAdapter(@NonNull FirestoreRecyclerOptions<ModelCategory> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull DoctorAdapter.NoteViewholder3 holder, int position, @NonNull ModelCategory model) {
        holder.textViewName.setText(model.category + " |");
        holder.title.setText(model.name);
        holder.location.setText(model.location);
        holder.hospital.setText(model.hospital);
        holder.button.setOnClickListener((v)->
        {
            Intent intent=new Intent(context, DateActivity.class);
            String docId=this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId",docId);
            intent.putExtra("dname",model.name);
            intent.putExtra("category",model.category);
            intent.putExtra("location",model.location);
            intent.putExtra("hospital",model.hospital);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public NoteViewholder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_makeappointment_layout, parent, false);
        return new NoteViewholder3(view);
    }
    class NoteViewholder3 extends RecyclerView.ViewHolder {
        TextView title,location,hospital;
        ImageView imageView;
        Switch aSwitch;
        TextView textViewName;
        LinearLayout button;
        public NoteViewholder3(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            title=itemView.findViewById(R.id.docName);
            location=itemView.findViewById(R.id.location);
            hospital=itemView.findViewById(R.id.hospital);
            button=itemView.findViewById(R.id.button);
        }
    }
}

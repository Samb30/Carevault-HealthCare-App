package com.example.carevault;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class Adapter extends FirestoreRecyclerAdapter<Note,Adapter.NoteViewholder> {
    Context context;
    public Adapter(@NonNull FirestoreRecyclerOptions<Note> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewholder holder, int position, @NonNull Note note) {
        holder.title.setText(note.title);
        holder.content.setText(note.content);
        holder.date.setText(note.date);
        holder.itemView.setOnClickListener((v)->
        {
            Intent intent=new Intent(context, com.example.carevault.MainActivitySar2.class);
            intent.putExtra("title",note.title);
            intent.putExtra("content",note.content);
            intent.putExtra("id",note.id);
            intent.putExtra("checkbox",note.arr);
            intent.putExtra("date",note.date);
            String docId=this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId",docId);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public NoteViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler,parent,false);
        return new NoteViewholder(view);
    }

    class NoteViewholder extends RecyclerView.ViewHolder {
        TextView title,content,date;
        Switch aSwitch;
        public NoteViewholder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.titletext);
            content=itemView.findViewById(R.id.contenttext);
            date=itemView.findViewById(R.id.timeStamp);
            aSwitch = itemView.findViewById(R.id.switch2);
            if(aSwitch!=null){
                aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            // The toggle is enabled
                        } else {
                            // The toggle is disabled
                        }
                    }
                });

            }
        }
    }
}

package com.example.carevault.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.carevault.R;

import java.util.ArrayList;

public class SeatAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> seats;
    public SeatAdapter(Context context, ArrayList<String> seats) {
        this.context = context;
        this.seats = seats;
    }
    @Override
    public int getCount() {
        return seats.size();
    }

    @Override
    public Object getItem(int position) {
        return seats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_layout, parent, false);
        }

        TextView seatTextView = convertView.findViewById(R.id.grid_item);
        seatTextView.setText(seats.get(position));

        return convertView;
    }

}

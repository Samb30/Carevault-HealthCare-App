package com.example.carevault.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carevault.R;

public class GridAdapter extends BaseAdapter {

    private Context context;
    private String[] itemNames;
    private int[] itemIcons;

    public GridAdapter(Context context, String[] itemNames, int[] itemIcons) {
        this.context = context;
        this.itemNames = itemNames;
        this.itemIcons = itemIcons;
    }

    @Override
    public int getCount() {
        return itemNames.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_layout_home, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.textViewName);

        imageView.setImageResource(itemIcons[position]);
        textView.setText(itemNames[position]);

        return convertView;
    }
}

package com.example.carevault;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends BaseAdapter {

    private Context mContext;
    private String[] mNames; // array to store names
    private int[] mImages; // array to store image resources

    public MyAdapter(Context context, String[] names, int[] images) {
        mContext = context;
        mNames = names;
        mImages = images;
    }

    @Override
    public int getCount() {
        return mNames.length;
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
        View gridView;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            gridView = new View(mContext);
            gridView = inflater.inflate(R.layout.grid_item, null);

            ImageView imageView = gridView.findViewById(R.id.imageView);
            TextView textViewName = gridView.findViewById(R.id.textViewName);

            imageView.setImageResource(mImages[position]);
            textViewName.setText(mNames[position]);
        } else {
            gridView = convertView;
        }

        return gridView;
    }
}


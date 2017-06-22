package com.example.albert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;


/*
 * Created by Albert on 5/18/2017
 */

public class TeaAdapter extends BaseAdapter
{
    private Context tContext;
    private LayoutInflater tInflater;
    private List<Tea> tDataSource;

    private FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
    private DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference();

    public TeaAdapter(Context context, List<Tea> items)
    {
        tContext = context;
        tDataSource = items;
        tInflater = (LayoutInflater) tContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount()
    {
        return tDataSource.size();
    }

    public Tea getItem(int position)
    {
        return tDataSource.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public void addTea(Tea tea)
    {
        tDataSource.add(tea);
    }

    public void deleteTea(int position)
    {
        tDataSource.remove(position);
        //mFirebaseDatabase.child("teas").child(userId).child(""+(position+1)).setValue(null);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View rowView = tInflater.inflate(R.layout.list_item_tea, parent, false);
        TextView titleTextView = (TextView) rowView.findViewById(R.id.tea_list_title);
//        TextView detailTextView = (TextView) rowView.findViewById(R.id.tea_list_detail);
        ImageView thumbnailImageView = (ImageView) rowView.findViewById(R.id.tea_list_thumbnail);
        String tea = getItem(position).getName();

        titleTextView.setText(tea);
//        detailTextView.setText(tea);

        Picasso.with(tContext).load(R.drawable.green_tea).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);

        return rowView;
    }
}
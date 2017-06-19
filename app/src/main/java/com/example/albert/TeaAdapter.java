package com.example.albert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View rowView = tInflater.inflate(R.layout.list_item_tea, parent, false);
        TextView titleTextView = (TextView) rowView.findViewById(R.id.tea_list_title);
        TextView detailTextView = (TextView) rowView.findViewById(R.id.tea_list_detail);
        ImageView thumbnailImageView = (ImageView) rowView.findViewById(R.id.tea_list_thumbnail);
        String tea = getItem(position).getName();

        titleTextView.setText(tea);
        detailTextView.setText(tea);

        Picasso.with(tContext).load(R.drawable.green_tea).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);

        return rowView;
    }
}

package com.eventa1.eventatake1;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookedEventsAdapter extends BaseAdapter {
    private List<BookedEvents> events = new ArrayList<>();
    private LayoutInflater layoutInflater=null;
    private Context context;

    public BookedEventsAdapter(List<BookedEvents> events, Context context) {
        this.events = events;
        this.context = context;
        this.events = events;
        this.context = context;
        Log.d("flashchat","CONTECT : " +context.toString());
        //layoutInflater = LayoutInflater.from(context);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d("flashchat","LF : " + layoutInflater.toString());
        Log.d("flashchat","Constructor created");

    }

    @Override
    public int getCount() {
        if(events.size()==0)
            return 1;
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return this.events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public static class ViewHolder1{

        public TextView compName;
        public TextView eveName,date;
        public TextView price;
        public TextView transID;
        public ImageView image;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder1 viewHolder;
        Log.d("flashchat",Integer.toString(events.size()));
        if (view == null) {
            Log.d("flashchat","VIEW IS NULL");
            view = layoutInflater.inflate(R.layout.list_booked_events,
                    null); //Layout of an item of a ListView

        }
        Log.d("flachchatss","BOOKLIST : " + events);
        viewHolder = new ViewHolder1();
        viewHolder.compName = view.findViewById(R.id.bok_compname);
        viewHolder.eveName = view.findViewById(R.id.bok_evename);
        viewHolder.price = view.findViewById(R.id.bok_price);
        viewHolder.transID = view.findViewById(R.id.bok_transid);
        viewHolder.image = view.findViewById(R.id.bok_poster);
        viewHolder.date = view.findViewById(R.id.bok_date);
        if(events.size()<=0){
            Log.d("flashchat","NO DATA");

        }
        else {
            Log.d("flashchat", "FOUND"+viewHolder.compName.getText().toString());
            viewHolder.compName.setText(events.get(position).getCompName());
            viewHolder.eveName.setText(events.get(position).getEveName());
            viewHolder.transID.setText("Transaction ID : " + events.get(position).getTansID());
            viewHolder.price.setText(events.get(position).getPrice());
            Picasso.with(context).load(events.get(position).getImage_url()).into(viewHolder.image);

            viewHolder.date.setText("Date : " + events.get(position).getDate());
        }
        return view;
    }
}

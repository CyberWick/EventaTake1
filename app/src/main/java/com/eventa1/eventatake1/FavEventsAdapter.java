package com.eventa1.eventatake1;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavEventsAdapter extends BaseAdapter {
    private List<Register> events = new ArrayList<>();
    private LayoutInflater layoutInflater=null;
    private Context context;


    public FavEventsAdapter(List<Register> events, Context context) {
        this.events = events;
        this.context = context;
        Log.d("flashchat","CONTECT : " +context.toString());
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
    public static class ViewHolder{

        public TextView text;
        public ImageView image;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        Log.d("flashchatfava",Integer.toString(events.size()));
        if (view == null) {
            Log.d("flashchatfava","VIEW IS NULL");
            view = layoutInflater.inflate(R.layout.list_past_events,
                    null); //Layout of an item of a ListView
        }
        viewHolder = new ViewHolder();
        viewHolder.text = view.findViewById(R.id.eventName);
        viewHolder.image = view.findViewById(R.id.eventImage);
        if(events.size()<=0){
            Log.d("flashchatfava","NO DATA");

        }
        else {
            Log.d("flashchatfava", "FOUND"+viewHolder.text.getText().toString());
            viewHolder.text.setText(events.get(position).getEve());
            Picasso.with(context).load(events.get(position).getImage_url()).into(viewHolder.image);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context,EventDesc.class);
                intent1.putExtra("eventName",events.get(position).getEve());
                Log.d("flashchatfava","Going to event desc with " + events.get(position).getEve() + " postion : " + position);
                context.startActivity(intent1);
            }
        });
        return view;
    }
}

package com.eventa1.eventatake1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PastEvents extends BaseAdapter {
    private List<EventsInfo> events = new ArrayList<>();
    private LayoutInflater layoutInflater=null;
    private Context context;

    public PastEvents(List<EventsInfo> events, Context context) {
        this.events = events;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
//        public TextView text1;
//        public TextView textWide;
        public ImageView image;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("flashchat","getView");
        View view = convertView;

        Log.d("flashchat",Integer.toString(events.size()));
        if (view == null) {

            view = layoutInflater.inflate(R.layout.list_past_events,
                    null); //Layout of an item of a ListView
        }
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.text = view.findViewById(R.id.eventName);
        if(events.size()<=0)
            Log.d("flashchat","NO DATA");
        else {
            Log.d("flashchat", "FOUND"+viewHolder.text.getText().toString());
            viewHolder.text.setText(events.get(position).getTitile());
        }
        return view;
    }
}

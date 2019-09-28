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

import java.util.ArrayList;
import java.util.List;

public class FavEventsAdapter extends BaseAdapter {
    private List<EventsInfo> events = new ArrayList<>();
    private LayoutInflater layoutInflater=null;
    private Context context;


    public FavEventsAdapter(List<EventsInfo> events, Context context) {
        //super(context,R.layout.list_past_events,events);
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
        Log.d("flaschat","SIZE : " + events.size());
        if(events.size()==0)
            return 1;
        return events.size();
    }

    @Override
    public Object getItem(int position) {

        Log.d("flashchat","GETITEM getView");
        return this.events.get(position);
    }

    @Override
    public long getItemId(int position) {

        Log.d("flashchat","POS getView");
        return position;
    }
    public static class ViewHolder{

        public TextView text;
//        public TextView text1;
//        public TextView textWide;
        public ImageView image;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d("flashchat","getView");
        View view = convertView;
        ViewHolder viewHolder;
        Log.d("flashchat",Integer.toString(events.size()));
        if (view == null) {
            Log.d("flashchat","VIEW IS NULL");
            view = layoutInflater.inflate(R.layout.list_past_events,
                    null); //Layout of an item of a ListView
            //viewHolder = new ViewHolder();
        }
//        else {
//            viewHolder = (ViewHolder) view.getTag();
//            Log.d("flashchat","VIEW NOT NULL");
//        }
        viewHolder = new ViewHolder();
        viewHolder.text = view.findViewById(R.id.eventName);
        if(events.size()<=0){
            Log.d("flashchat","NO DATA");

        }
        else {
            Log.d("flashchat", "FOUND"+viewHolder.text.getText().toString());
            viewHolder.text.setText(events.get(position).getTitile());

        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context,EventDesc.class);
                intent1.putExtra("eventName",events.get(position).getTitile());
                //intent1.putExtr;
                Log.d("flashchat","Going to event desc with " + events.get(position).getTitile());
                context.startActivity(intent1);
                //context.finish();
            }
        });
        return view;
    }
}

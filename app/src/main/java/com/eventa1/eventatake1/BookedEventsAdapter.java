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

import java.util.ArrayList;
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
//        Log.d("flaschat","SIZE : " + events.size());
        if(events.size()==0)
            return 1;
        return events.size();
    }

    @Override
    public Object getItem(int position) {

//        Log.d("flashchat","GETITEM getView");
        return this.events.get(position);
    }

    @Override
    public long getItemId(int position) {

//        Log.d("flashchat","POS getView");
        return position;
    }
    public static class ViewHolder1{

        public TextView compName;
        public TextView eveName;
        public TextView price;
        public TextView transID;
        //        public TextView text1;
//        public TextView textWide;
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
            //viewHolder = new ViewHolder();
        }
//        else {
//            viewHolder = (ViewHolder) view.getTag();
//            Log.d("flashchat","VIEW NOT NULL");
//        }
        Log.d("flachchatss","BOOKLIST : " + events);
        viewHolder = new ViewHolder1();
        viewHolder.compName = view.findViewById(R.id.bok_compname);
        viewHolder.eveName = view.findViewById(R.id.bok_evename);
        viewHolder.price = view.findViewById(R.id.bok_price);
        viewHolder.transID = view.findViewById(R.id.bok_transid);
        viewHolder.image = view.findViewById(R.id.bok_poster);
        if(events.size()<=0){
            Log.d("flashchat","NO DATA");

        }
        else {
            Log.d("flashchat", "FOUND"+viewHolder.compName.getText().toString());
            viewHolder.compName.setText(events.get(position).getCompName());
            viewHolder.eveName.setText(events.get(position).getEveName());
            viewHolder.transID.setText(events.get(position).getTansID());
            viewHolder.price.setText(events.get(position).getPrice());
            Picasso.with(context).load(events.get(position).getImage_url()).into(viewHolder.image);

        }
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1 = new Intent(context,EventDesc.class);
//                intent1.putExtra("eventName",events.get(position).getTitile());
//                //intent1.putExtr;
//                Log.d("flashchat","Going to event desc with " + events.get(position).getTitile());
//                context.startActivity(intent1);
//                //context.finish();
//            }
//        });
        return view;
    }
}

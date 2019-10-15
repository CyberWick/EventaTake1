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

public class BookListAdapter extends BaseAdapter {
    private List<BookedEvents2user> events = new ArrayList<>();
    private LayoutInflater layoutInflater=null;
    private Context context;


    public BookListAdapter(List<BookedEvents2user> events, Context context) {
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

        public TextView text,user,pos;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        Log.d("flashchatfava",Integer.toString(events.size()));
        if (view == null) {
            Log.d("flashchatfava","VIEW IS NULL");
            view = layoutInflater.inflate(R.layout.userbook,
                    null); //Layout of an item of a ListView
        }
        viewHolder = new ViewHolder();
        viewHolder.text = view.findViewById(R.id.eve);
        viewHolder.user = view.findViewById(R.id.usr);
        viewHolder.pos = view.findViewById(R.id.posi);
       // viewHolder.image = view.findViewById(R.id.eventImage);
        if(events.size()<=0){
            Log.d("flashchatfava","NO DATA");

        }
        else {
            Log.d("flashchatfava", "FOUND"+viewHolder.text.getText().toString());
            viewHolder.text.setText(events.get(position).getCompName());
            viewHolder.user.setText(events.get(position).getUsernname());
            viewHolder.pos.setText(Integer.toString(position+1));
//            viewHolder.pos.setText(Integer.toString(position+1));
          //  Picasso.with(context).load(events.get(position).getImage_url()).into(viewHolder.image);
        }

        return view;
    }
}

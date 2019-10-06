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

import java.util.ArrayList;
import java.util.List;

public class HostEventsAdapter extends BaseAdapter {
    private List<Register> regList = new ArrayList<>();
    private LayoutInflater layoutInflater=null;
    private Context context;

    public HostEventsAdapter(List<Register> regList, Context context) {
        this.regList = regList;
        this.context = context;
        Log.d("flashchat","CONTECT : " +context.toString());
        //layoutInflater = LayoutInflater.from(context);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d("flashchat","LF : " + layoutInflater.toString());
        Log.d("flashchat","Constructor created");
    }

    @Override
    public int getCount() {
    Log.d("flashchatad","SIZE : " + regList.size());
        if(regList.size()==0)
            return 1;
        return regList.size();
    }

    @Override
    public Object getItem(int position) {

//        Log.d("flashchat","GETITEM getView");
        return this.regList.get(position);
    }

    @Override
    public long getItemId(int position) {

      // Log.d("flashchatad","Pos"+Integer.toString(position));
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
//        Log.d("flashchat","getView");
        View view = convertView;
        ViewHolder viewHolder;
        Log.d("flashchat",Integer.toString(regList.size()));
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
        if(regList.size()<=0){
            Log.d("flashchat","NO DATA");

        }
        else {
            Log.d("flashchat", "FOUND"+viewHolder.text.getText().toString());
            Log.d("flashchatad","RegList : " + regList.get(position));
            //viewHolder.text.setText(regList.get(position).getEve());

        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, EventDesc.class);
                intent1.putExtra("eventName",regList.get(position).getEve());
                //intent1.putExtr;
                Log.d("flashchat","Going to event desc with " + regList.get(position).getEve());
                context.startActivity(intent1);
                //context.finish();
            }
        });
        return view;
    }
}

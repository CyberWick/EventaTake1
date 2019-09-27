package com.eventa1.eventatake1;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eventa1.eventatake1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.eventa1.eventatake1.MainActivity.DATE_OF_BIRTH_KEY;
import static com.eventa1.eventatake1.MainActivity.DISPLAY_NAME_KEY;
import static com.eventa1.eventatake1.MainActivity.PHONE_KEY;

public class viewPagerAdapter extends PagerAdapter {
    private Context context;
    static final String TAG = "flashchat";
    private LayoutInflater layoutInflater;
    private List<EventsInfo> Events = new ArrayList<>();
    private DatabaseReference dbRef;
    public viewPagerAdapter(Context context,List<EventsInfo> Events){
        this.context = context;
        this.Events = Events;
    }
    //    private Integer[] images = {R.drawable.credenz19,R.drawable.mindspark19,R.drawable.cultural1,R.drawable.seminar1,R.drawable.tedx1};
    @Override
    public int getCount() {
        return Events.size();
    }

    public viewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout,null);
        ImageView imageView = view.findViewById(R.id.imageView2);
        //.setImageResource(Events.get(position));
        //getListItems();
        Picasso.with(context).load(Events.get(position).getImage()).into(imageView);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context,EventDesc.class);
                intent1.putExtra("eventName",Events.get(position).getTitile());
                //intent1.putExtr;
                Log.d("flashchat","Going to event desc with " + Events.get(position).getTitile());
                context.startActivity(intent1);
                //context.finish();
            }
        });
        ViewPager vp = (ViewPager) container;
        vp.addView(view,0);
       // container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }

}
package com.eventa1.eventatake1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static com.eventa1.eventatake1.MainActivity.CHAT_PREFS;
import static com.eventa1.eventatake1.MainActivity.FAVEVENTS_LIST;
import static com.eventa1.eventatake1.MainActivity.USER_ID;

public class EventDesc extends AppCompatActivity implements IfFirebaseLoad_comp {
    private ExpandableAdapter expandableAdapter;
    private ExpandableListView expandableListView;
    private ImageView imageView;
    private ImageView likeImage;
    private TextView textName;
    private TextView textLoc;
    private TextView textDesc;
    private IfFirebaseLoad_comp ifFirebaseLoad;
    private Register register_event;
    private List<String> compList = new ArrayList<>();
    private Button back_but;
    private SharedPreferences prefs;
    private String usrID;
    private HashMap<String, Compete> compMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_desc);
        imageView = findViewById(R.id.event_poster);
        textName = findViewById(R.id.event_name);
        textLoc = findViewById(R.id.event_location);
        textDesc = findViewById(R.id.event_desc);
        expandableListView = findViewById(R.id.event_list);
        back_but = findViewById(R.id.back_but_event);
        likeImage = findViewById(R.id.imageLike);

        likeImage.setTag(R.mipmap.ic_favunlike);
        //likeImage.setVisibility(View.INVISIBLE);
        ifFirebaseLoad = this;
        prefs = getSharedPreferences(CHAT_PREFS,MODE_PRIVATE);
        usrID = prefs.getString(USER_ID,null);
        CheckFav();
//        Log.d("flashchat","IN EVENTDESC USRID : " + usrID);
        getData();
        back_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void getData(){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Register");
        dbRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.d("flashchat","INEVENTDESC Searching for EVENTS");
//                    Log.d("flashchat", dataSnapshot.getKey());
                    Bundle bundle = getIntent().getExtras();
                    String eveName = bundle.getString("eventName");
                    Log.d("flashchat","LOOKING FOR : " + eveName);
                    register_event = dataSnapshot.child(eveName).getValue(Register.class);
//                    Log.d("flashchat","Read EVENT NAME : " + register_event.getEve());
                    Picasso.with(EventDesc.this).load(register_event.getImage_url()).into(imageView);
                    textDesc.setText(register_event.getDes());
                    textLoc.setText(register_event.getCol());
                    textName.setText(register_event.getEve());
                    dataSnapshot = dataSnapshot.child(eveName).child("Compete");
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                        Log.d("flashchat", postSnapshot.getKey());
                        Compete temp = dataSnapshot.child(postSnapshot.getKey()).getValue(Compete.class);
                        compList.add(temp.getEvename2());
//                        Log.d("flashchat", "IN BOOKMARKS" + temp.getEvename2());
                        compMap.put(temp.getEvename2(),temp);
                    }
                ifFirebaseLoad.onFirebaseLoadSuccess(compList,compMap);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("flashchat","Something went WRONG");
            }
        });
    }

    @Override
    public void onFirebaseLoadSuccess(List<String> compList, HashMap<String, Compete> hashMap) {
        expandableListView.setAdapter(new ExpandableAdapter(this,compList,hashMap,register_event.getEve()));
    }

    @Override
    public void onFirebaseLoadFail(String message) {
        Log.d("flashchat",message);
    }
    public void AddtoFav(View view){
//        Drawable unlikeimg = getDrawable(R.mipmap.ic_favunlike);
        //switch(view.get)
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Favourites");
        Bundle bundle = getIntent().getExtras();
        String eveName = bundle.getString("eventName");
        //String usrID = prefs.getString(USER_ID,null);
        int tag = (int) likeImage.getTag();
        if(tag == R.mipmap.ic_favunlike){
            Log.d("flashchat","ADDING to FAV");
            likeImage.setImageResource(R.mipmap.ic_favlike);
            likeImage.setTag(R.mipmap.ic_favlike);
            Log.d("flashchat","USR : " + usrID +"  eveName : " +eveName);

            dbRef.child(usrID).child("EventName").child(eveName).setValue(eveName);
            //dbRef.setValue(eveName);
            Set<String> favEvents = new ArraySet<>();
            favEvents = prefs.getStringSet(FAVEVENTS_LIST,null);
            favEvents.add(eveName);
            prefs.edit().putStringSet(FAVEVENTS_LIST,favEvents).apply();
        }
        else {
            Log.d("flashchat","ADDING to UNFAV");
            likeImage.setImageResource(R.mipmap.ic_favunlike);
            likeImage.setTag(R.mipmap.ic_favunlike);
            dbRef.child(usrID).child("EventName").child(eveName).removeValue();
            Set<String> favEvents = new ArraySet<>();
            favEvents = prefs.getStringSet(FAVEVENTS_LIST,null);
            for(String temp : favEvents){
                if(temp.equals(eveName)){
                    favEvents.remove(temp);
                    break;
                }
            }
            Log.d("flashchat","SIZE AFTER REMOVE : " + favEvents.size());
            prefs.edit().putStringSet(FAVEVENTS_LIST,favEvents).apply();

        }
    }



    private Boolean CheckFav(){
        Set<String> favEvents;// = new ArraySet<>();
        favEvents = prefs.getStringSet(FAVEVENTS_LIST,null);
        if(favEvents==null) {
            Log.d("flashchat","NO EVENTS ADDED to FAVS");
            return false;
        } else {
            int flag =0;
            Bundle bundle = getIntent().getExtras();
            final String eveName = bundle.getString("eventName");

            for(String tempEve : favEvents) {
                if(tempEve.equals(eveName)){
                    flag=1;
                    break;
                }

            }
            if(flag==1){
                Log.d("flashchat","was Booked Already");
                likeImage.setImageResource(R.mipmap.ic_favlike);
                likeImage.setTag(R.mipmap.ic_favlike);

                return true;
            }
        }
        Log.d("flashchat","New EVENT");
        return false;
        }
    }
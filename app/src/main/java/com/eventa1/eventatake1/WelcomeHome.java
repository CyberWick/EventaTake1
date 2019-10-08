package com.eventa1.eventatake1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.eventa1.eventatake1.MainActivity.CHAT_PREFS;
import static com.eventa1.eventatake1.MainActivity.DATE_OF_BIRTH_KEY;
import static com.eventa1.eventatake1.MainActivity.DISPLAY_NAME_KEY;
import static com.eventa1.eventatake1.MainActivity.FAVEVENTS_LIST;
import static com.eventa1.eventatake1.MainActivity.PHONE_KEY;
import static com.eventa1.eventatake1.MainActivity.USER_ID;

public class WelcomeHome extends AppCompatActivity implements IfFirebaseLoad {
    private SharedPreferences prefs;
    private DatabaseReference dbRef;
    private TextView mUserName;
    private String usrName;
    private FirebaseAuth mAuth;
    private viewPagerAdapter myAdapter;
    private ViewPager viewPager,viewPager1;
    private IfFirebaseLoad ifFirebaseLoad;
    private final static int ACTIVITY_NUMBER=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_home);
        prefs = getSharedPreferences(CHAT_PREFS, MODE_PRIVATE);
        //VideoView videoView=findViewById(R.id.videoView);
        //videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.credenz1));
        //videoView.setMediaController(new MediaController(this));
//        videoView.requestFocus();
//        videoView.start();
//
//        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        // not playVideo
//                        // playVideo();
//
//                        mp.start();
//                    }
//                });

        mUserName = findViewById(R.id.usrnamehome);
        ifFirebaseLoad = this;
        getListItems();
        viewPager = findViewById(R.id.viewPager);
        viewPager1=findViewById(R.id.viewPager1);
        ImageAdapter adapter=new ImageAdapter(this);
        viewPager1.setAdapter(adapter);

        viewPagerAdapter viewPagerAdapter = new viewPagerAdapter(this);
        viewPager.setPageTransformer(true,new DepthPageTransformer());

        readData();
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.botbar);
        BottomNavHelper.enableNavigation(WelcomeHome.this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUMBER);
        menuItem.setChecked(true);
    }

    private void getListItems() {
        Log.d("flashchat","Searching for EVENTS");
        dbRef = FirebaseDatabase.getInstance().getReference("Events");
        dbRef.addValueEventListener(new ValueEventListener() {
            List<EventsInfo> Events = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("flashchat","Searching for EVENTS");
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Log.d("flashchat", postSnapshot.getKey());
                    EventsInfo temp = dataSnapshot.child(postSnapshot.getKey()).getValue(EventsInfo.class);
                    Events.add(temp);
                    Log.d("flashchat", temp.getTitile());
                    Log.d("flashchat","with IMAGE url  : " + temp.getImage());

                }
                ifFirebaseLoad.onFirebaseLoadSuccess(Events);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("flashchat","Something went WRONG");
            }
        });
    }
    private void readData() {
        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("email");
        final String usrname,phnno;
        if(email.equals("")) {
            usrname = prefs.getString(DISPLAY_NAME_KEY,null);
            phnno = prefs.getString(PHONE_KEY,"");
            Log.d("flashchat","IN SREF Read username as " + usrname);
            Log.d("flashchat","WH USRID : " + prefs.getString(USER_ID,null));
            mUserName.setText("Hi," + usrname);
            usrName = mUserName.getText().toString();
        } else {
            final String usrID = bundle.getString("dbusr");
            Log.d("flashchat","Reading UID : " + usrID);

            dbRef = FirebaseDatabase.getInstance().getReference("users").child(usrID);
            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("flashchat","DETAILS" + dataSnapshot.toString());
                    UserInfo1 usr1 = dataSnapshot.getValue(UserInfo1.class);
                    String usrname = usr1.getUsrname();
                    String phnno = usr1.getPhnno();
                    String dob = usr1.getDob();
                    prefs.edit().putString(DISPLAY_NAME_KEY, usrname).apply();
                    prefs.edit().putString(PHONE_KEY, phnno).apply();
                    prefs.edit().putString(DATE_OF_BIRTH_KEY, dob).apply();
                    prefs.edit().putString(USER_ID, usrID).apply();
                    Log.d("flashchat","IN firebase Read username as " + usrname);
                    mUserName.setText("Hi," + usrname);
                    usrName = mUserName.getText().toString();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("flashchat","Something went WRONG");
                }
            });
        }
    }

    @Override
    public void onFirebaseLoadSuccess(List<EventsInfo> list) {
        myAdapter = new viewPagerAdapter(this,list);
        viewPager.setAdapter(myAdapter);
    }

    @Override
    public void onFirebaseLoadFail(String message) {

    }
    private void GetFavourites() {

        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Favourites");
        final String usrID = prefs.getString(USER_ID,null);
        Log.d("flashchat1","In GET FAVOURITES");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(usrID)) {
                    Log.d("flashchat1", "FAV AlREADY EXISTS");
                    dbRef.child(usrID).child("EventName").addValueEventListener(new ValueEventListener() {
                        Set<String> favEvents = new ArraySet<>();

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            favEvents = new ArraySet<>();
                            Log.d("flashchat1", "BEFORE ADDING " + favEvents.size());
                            for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                                Log.d("flashchat1", "FOUND " + snapshot1.getKey());
                                favEvents.add(snapshot1.getKey());
                            }
                            Log.d("flashchat1", "SIZE OF FAVS IN LOGIN : " + favEvents.size());
                            prefs.edit().putStringSet(FAVEVENTS_LIST, favEvents).apply();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            favEvents = new ArraySet<>();
                            Log.d("flashchat1", "FAV was not PRESENT");
                            prefs.edit().putStringSet(FAVEVENTS_LIST, favEvents).apply();
                        }

                    });
                } else {
                    Set<String> nullSet = new ArraySet<>();
                    prefs.edit().putStringSet(FAVEVENTS_LIST,nullSet).apply();
                    Log.d("flashchatad","NOT IN IF SIZE OF FAVEVELIST : " + nullSet.size());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("flashchat1","FAV was not PRESENT");
                Set<String> favEvents = new ArraySet<>();
                prefs.edit().putStringSet(FAVEVENTS_LIST,favEvents).apply();
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent start = new Intent(Intent.ACTION_MAIN);
        start.addCategory(Intent.CATEGORY_HOME);
        start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(start);
    }
}



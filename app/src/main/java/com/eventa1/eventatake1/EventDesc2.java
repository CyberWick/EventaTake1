package com.eventa1.eventatake1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;

import android.content.Intent;
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

public class EventDesc2 extends AppCompatActivity implements IfFirebaseLoad_comp {
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
    private Button checkBook;
    private SharedPreferences prefs;
    private String usrID;
    private HashMap<String, Compete> compMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_desc2);
        imageView = findViewById(R.id.event_poster);
        textName = findViewById(R.id.event_name);
        textLoc = findViewById(R.id.event_location);
        textDesc = findViewById(R.id.event_desc);
        expandableListView = findViewById(R.id.event_list);
        back_but = findViewById(R.id.back_but_event);
        ifFirebaseLoad = this;
        prefs = getSharedPreferences(CHAT_PREFS,MODE_PRIVATE);
        usrID = prefs.getString(USER_ID,null);

//        Log.d("flashchat","IN EVENTDESC USRID : " + usrID);
        getData();
        checkBook = findViewById(R.id.checkBook);
        checkBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b=new Intent(EventDesc2.this,DispBook.class);
                b.putExtra("EventName",textName.getText().toString());
                startActivity(b);

            }
        });
        back_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void getData(){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Unconfirmed");
        SharedPreferences prefs = getSharedPreferences(CHAT_PREFS,0);
        String UsrID = prefs.getString(USER_ID,null);
        dbRef = dbRef.child(UsrID);
        dbRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.d("flashchat","INEVENTDESC Searching for EVENTS");
//                    Log.d("flashchat", dataSnapshot.getKey());
                Bundle bundle = getIntent().getExtras();
                String eveName = bundle.getString("eventName");
                Log.d("flashchated","LOOKING FOR : " + eveName);
                if (dataSnapshot.hasChild(eveName)) {
                    register_event = dataSnapshot.child(eveName).getValue(Register.class);
//                    Log.d("flashchat","Read EVENT NAME : " + register_event.getEve());
                    Picasso.with(EventDesc2.this).load(register_event.getImage_url()).into(imageView);
                    textDesc.setText(register_event.getDes());
                    textLoc.setText(register_event.getCol());
                    textName.setText(register_event.getEve());
                    dataSnapshot = dataSnapshot.child(eveName).child("Compete");
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                        Log.d("flashchat", postSnapshot.getKey());
                        Compete temp = dataSnapshot.child(postSnapshot.getKey()).getValue(Compete.class);
                        compList.add(temp.getEvename2());
//                        Log.d("flashchat", "IN BOOKMARKS" + temp.getEvename2());
                        compMap.put(temp.getEvename2(), temp);
                    }
                    ifFirebaseLoad.onFirebaseLoadSuccess(compList, compMap,"YES");
                }else {
                    DatabaseReference dbusr = FirebaseDatabase.getInstance().getReference("Unconfirmed").child(usrID);
                    dbusr.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Bundle bundle = getIntent().getExtras();
                            String eveName = bundle.getString("eventName");
                            Log.d("flashchated","LOOKING FOR IN UNCONFIRMED: " + eveName);
                            register_event = dataSnapshot.child(eveName).getValue(Register.class);
//                    Log.d("flashchat","Read EVENT NAME : " + register_event.getEve());
                            Log.d("flashchated","EVENT : " + register_event.getEve());
                            Picasso.with(EventDesc2.this).load(register_event.getImage_url()).into(imageView);
                            textDesc.setText(register_event.getDes());
                            textLoc.setText(register_event.getCol());
                            textName.setText(register_event.getEve());
                            dataSnapshot = dataSnapshot.child(eveName).child("Compete");
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                        Log.d("flashchat", postSnapshot.getKey());
                                Compete temp = dataSnapshot.child(postSnapshot.getKey()).getValue(Compete.class);
                                compList.add(temp.getEvename2());
//                        Log.d("flashchat", "IN BOOKMARKS" + temp.getEvename2());
                                compMap.put(temp.getEvename2(), temp);
                                likeImage.setVisibility(View.INVISIBLE);
                            }
                            ifFirebaseLoad.onFirebaseLoadSuccess(compList, compMap,"NO");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("flashchat","Something went WRONG");
            }
        });
    }

    @Override
    public void onFirebaseLoadSuccess(List<String> compList, HashMap<String, Compete> hashMap,String status) {
        expandableListView.setAdapter(new ExpandableAdapter(this,compList,hashMap,register_event.getEve(),"NO",register_event.getEndDate()));
    }

    @Override
    public void onFirebaseLoadFail(String message) {
        Log.d("flashchat",message);
    }




}
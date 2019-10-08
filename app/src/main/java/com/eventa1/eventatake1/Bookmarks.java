package com.eventa1.eventatake1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Bookmarks extends AppCompatActivity implements HostFirebase {
    private final static int ACTIVITY_NUMBER = 2;
    private List<Register> eveList = new ArrayList<>();
    private ListView listView;
    private HostFirebase ifFirebaseLoad;
//    private String[] events = {"Credenz","Pulzion"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bookmarks);
//        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.botbar);
//        BottomNavHelper.enableNavigation(Bookmarks.this, bottomNavigationViewEx);
//        Menu menu = bottomNavigationViewEx.getMenu();
//        MenuItem menuItem = menu.getItem(ACTIVITY_NUMBER);
//        menuItem.setChecked(true);
        ifFirebaseLoad = this;

        getAllPastEvents();
        //ArrayAdapter<EventsInfo> usrItems= new ArrayAdapter<EventsInfo>(this,android.R.layout.simple_list_item_1,eveList);
//        ArrayAdapter<String> usrItems= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,events);
//        Log.d("flashchat",listView.toString());
//        Log.d("flashchat",eveList.toString());


//        listView.setAdapter(usrItems);
    }
    private void getAllPastEvents(){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Events");
        dbRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("flashchat","Searching for EVENTS");
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Log.d("flashchat", postSnapshot.getKey());
                    Register temp = dataSnapshot.child(postSnapshot.getKey()).getValue(Register.class);
                    eveList.add(temp);
                    Log.d("flashchat", "IN BOOKMARKS" + temp.getEve());
//                    Log.d("flashchat","with IMAGE url  : " + temp.getImage());

                }
                ifFirebaseLoad.onFirebaseLoadSuccess(eveList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("flashchat","Something went WRONG");
            }
        });
    }



    @Override
    public void onFirebaseLoadSuccess(List<Register> list) {
        Log.d("flashchat","SENDING EVELIST " + Integer.toString(eveList.size()));
        listView.setAdapter(new FavEventsAdapter(list,Bookmarks.this));
    }

    @Override
    public void onFirebaseLoadFail(String message) {
        Toast.makeText(Bookmarks.this,message,Toast.LENGTH_SHORT).show();
    }
}

package com.eventa1.eventatake1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.eventa1.eventatake1.MainActivity.CHAT_PREFS;
import static com.eventa1.eventatake1.MainActivity.USER_ID;

public class DispBook extends AppCompatActivity implements Book2userLoad {

    private ListView boklist;
    private Book2userLoad load;
    private TextView nomsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp_book);
        boklist=findViewById(R.id.booklist);
        nomsg = findViewById(R.id.nomsg_d2);
        load=this;

        getList();
    }

    public void getList()
    {
        SharedPreferences preferences=getSharedPreferences(CHAT_PREFS,0);
        final String usrID=preferences.getString(USER_ID,null);
        Bundle bundle=getIntent().getExtras();
        String evename=bundle.getString("EventName");
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Unconfirmed").child(usrID).child(evename).child("Bookings");
        dbRef.addValueEventListener(new ValueEventListener() {
            List<BookedEvents2user> hostList= new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("flashchat", "Searching for EVENTS");


                    //for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                      //  Log.d("flashchatad", "USER"+postSnapshot.getKey());

                        //dataSnapshot = dataSnapshot.child(usrID);//getValue(RegistreEvent.class);

                        for (DataSnapshot postSnapshot1 : dataSnapshot.getChildren()) {
                          BookedEvents2user tempo = dataSnapshot.child(postSnapshot1.getKey()).getValue(BookedEvents2user.class);
                            hostList.add(tempo);

                            Log.d("flashchatad", "event"+postSnapshot1.getKey() + "    " + tempo.getEveName());
                        }
                load.onFirebaseLoadSuccess(hostList);
                    }





            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){
                Log.d("flashchat", "Something went WRONG");
            }


        });
    }
    @Override
    public void onFirebaseLoadSuccess(List<BookedEvents2user> list) {
        Log.d("flashchatad","SIZE OF LIST : " + list.toString());
        if(list.size()<=0){
            boklist.setVisibility(View.INVISIBLE);
            nomsg.setVisibility(View.VISIBLE);
            nomsg.setText("No Bookings done yet!");
        }else{
            boklist.setVisibility(View.VISIBLE);
            boklist.setAdapter(new BookListAdapter(list,this));
            nomsg.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onFirebaseLoadFail(String message) {

    }
}

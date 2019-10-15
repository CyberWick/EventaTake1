package com.eventa1.eventatake1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.eventa1.eventatake1.MainActivity.CHAT_PREFS;
import static com.eventa1.eventatake1.MainActivity.DISPLAY_NAME_KEY;
import static com.eventa1.eventatake1.MainActivity.USER_ID;

public class Receipt extends AppCompatActivity {
    private TextView compName;
    private TextView EventName;
    private TextView Price;
    private ImageView evePoster;
    private Button butOk;
    private String image_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        compName = findViewById(R.id.rec_compname);
        EventName = findViewById(R.id.rec_evename);
        Price = findViewById(R.id.rec_price);
        evePoster = findViewById(R.id.eventpos);

        Bundle bundle = getIntent().getExtras();
        Compete compete = (Compete) bundle.get("CompDetails");
        compName.setText("Competition Name : " + compete.getEvename2());
        final String eveName = (String) bundle.get("EventName");
        EventName.setText("Event : " + eveName);
        Price.setText("Price : " + compete.getPric());
//        Picasso.with(this).load()
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Register");
        dbRef = dbRef.child(eveName);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Register register = dataSnapshot.getValue(Register.class);
                Log.d("flachchatss","LOADING IMAGE");
                image_url = register.getImage_url();
                Log.d("flachchatss","IMAGE IN onDATACHANGE : " + image_url);
                Picasso.with(Receipt.this).load(register.getImage_url()).into(evePoster);
                DatabaseReference dbRef1 = FirebaseDatabase.getInstance().getReference("BookedEvents");
                final SharedPreferences prefs = getSharedPreferences(CHAT_PREFS,MODE_PRIVATE);
                String usrID = prefs.getString(USER_ID,null);
                final String uniqueID = UUID.randomUUID().toString();
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                final BookedEvents[] bookedEvents = {new BookedEvents(compName.getText().toString(), eveName, image_url, Price.getText().toString(), uniqueID,df.format(date))};
                Log.d("flachchatss","IMAGE IN RECEIPT : " + bookedEvents[0].getImage_url());
                DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                dbRef1.child(usrID).child(compName.getText().toString() + UUID.randomUUID().toString().substring(0,6)).setValue(bookedEvents[0]);
                Register register1 = dataSnapshot.getValue(Register.class);
                Log.d("flashchatad","Host : " + register1.getHostedby());

                if(register1.getHostedby()==(null)){
                    Log.d("flashchatad","Host : " + register1.getHostedby());
                }else{
                    DatabaseReference dbBook = FirebaseDatabase.getInstance().getReference("Unconfirmed").child(register1.getHostedby());//.child(eveName);
                    final RegistreEvent[] registreEvent = new RegistreEvent[1];
                    String usrName = prefs.getString(DISPLAY_NAME_KEY,null);
                    Log.d("whta","NAME : " + usrName);
                    BookedEvents2user bookedEventsq = new BookedEvents2user(compName.getText().toString(),eveName,image_url,Price.getText().toString(),uniqueID,usrName);
                    dbBook.child(eveName).child("Bookings").child(usrID+"+" + compName.getText().toString()  + UUID.randomUUID().toString().substring(0,6)).setValue(bookedEventsq);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        butOk = findViewById(R.id.rec_ok);
        butOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}

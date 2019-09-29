package com.eventa1.eventatake1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import static com.eventa1.eventatake1.MainActivity.CHAT_PREFS;
import static com.eventa1.eventatake1.MainActivity.LOG_CHECK_KEY;

public class Profile extends AppCompatActivity {
    private final static int ACTIVITY_NUMBER=3;
    private FirebaseAuth.AuthStateListener authStateListener;
    private TextView mUserName;
    Button signout,about,help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        signout=findViewById(R.id.signout);
        about=findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this,aboutus.class);
                startActivity(intent);
            }
        });
        help=findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this,helpactivity.class);
                startActivity(intent);
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                SharedPreferences prefs = getSharedPreferences(CHAT_PREFS,MODE_PRIVATE);
                prefs.edit().putBoolean(LOG_CHECK_KEY,false).apply();
                Intent intent = new Intent(Profile.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//makesure user cant go back
                startActivity(intent);
            }
        });
        Bundle bundle = getIntent().getExtras();
        String usrName = null;
        if (bundle != null) {
            usrName = bundle.getString("usrname");
        }
        mUserName = findViewById(R.id.textView);
        mUserName.setText("Hi, " + usrName);
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.botbar);
        BottomNavHelper.enableNavigation(Profile.this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUMBER);
        menuItem.setChecked(true);
    }

    public void regevent(View v)
    {
        Intent i = new Intent(Profile.this, regEvent.class);
        startActivity(i);
        finish();
    }
}

package com.eventa1.eventatake1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class Profile extends AppCompatActivity {
    private final static int ACTIVITY_NUMBER=3;
    private TextView mUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Bundle bundle = getIntent().getExtras();
        String usrName = bundle.getString("usrname");
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

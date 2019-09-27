package com.eventa1.eventatake1;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;

import android.content.SharedPreferences;
import android.view.MenuItem;
import android.widget.Toast;

import static com.eventa1.eventatake1.MainActivity.CHAT_PREFS;
import static com.eventa1.eventatake1.MainActivity.DISPLAY_NAME_KEY;

public class BottomNavHelper {


    public static void enableNavigation(final Context context, BottomNavigationView bottomNavigationView){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.id_home:
                        //Toast.makeText(WelcomeHome.this,"Selected Home",Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(context,WelcomeHome.class);
                        intent1.putExtra("email","");
                        context.startActivity(intent1);
                        break;
                    case R.id.id_usr:
                        Intent intent4 = new Intent(context,Profile.class);
                        SharedPreferences prefs = context.getSharedPreferences(CHAT_PREFS,Context.MODE_PRIVATE);
                        String usrName = prefs.getString(DISPLAY_NAME_KEY,null);
                        intent4.putExtra("usrname",usrName);
                        context.startActivity(intent4);
                        //Toast.makeText(WelcomeHome.this,"Selected USER",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.id_favevents:
                        Intent intent3 = new Intent(context,Bookmarks.class);
                        context.startActivity(intent3);
                        //Toast.makeText(WelcomeHome.this,"Selected BOOKMARKS",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.id_search:
                        Intent intent2 = new Intent(context,Search.class);
                        context.startActivity(intent2);
                        //Toast.makeText(WelcomeHome.this,"Selected SEARCH",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;

                }
                return true;
            }
        });
    }
}

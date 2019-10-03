package com.eventa1.eventatake1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

public class YourEvents extends AppCompatActivity implements PastEventsFrag.OnFragmentInteractionListener,FavEventsFrag.OnFragmentInteractionListener,HostedEventsFrag.OnFragmentInteractionListener{
    private final static int ACTIVITY_NUMBER = 2;
    private List<EventsInfo> eveList = new ArrayList<>();
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TabItem pEve;
    private TabItem fEve;
    private TabItem hEVE;
    private YourEventsAdapter mYourEventsAdapter;
    //    private String[] events = {"Credenz","Pulzion"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_events);
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.botbar);
        BottomNavHelper.enableNavigation(YourEvents.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUMBER);
        menuItem.setChecked(true);
        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewpbook);
        pEve = findViewById(R.id.pasteve);
        fEve = findViewById(R.id.faveve);
        hEVE = findViewById(R.id.hosteve);
        mYourEventsAdapter = new YourEventsAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(mYourEventsAdapter);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0){
                    Toast.makeText(YourEvents.this,"IN PAST", Toast.LENGTH_SHORT).show();
                }
                else if(tab.getPosition()==1){
                    Toast.makeText(YourEvents.this,"IN FAV", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(YourEvents.this,"IN HoST", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
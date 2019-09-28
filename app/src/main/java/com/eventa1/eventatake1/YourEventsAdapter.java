package com.eventa1.eventatake1;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class YourEventsAdapter extends FragmentPagerAdapter {
    private int numOfTabs;

    public YourEventsAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override

    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new PastEventsFrag();
            case 1:
                return  new FavEventsFrag();
            case 2:
                return new HostedEventsFrag();
            default:
                return null;
        }
        //return null;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}

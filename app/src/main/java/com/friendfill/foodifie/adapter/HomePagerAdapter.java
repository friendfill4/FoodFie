package com.friendfill.foodifie.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.friendfill.foodifie.fragment.HomePagerItemFragment;

/**
 * Created by FriendFill on 19-Jan-18.
 */

public class HomePagerAdapter extends FragmentStatePagerAdapter {
    int NUM_PAGES = 0;

    public HomePagerAdapter(FragmentManager fm, int pages) {
        super(fm);
        NUM_PAGES = pages;
    }

    @Override
    public Fragment getItem(int position) {
        return HomePagerItemFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}

package com.example.mma7faztyv2;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<tabData> tab_titles = new ArrayList<>();
    private static final String TAG = "SectionsPagerAdapter";
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }
    public void addtab(tabData tab)
    {
        tab_titles.add(tab);
    }
    @Override
    public Fragment getItem(int position) {
       return tab_titles.get(position).getFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Log.i(TAG, String.format("getPageTitle:%s",tab_titles.get(position).getName()));
        return tab_titles.get(position).getName();
    }

    @Override
    public int getCount() {
        return tab_titles.size();
    }
}
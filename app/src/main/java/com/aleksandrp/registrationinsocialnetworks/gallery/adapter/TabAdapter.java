package com.aleksandrp.registrationinsocialnetworks.gallery.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;

import android.support.v13.app.FragmentPagerAdapter;

import com.aleksandrp.registrationinsocialnetworks.gallery.fragment.ImageFragment;

/**
 * Created by AleksandrP on 14.06.2016.
 */
public class TabAdapter extends FragmentPagerAdapter {

    private int numberOfTabs;
    private Context mContext;
    private String[] mUpls;

    public TabAdapter(FragmentManager fm, int numberOfTabs, Context mContext, String[] mUrlPhotos) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        this.mContext = mContext;
        this.mUpls = mUrlPhotos;
    }


    @Override
    public Fragment getItem(int position) {

        if (position == 0 || position < mUpls.length) {
            return new ImageFragment(mUpls[position], mContext);
        } else return null;
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
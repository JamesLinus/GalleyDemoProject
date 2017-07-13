package com.example.meitu.gallerydemoproject.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.meitu.gallerydemoproject.Beans.ImageMessage;

import java.util.List;

/**
 * Created by meitu on 2017/7/13.
 */

public class imageFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    public imageFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragments = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}

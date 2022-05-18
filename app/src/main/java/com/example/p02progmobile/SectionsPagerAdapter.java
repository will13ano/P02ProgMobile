package com.example.p02progmobile;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES =  new int[]{
            R.string.players,
            R.string.teams
    };
    private final Context sectionContext;

    public SectionsPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        sectionContext = context;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment ;

        switch (position) {
            case 0:
                fragment = new FirstFragment();
                break;
            case 1:
                fragment = new SecondFragment();
                break;
            default:
                fragment = null;
                break;
        }

        assert fragment != null;
        return fragment;
    }

    @NonNull
    @Override
    public CharSequence getPageTitle(int position) {
        return sectionContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}

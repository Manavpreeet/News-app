package com.example.newstime.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.newstime.Fragment.GeneralFragment;
import com.example.newstime.Fragment.HealthFragment;
import com.example.newstime.Fragment.ScienceFragment;
import com.example.newstime.Fragment.SportsFragment;

public class FragmentAdapter extends FragmentPagerAdapter {


    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new GeneralFragment();
            case 1:
                return new HealthFragment();
            case 2:
                return new ScienceFragment();
            default:
                return new SportsFragment();

        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "General";
            case 1:
                return "Health";
            case 2:
                return "Science";
            default:
                return "Sports";
        }
    }
}

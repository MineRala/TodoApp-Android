package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new DoneFragment();
            case 2:
                return new ProfileFragment();
            default:
                return new ListFragment();
        }
    }

    @Override
    public int getCount() {
        // List, Done and Profile fragments.
        return 3;
    }
}

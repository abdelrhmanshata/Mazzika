package com.example.mazzika.adapter;

import android.util.Pair;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionPageAdapter extends FragmentStatePagerAdapter {

    private List<Pair<String, Fragment>> pages;

    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
        pages = new ArrayList<>();
    }

    public void addFragment(Fragment fragment, String title) {

        this.pages.add(new Pair<>(title, fragment));
    }

    @Override
    public Fragment getItem(int position) {
        return pages.get(position).second;
    }

    @Override
    public int getCount() {
        return pages.size();
    }
}

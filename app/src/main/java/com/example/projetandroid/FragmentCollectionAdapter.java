package com.example.projetandroid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentCollectionAdapter extends FragmentPagerAdapter {

    public FragmentCollectionAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        SnowtamFragment snowtamFragment = new SnowtamFragment();
        Bundle bundle = new Bundle();
        position = position + 1;
        bundle.putString("message","Hello From Page : " + position);
        snowtamFragment.setArguments(bundle);

        return snowtamFragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}

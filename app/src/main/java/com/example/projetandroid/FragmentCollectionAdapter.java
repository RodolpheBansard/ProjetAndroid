package com.example.projetandroid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Cette classe permet de créer un fragment pour chaque snowtam
 */

public class FragmentCollectionAdapter extends FragmentPagerAdapter {

     private int nPages;
     private Snowtam[] snowtam;

    public FragmentCollectionAdapter(FragmentManager fm, Snowtam[] snowtam) {
        super(fm);
        this.snowtam = snowtam;
        this.nPages = snowtam.length;
    }

    /**
     * Crée le fragment et lui associe le contenu à afficher
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        SnowtamFragment snowtamFragment = new SnowtamFragment();
        Bundle bundle = new Bundle();
        position = position + 1;
        bundle.putString("message",snowtam[position-1].toString());
        snowtamFragment.setArguments(bundle);

        return snowtamFragment;
    }

    @Override
    public int getCount() {
        return nPages;
    }


}

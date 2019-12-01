package com.example.projetandroid;

import androidx.viewpager.widget.ViewPager;

public class OnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
    private int currentPage;

    @Override
    public void onPageSelected(int position) {
        currentPage = position;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}

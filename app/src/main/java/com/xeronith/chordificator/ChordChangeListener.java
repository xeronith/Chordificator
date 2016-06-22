package com.xeronith.chordificator;

import android.support.v4.view.ViewPager;

public class ChordChangeListener implements ViewPager.OnPageChangeListener {
    public ChordsPagerAdapter chordsPagerAdapter;

    public ChordChangeListener(ChordsPagerAdapter chordsPagerAdapter) {
        this.chordsPagerAdapter = chordsPagerAdapter;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Chordificator.setCurrentChord(chordsPagerAdapter.getItem(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

package com.xeronith.chordificator;

import android.support.v4.view.ViewPager;

public class NoteChangeListener implements ViewPager.OnPageChangeListener {
    public NotesPagerAdapter notesPagerAdapter;

    public NoteChangeListener(NotesPagerAdapter notesPagerAdapter) {
        this.notesPagerAdapter = notesPagerAdapter;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Chordificator.setCurrentNote(notesPagerAdapter.getItem(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

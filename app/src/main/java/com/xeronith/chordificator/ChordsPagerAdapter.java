package com.xeronith.chordificator;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChordsPagerAdapter extends PagerAdapter {

    private final Context context;
    private final Chord[] items;
    private final LayoutInflater layoutInflater;

    public ChordsPagerAdapter(Context context) {
        this.context = context;
        this.items = Chordificator.getChordTypes();
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Chordificator.getChordTypes().length;
    }

    public Chord getItem(int position) {
        return items[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        TextView view = (TextView)layoutInflater.inflate(R.layout.template_chord_type, null);
        view.setText(items[position].getName() + items[position].getFormula());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}

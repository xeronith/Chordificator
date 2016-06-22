package com.xeronith.chordificator;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NotesPagerAdapter extends PagerAdapter {

    private Note[] items;
    private final LayoutInflater layoutInflater;

    public NotesPagerAdapter(Context context) {
        this.items = Chordificator.getNotes();
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.length;
    }

    public Note getItem(int position) {
        return items[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.template_root_note, null);
        TextView textView = (TextView)view.findViewById(R.id.textView);
        textView.setText(items[position].getName());
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

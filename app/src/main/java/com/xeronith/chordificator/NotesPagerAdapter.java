package com.xeronith.chordificator;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.xeronith.chordificator.databinding.TemplateRootNoteBinding;

public class NotesPagerAdapter extends PagerAdapter {

    private final Context context;
    private final Note[] items;
    private final LayoutInflater layoutInflater;

    public NotesPagerAdapter(Context context) {
        this.context = context;
        this.items = Chordificator.getNotes();
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.length;
    }

    public Note getItem(int position) {
        return items[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        TemplateRootNoteBinding binding = TemplateRootNoteBinding.inflate(layoutInflater, container, false);
        final TextView textView = binding.textView;
        textView.setText(items[position].getName());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items[position].play(context);
                textView.animate()
                        .setInterpolator(new DecelerateInterpolator())
                        .setDuration(500)
                        .rotationY(360)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                textView.setRotationY(0);
                            }
                        });
            }
        });

        container.addView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}

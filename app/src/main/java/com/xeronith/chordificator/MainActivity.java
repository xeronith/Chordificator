package com.xeronith.chordificator;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Context context;

    private GestureDetectorCompat chordGestureDetector;
    private GestureDetectorCompat scaleGestureDetector;

    private BottomSheetBehavior<LinearLayout> behavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context = this;

        final ViewPager viewPagerNote = (ViewPager) findViewById(R.id.viewPagerNote);
        final ViewPager viewPagerChord = (ViewPager) findViewById(R.id.viewPagerChord);
        final RecyclerView recyclerViewScale = (RecyclerView) findViewById(R.id.recyclerViewScale);
        final RecyclerView recyclerViewChord = (RecyclerView) findViewById(R.id.recyclerViewChord);

        ((TextView) findViewById(R.id.dialogue)).setText(TextUtils.concat(getString(R.string.lorem)));
        behavior = BottomSheetBehavior.from((LinearLayout) findViewById(R.id.bottom_sheet));


        assert viewPagerNote != null;
        assert viewPagerChord != null;
        assert recyclerViewScale != null;
        assert recyclerViewChord != null;

        final NotesPagerAdapter notesPagerAdapter = new NotesPagerAdapter(this);
        final ChordsPagerAdapter chordsPagerAdapter = new ChordsPagerAdapter(this);

        viewPagerNote.setAdapter(notesPagerAdapter);
        viewPagerChord.setAdapter(chordsPagerAdapter);

        viewPagerNote.addOnPageChangeListener(new NoteChangeListener(notesPagerAdapter));
        viewPagerChord.addOnPageChangeListener(new ChordChangeListener(chordsPagerAdapter));

        setupRecyclerViews(recyclerViewScale, recyclerViewChord);

        recyclerViewScale.setAdapter(new NotesRecyclerAdapter(context, Chordificator.getCurrentScale(), R.layout.template_scale_note));
        recyclerViewChord.setAdapter(new NotesRecyclerAdapter(context, Chordificator.getCurrentChord(), R.layout.template_chord_note));

        this.chordGestureDetector = new GestureDetectorCompat(this, new GestureListener(recyclerViewChord));
        this.scaleGestureDetector = new GestureDetectorCompat(this, new GestureListener(recyclerViewScale));

        recyclerViewScale.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleGestureDetector.onTouchEvent(event);
                return false;
            }
        });

        recyclerViewChord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                chordGestureDetector.onTouchEvent(event);
                return false;
            }
        });

        Chordificator.setChordificatorStateListener(new Chordificator.IChordificatorStateListener() {
            @Override
            public void onStateChanged() {
                recyclerViewScale.setAdapter(new NotesRecyclerAdapter(context, Chordificator.getCurrentScale(), R.layout.template_scale_note));
                recyclerViewChord.setAdapter(new NotesRecyclerAdapter(context, Chordificator.getCurrentChord(), R.layout.template_chord_note));
            }
        });
    }

    private void setupRecyclerViews(RecyclerView... recyclerViews) {
        for (RecyclerView recyclerView : recyclerViews) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private final View view;

        public GestureListener(View view) {
            this.view = view;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if (view.getId() == R.id.recyclerViewChord)
                Chordificator.playChord(context);
            else if (view.getId() == R.id.recyclerViewScale)
                Chordificator.playScale(context);

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}

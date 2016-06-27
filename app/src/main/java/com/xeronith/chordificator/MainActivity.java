package com.xeronith.chordificator;

import android.content.Context;
import android.databinding.DataBindingUtil;
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

import com.xeronith.chordificator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private Context context;

    private GestureDetectorCompat chordGestureDetector;
    private GestureDetectorCompat scaleGestureDetector;

    private BottomSheetBehavior<LinearLayout> bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMainViewModel(MainViewModel.get(getString(R.string.app_name)));

        this.context = this;

        final ViewPager viewPagerNote = binding.viewPagerNote;
        final ViewPager viewPagerChord = binding.viewPagerChord;
        final RecyclerView recyclerViewScale = binding.recyclerViewScale;
        final RecyclerView recyclerViewChord = binding.recyclerViewChord;
        final LinearLayout linearLayoutBottomSheet = binding.linearLayoutBottomSheet;
        final TextView textViewDialogue = binding.textViewDialogue;

        textViewDialogue.setText(TextUtils.concat(getString(R.string.lorem)));
        bottomSheetBehavior = BottomSheetBehavior.from(linearLayoutBottomSheet);

        final NotesPagerAdapter notesPagerAdapter = new NotesPagerAdapter(this);
        final ChordsPagerAdapter chordsPagerAdapter = new ChordsPagerAdapter(this);

        viewPagerNote.setAdapter(notesPagerAdapter);
        viewPagerChord.setAdapter(chordsPagerAdapter);

        viewPagerNote.addOnPageChangeListener(new NoteChangeListener(notesPagerAdapter));
        viewPagerChord.addOnPageChangeListener(new ChordChangeListener(chordsPagerAdapter));

        viewPagerNote.setClipToPadding(false);
        viewPagerNote.setPadding(130, 0, 130, 0);
        viewPagerNote.setPageMargin(20);

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

    @Override
    public void onBackPressed() {
        if (bottomSheetBehavior != null && bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED)
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        else
            super.onBackPressed();
    }

    private void setupRecyclerViews(RecyclerView... recyclerViews) {
        for (RecyclerView recyclerView : recyclerViews) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
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

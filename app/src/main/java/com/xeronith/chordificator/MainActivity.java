package com.xeronith.chordificator;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager viewPagerNote = (ViewPager) findViewById(R.id.viewPagerNote);
        final ViewPager viewPagerChord = (ViewPager) findViewById(R.id.viewPagerChord);
        final RecyclerView recyclerViewScale = (RecyclerView) findViewById(R.id.recyclerViewScale);
        final RecyclerView recyclerViewChord = (RecyclerView) findViewById(R.id.recyclerViewChord);

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

        recyclerViewScale.setAdapter(new NotesRecyclerAdapter(Chordificator.getCurrentScale(), R.layout.template_scale_note));
        recyclerViewChord.setAdapter(new NotesRecyclerAdapter(Chordificator.getCurrentChord(), R.layout.template_chord_note));

        Chordificator.setChordificatorStateListener(new Chordificator.IChordificatorStateListener() {
            @Override
            public void onStateChanged() {
                recyclerViewScale.setAdapter(new NotesRecyclerAdapter(Chordificator.getCurrentScale(), R.layout.template_scale_note));
                recyclerViewChord.setAdapter(new NotesRecyclerAdapter(Chordificator.getCurrentChord(), R.layout.template_chord_note));
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
}

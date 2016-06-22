package com.xeronith.chordificator;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        final Context context = this;

        final ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        final RecyclerView recyclerView2 = (RecyclerView)findViewById(R.id.recyclerView2);

        assert viewPager != null;
        assert recyclerView != null;
        assert recyclerView2 != null;

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Chordificator.getNotes().length;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                View view = getLayoutInflater().inflate(R.layout.template_note, null);

                TextView textView = (TextView)view.findViewById(R.id.textView);
                textView.setText(Chordificator.getNotes()[position].getName());

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
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                List<Note> notes = Chordificator.getChord(Chord.Major, Chordificator.getNotes()[position]);
                recyclerView.setAdapter(new ChordsAdapter(notes, R.layout.template_chord));

                List<Note> scale = Chordificator.getScale(Scale.Major, Chordificator.getNotes()[position]);
                recyclerView2.setAdapter(new ChordsAdapter(scale, R.layout.template_scale));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setItemAnimator(new DefaultItemAnimator());

        List<Note> notes = Chordificator.getChord(Chord.Major, Chordificator.getNotes()[0]);
        recyclerView.setAdapter(new ChordsAdapter(notes, R.layout.template_chord));

        List<Note> scale = Chordificator.getScale(Scale.Major, Chordificator.getNotes()[0]);
        recyclerView2.setAdapter(new ChordsAdapter(scale, R.layout.template_scale));

        /*final Button buttonChord = (Button)findViewById(R.id.buttonChord);
        final Button buttonScale = (Button)findViewById(R.id.buttonScale);
        final TextView textView = (TextView)findViewById(R.id.textView);
        final Spinner spinner = (Spinner)findViewById(R.id.spinner);

        assert buttonChord != null;
        assert buttonScale != null;
        assert textView != null;
        assert spinner != null;

        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Chordificator.getNotes()));

        buttonChord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Note> scale = Chordificator.getChord(Chord.Diminished7th, (Note)spinner.getSelectedItem());

                StringBuilder stringBuilder = new StringBuilder();
                for(Note note : scale) {
                    stringBuilder.append(note);
                    stringBuilder.append("\n");
                }

                textView.setText(stringBuilder);
            }
        });

        buttonScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Note> scale = Chordificator.getScale(Scale.Major, (Note)spinner.getSelectedItem());

                StringBuilder stringBuilder = new StringBuilder();
                for(Note note : scale) {
                    stringBuilder.append(note.getName());
                    stringBuilder.append("\n");
                }

                textView.setText(stringBuilder);
            }
        });*/
    }
}

package com.xeronith.chordificator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button buttonChord = (Button)findViewById(R.id.buttonChord);
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
        });
    }
}

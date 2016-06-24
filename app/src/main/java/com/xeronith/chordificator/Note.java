package com.xeronith.chordificator;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import java.util.Arrays;

class Note {

    private String name;
    private String alternativeName;

    public String getName() {
        return name;
    }

    public String getAlternativeName() {
        return alternativeName;
    }

    public Note(String name) {
        this.name = name;
        this.alternativeName = name;
    }

    public Note(String name, String alternativeName) {
        this.name = name;
        this.alternativeName = alternativeName;
    }

    public Note flat() {
        Note[] notes = Chordificator.getNotes();
        int index = Arrays.asList(notes).indexOf(this);

        if (index < 0)
            return null;
        else if (index == 0)
            return notes[notes.length - 1];
        else
            return notes[index - 1];
    }

    public Note sharp() {
        Note[] notes = Chordificator.getNotes();
        int index = Arrays.asList(notes).indexOf(this);

        if (index < 0)
            return null;
        else
            return notes[(index + 1) % notes.length];
    }

    public void play(final Context context) {
        int id = context.getResources().getIdentifier(Note.this.name.replace('#', 's').toLowerCase(), "raw", context.getPackageName());
        final MediaPlayer mediaPlayer = MediaPlayer.create(context, id);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
            }
        });
    }

    @Override
    public String toString() {
        return this.name;
    }
}

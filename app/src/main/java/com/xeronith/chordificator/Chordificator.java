package com.xeronith.chordificator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Chordificator {

    private static final int T = 2, S = 1;

    private static final Note[] notes;
    public static Note[] getNotes() {
        return notes;
    }

    static {
        notes = new Note[]{
                new Note("C"),
                new Note("C#", "Db"),
                new Note("D"),
                new Note("Eb", "D#"),
                new Note("E"),
                new Note("F"),
                new Note("F#", "Gb"),
                new Note("G"),
                new Note("G#", "Ab"),
                new Note("A"),
                new Note("Bb", "A#"),
                new Note("B")
        };
    }

    private static Note currentNote = notes[0];
    private static Chord currentChord = Chord.Major;
    private static Scale currentScale = Scale.Major;

    public static Note getCurrentNote() {
        return currentNote;
    }

    public static void setCurrentNote(Note currentNote) {
        if(Chordificator.currentNote != currentNote) {
            Chordificator.currentNote = currentNote;
            if(Chordificator.chordificatorStateListener != null)
                chordificatorStateListener.onStateChanged();
        }
    }

    public static List<Note> getCurrentChord() {
        return getChord();
    }

    public static void setCurrentChord(Chord currentChord) {
        if(Chordificator.currentChord != currentChord) {
            Chordificator.currentChord = currentChord;
            if(Chordificator.chordificatorStateListener != null)
                chordificatorStateListener.onStateChanged();
        }
    }

    public static List<Note> getCurrentScale() {
        return getScale();
    }

    public static void setCurrentScale(Scale currentScale) {
        if(Chordificator.currentScale != currentScale) {
            Chordificator.currentScale = currentScale;
            if(Chordificator.chordificatorStateListener != null)
                chordificatorStateListener.onStateChanged();
        }
    }

    public static List<Note> getScale() {

        List<Integer> steps = new ArrayList<>();

        switch (currentScale) {
            case Major:
                steps.addAll(Arrays.asList(T, T, S, T, T, T, S));
                break;
            case Minor:
                steps.addAll(Arrays.asList(T, S, T, T, S, T, T));
                break;
        }

        int index = -1;

        for (int i = 0; i < notes.length; i++)
            if (notes[i].getName().equals(currentNote.getName()) || notes[i].getAlternativeName().equals(currentNote.getAlternativeName())) {
                index = i;
                break;
            }

        List<Note> result = new ArrayList<>();

        if (index != -1)
            for (int step : steps) {
                result.add(notes[index % notes.length]);
                index += step;
            }

        return result;
    }
    public static List<Note> getChord() {

        List<Note> scale = getScale();
        List<Note> result = new ArrayList<>();

        switch (currentChord) {
            case Major:
                // 1 3 5
                result.addAll(Arrays.asList(scale.get(0), scale.get(2), scale.get(4)));
                break;
            case Minor:
                // 1 3b 5
                result.addAll(Arrays.asList(scale.get(0), scale.get(2).flat(), scale.get(4)));
                break;
            case Diminished:
                // 1 3b 5b
                result.addAll(Arrays.asList(scale.get(0), scale.get(2).flat(), scale.get(4).flat()));
                break;
            case Augmented:
                // 1 3 5#
                result.addAll(Arrays.asList(scale.get(0), scale.get(2), scale.get(4).sharp()));
                break;
            case Major7th:
                // 1 3 5 7
                result.addAll(Arrays.asList(scale.get(0), scale.get(2), scale.get(4), scale.get(6)));
                break;
            case Minor7th:
                // 1 3b 5 7b
                result.addAll(Arrays.asList(scale.get(0), scale.get(2).flat(), scale.get(4), scale.get(6).flat()));
                break;
            case Dominant7th:
                // 1 3 5 7b
                result.addAll(Arrays.asList(scale.get(0), scale.get(2), scale.get(4), scale.get(6).flat()));
                break;
            case Diminished7th:
                // 1 3b 5b 7bb
                result.addAll(Arrays.asList(scale.get(0), scale.get(2).flat(), scale.get(4).flat(), scale.get(6).flat().flat()));
                break;
        }

        return result;
    }
    public static Chord[] getChordTypes() {
        return Chord.values();
    }

    private static IChordificatorStateListener chordificatorStateListener;
    public static void setChordificatorStateListener(IChordificatorStateListener chordificatorStateListener) {
        Chordificator.chordificatorStateListener = chordificatorStateListener;
    }

    public interface IChordificatorStateListener {
        void onStateChanged();
    }
}

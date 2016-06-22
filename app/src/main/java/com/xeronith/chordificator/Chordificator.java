package com.xeronith.chordificator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Chordificator {

    private static Note[] notes;
    private final static int T = 2, S = 1;

    static {
        notes = new Note[]{
                new Note("C"),
                new Note("C#", "Db"),
                new Note("D"),
                new Note("D#", "Eb"),
                new Note("E"),
                new Note("F"),
                new Note("F#", "Gb"),
                new Note("G"),
                new Note("G#", "Ab"),
                new Note("A"),
                new Note("A#", "Bb"),
                new Note("B")
        };
    }

    public static Note[] getNotes() {
        return notes;
    }

    public static List<Note> getScale(Scale scale, Note note) {

        List<Integer> steps = new ArrayList<>();

        switch (scale) {
            case Major:
                steps.addAll(Arrays.asList(T, T, S, T, T, T, S));
                break;
            case Minor:
                steps.addAll(Arrays.asList(T, S, T, T, S, T, T));
                break;
        }

        int index = -1;

        for (int i = 0; i < notes.length; i++)
            if (notes[i].getName().equals(note.getName()) || notes[i].getAlternativeName().equals(note.getAlternativeName())) {
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

    public static List<Note> getChord(Chord chord, Note note) {

        List<Note> scale = getScale(Scale.Major, note);
        List<Note> result = new ArrayList<>();

        switch (chord) {
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
}

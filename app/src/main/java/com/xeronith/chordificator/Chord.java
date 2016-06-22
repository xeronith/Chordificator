package com.xeronith.chordificator;

public enum Chord {
    Major,
    Minor,
    Diminished,
    Augmented,
    Major7th("Major 7th"),
    Minor7th("Minor 7th"),
    Dominant7th("Dominant 7th"),
    Diminished7th("Diminished 7th");

    private final String name;

    Chord() {
        this.name = toString();
    }

    Chord(String name) {
        this.name = name;
    }

    public String getName() {
        if(this.name != null || !this.name.isEmpty())
            return this.name;
        return super.toString();
    }
}

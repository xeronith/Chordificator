package com.xeronith.chordificator;

public enum Chord {
    Major("Major", "1 3 5"),
    Minor("Minor", "1 3b 5"),
    Diminished("Diminished", "1 3b 5b"),
    Augmented("Augmented", "1 3 5#"),
    Major7th("Major 7th", "1 3 5 7"),
    Minor7th("Minor 7th", "1 3b 5 7b"),
    Dominant7th("Dominant 7th", "1 3 5 7b"),
    Diminished7th("Diminished 7th", "1 3b 5b 7bb");

    private final String name;
    private final String formula;

    Chord(String name, String formula) {
        this.name = name;
        this.formula = formula;
    }

    public String getName() {
        return this.name;
    }

    public String getFormula() {
        return this.formula;
    }
}

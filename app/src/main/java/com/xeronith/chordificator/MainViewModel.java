package com.xeronith.chordificator;

public class MainViewModel {
    private String appTitle;

    private MainViewModel(String appTitle) {
        this.appTitle = appTitle;
    }

    public static MainViewModel get(String appTitle) {
        return new MainViewModel(appTitle);
    }

    public String getAppTitle() {
        return this.appTitle;
    }
}

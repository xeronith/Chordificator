package com.xeronith.chordificator;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.xeronith.chordificator.BR;

public class MainViewModel extends BaseObservable {
    private String appTitle;

    private MainViewModel(String appTitle) {
        this.setAppTitle(appTitle);
    }

    public static MainViewModel get(String appTitle) {
        return new MainViewModel(appTitle);
    }

    @Bindable
    public String getAppTitle() {
        return this.appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
        notifyPropertyChanged(BR.appTitle);
    }
}

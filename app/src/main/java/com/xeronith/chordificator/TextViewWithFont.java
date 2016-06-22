package com.xeronith.chordificator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewWithFont extends TextView {
    public TextViewWithFont(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TextViewWithFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TextViewWithFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    public void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TextViewPlusFont, 0, 0);
        try {
            String fontInAssets = ta.getString(R.styleable.TextViewPlusFont_customFont);
            setTypeface(Typefaces.get(context, "fonts/" + fontInAssets));
        } finally {
             ta.recycle();
        }
    }
}

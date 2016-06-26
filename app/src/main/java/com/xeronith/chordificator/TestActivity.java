package com.xeronith.chordificator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final ImageView imageView = (ImageView) findViewById(R.id.imageView);

        Picasso
                .with(this)
                .load("http://square.github.io/picasso/static/sample.png")
                .placeholder(R.drawable.progress_animation)
                .into(imageView);
    }
}

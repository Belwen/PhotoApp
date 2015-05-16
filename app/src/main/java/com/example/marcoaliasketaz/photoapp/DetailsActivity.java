package com.example.marcoaliasketaz.photoapp;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends ActionBarActivity {

    ImageView imageView;
    TextView titleTextView;
    TextView descriptionTextView;

    int id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        String title = getIntent().getStringExtra("title");
        String imageUri = getIntent().getStringExtra("image");
        String decription = getIntent().getStringExtra("description");

        titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText(title);

        imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageURI(Uri.parse(imageUri));

        descriptionTextView=(TextView) findViewById(R.id.description);
        descriptionTextView.setText(decription);
    }
}

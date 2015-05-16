package com.example.marcoaliasketaz.photoapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;


/**
 * Created by MarcoaliasKetaz on 27/04/2015.
 */
public class GalleryActivity extends ActionBarActivity {

    private GridView gridView;
    private GridViewAdapter gridAdapter;

    private DBHandler db = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, db.getAllPhotos());
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Photo photo = (Photo) parent.getItemAtPosition(position);

                //Create intent
                Intent intent = new Intent(GalleryActivity.this, DetailsActivity.class);
                intent.putExtra("title", photo.get_libelle());
                intent.putExtra("image", photo.get_photouri());
                intent.putExtra("decription",photo.get_commentaires());

                //Start details activity
                startActivity(intent);
            }
        });
    }


}

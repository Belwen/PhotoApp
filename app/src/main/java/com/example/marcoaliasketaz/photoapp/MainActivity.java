package com.example.marcoaliasketaz.photoapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;


public class MainActivity extends ActionBarActivity implements OnMapReadyCallback {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    MapFragment mMapFragment;
    GoogleMap googleMap;
    Uri imgURI;
    DBHandler dbhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbhandler = new DBHandler(getApplicationContext());

        mMapFragment = MapFragment.newInstance();
        getFragmentManager().beginTransaction()
                .add(R.id.main, mMapFragment)
                .commit();
        mMapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_photo) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent,REQUEST_IMAGE_CAPTURE);
            return true;
        }
        if (id == R.id.action_galerie) {
            Intent galleryIntent = new Intent(MainActivity.this, GalleryActivity.class);
            startActivity(galleryIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data){
        super.onActivityResult(reqCode, resCode, data);
        if(resCode==-1 && reqCode ==REQUEST_IMAGE_CAPTURE && data!=null){
            Uri pic_uri = data.getData();
            if(pic_uri!=null){
                imgURI=pic_uri;
            }else{
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imgURI=getImageUri(getApplicationContext(),imageBitmap);
            }

            //création de la photo
            Date _date=new Date();
            Localisation localisation = new Localisation(getApplicationContext());
            Photo photo = new Photo(1,localisation.get_latitude(), localisation.get_longitude(),
                    localisation.get_orientation(),_date,"libelle","commentaires",String.valueOf(imgURI));
            dbhandler.createPhoto(photo);

            //Ajout du marker sur le plan
            Toast.makeText(getApplicationContext(),"photo",Toast.LENGTH_SHORT);
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(photo.get_latitude(),photo.get_longitude()))
                    .title(photo.get_libelle()));
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public void afficherMarker(){
        if(googleMap!=null){
            List<Photo> photos = dbhandler.getAllPhotos();
            for (int i =0; i<photos.size();i++){
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(photos.get(i).get_latitude(),photos.get(i).get_longitude()))
                        .title(photos.get(i).get_libelle()));
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap=map;
        afficherMarker();
    }
}

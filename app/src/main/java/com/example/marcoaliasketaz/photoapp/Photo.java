package com.example.marcoaliasketaz.photoapp;

import android.net.Uri;

import java.util.Date;

/**
 * Created by Baruite on 20/04/2015.
 */
public class Photo {
    private String _libelle, _commentaires,_photouri;
    private Date _date;
    private double _latitude, _longitude, _orientation;
    private int _id;

    public Photo(int id, double latitude, double longitude, double orientation,Date date, String libelle, String commentaires, String photoUri){
        _id=id;
        _latitude=latitude;
        _longitude=longitude;
        _orientation=orientation;
        _date=date;
        _libelle=libelle;
        _commentaires=commentaires;
        _photouri=photoUri;

    }

    public double get_latitude(){return _latitude; }
    public double get_longitude(){return _longitude; }
    public double get_orientation(){return _orientation; }
    public Date get_date(){return _date; }
    public String get_libelle(){return _libelle; }
    public String get_commentaires(){return _commentaires; }
    public String get_photouri(){return _photouri; }
    public int get_id(){return _id; }
}

package com.example.marcoaliasketaz.photoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.google.android.gms.wallet.wobs.CommonWalletObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Baruite on 20/04/2015.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;

    private static final String DATABASE_NAME = "photoManager",
            TABLE_PHOTO = "photo",
            KEY_ID = "id",
            KEY_LATITUDE = "latitude",
            KEY_LONGITUDE = "longitude",
            KEY_ORIENTATION="orientation",
            KEY_DATE ="date",
            KEY_LIBELLE="libelle",
            KEY_COMMENTAIRES="commentaires",
            KEY_PHOTOURI="photouri";

    public DBHandler(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_PHOTO + "(" +
                        KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        KEY_LATITUDE + " DOUBLE," +
                        KEY_LONGITUDE + " DOUBLE," +
                        KEY_ORIENTATION + " DOUBLE," +
                        KEY_DATE + " DATE," +
                        KEY_LIBELLE + " TEXT," +
                        KEY_COMMENTAIRES + " TEXT," +
                        KEY_PHOTOURI + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_PHOTO);
        onCreate(db);
    }

    public void createPhoto(Photo photo){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_LATITUDE,photo.get_latitude());
        values.put(KEY_LONGITUDE,photo.get_longitude());
        values.put(KEY_ORIENTATION,photo.get_orientation());
        values.put(KEY_DATE,photo.get_date().toString());
        values.put(KEY_LIBELLE,photo.get_libelle());
        values.put(KEY_COMMENTAIRES,photo.get_commentaires());
        values.put(KEY_PHOTOURI,photo.get_photouri());
        db.insert(TABLE_PHOTO, null, values);
        db.close();
    }

    public List<Photo> getAllPhotos(){
        List<Photo> photos = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PHOTO, null);

        if (cursor.moveToFirst()) do {
            Photo photo = new Photo(cursor.getInt(0),cursor.getDouble(1),cursor.getDouble(2),cursor.getDouble(3), toDate(cursor.getString(4)),
                    cursor.getString(5),cursor.getString(6),cursor.getString(7));
            photos.add(photo);
        } while (cursor.moveToNext());

        return photos;
    }

    public Photo getPhoto(int id){

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PHOTO + " WHERE id=" + String.valueOf(id), null);
        Photo photo;
        if (cursor.moveToFirst()){
             photo = new Photo(cursor.getInt(0),cursor.getDouble(1),cursor.getDouble(2),cursor.getDouble(3), toDate(cursor.getString(4)),
                    cursor.getString(5),cursor.getString(6),cursor.getString(7));
        }
        else{
            photo=null;
        }

        return photo;
    }

    public void deletePhoto(Photo photo){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PHOTO, KEY_ID+"=?", new String[]{String.valueOf(photo.get_id())});
        db.close();
    }

    public Date toDate(String date){
        Date _date;
        DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            _date = iso8601Format.parse(date);
            return _date;
        }catch (Exception e){
            return new Date();
        }
    }
}
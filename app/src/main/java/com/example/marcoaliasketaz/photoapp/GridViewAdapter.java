package com.example.marcoaliasketaz.photoapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends ArrayAdapter<Photo> {

    private Context context;
    private int layoutResourceId;
    private List<Photo> data;

    public GridViewAdapter(Context context, int layoutResourceId, List<Photo> _data) {
        super(context, layoutResourceId, _data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = _data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.text);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Photo photo = data.get(position);
        holder.imageTitle.setText(photo.get_libelle());
        if (photo.get_photouri()!= null)
            holder.image.setImageURI((Uri.parse(photo.get_photouri())));

        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }

    private String getPath(Uri uri) {
        String[]  data = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(context, uri, data, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
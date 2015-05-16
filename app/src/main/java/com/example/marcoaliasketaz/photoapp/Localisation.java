package com.example.marcoaliasketaz.photoapp;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Baruite on 21/04/2015.
 */
public class Localisation implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public Localisation(Context context){
        buildGoogleApiClient(context);
    }

    //localisation
    GoogleApiClient mGoogleApiClient;
    android.location.Location mLastLocation;

    protected synchronized void buildGoogleApiClient(Context context) {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public double get_latitude(){
        if(mLastLocation!=null)
        return mLastLocation.getLatitude();
        else
            return 0;
    }
    public double get_longitude(){

        if(mLastLocation!=null)
            return mLastLocation.getLongitude();
        else
            return 0;
    }
    public double get_orientation(){
        if(mLastLocation!=null)
            return mLastLocation.getBearing();
        else
            return 0;
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}

package com.jacobconner.mynewcontactlist;

import android.location.Location;
import android.location.LocationListener;

public class GPSListener implements LocationListener {
    private double longitude;
    private double latitude;

    @Override
    public void onLocationChanged(Location loc)
    {
        longitude = loc.getLongitude();
        latitude = loc.getLatitude();
    }

    public double getLongitude(){return longitude;};
    public double getLatitude(){return latitude;};
}

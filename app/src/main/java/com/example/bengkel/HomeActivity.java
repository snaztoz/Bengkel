package com.example.bengkel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class HomeActivity extends AppCompatActivity {

    public static final int LOCATION_PERMISSION_CODE = 0;
    public static final String BENGKEL_TYPE = "com.example.bengkel.BENGKEL_TYPE";
    public static final String LOCATION_LAT = "com.example.bengkel.LOCATION_LAT";
    public static final String LOCATION_LONG = "com.example.bengkel.LOCATION_LONG";

    private FusedLocationProviderClient locationClient;
    private int clickedBengkelType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // menyembunyikan title bar
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException exc) {
        }

        locationClient = LocationServices.getFusedLocationProviderClient(this);

        setContentView(R.layout.activity_home);
    }

    public void handleBengkelClicked(View view) {
        clickedBengkelType = view.getId();

        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_CODE);
        } else {
            startBengkelListActivity();
        }
    }

    @SuppressLint("MissingPermission")
    public void startBengkelListActivity() {
        Intent intent = new Intent(this, BengkelListActivity.class);

        locationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location == null) {
                            return;
                        }

                        if (clickedBengkelType == R.id.buttonMotorcycleBengkelList) {
                            intent.putExtra(BENGKEL_TYPE, "motorcycle");
                        } else if (clickedBengkelType == R.id.buttonCarBengkelList) {
                            intent.putExtra(BENGKEL_TYPE, "car");
                        }

                        intent.putExtra(LOCATION_LAT, location.getLatitude());
                        intent.putExtra(LOCATION_LONG, location.getLongitude());

                        startActivity(intent);
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                            @NonNull String[] permissions,
                                            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_CODE
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            requestLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestLocation() {
        AppCompatActivity activity = this;

        LocationRequest req = LocationRequest.create()
                .setInterval(60000)
                .setFastestInterval(5000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationCallback removeCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
            }
        };

        LocationCallback requestCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult == null) {
                    return;
                }

                LocationServices.getFusedLocationProviderClient(activity)
                        .removeLocationUpdates(removeCallback);

                startBengkelListActivity();
            }
        };

        LocationServices.getFusedLocationProviderClient(this)
                .requestLocationUpdates(req, requestCallback, null);
    }
}
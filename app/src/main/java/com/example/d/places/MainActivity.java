package com.example.d.places;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    protected GoogleApiClient mGoogleApiClient;
    Location myLocation;
    protected LocationRequest mLocRequest;
    public static Location mCurLocation;
    private final long LOC_UPDATE_INTERVAL = 10000;
    private final long LOC_FASTEST_UPDATE = 5000;
    private String lat = "";
    private String lon = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.myTabLayout);
        viewPager = (ViewPager) findViewById(R.id.myViewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FragmentSearch(), "Search");
        adapter.AddFragment(new FragmentFavourites(), "Favourites");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocRequest = new LocationRequest();
        mLocRequest.setInterval(LOC_UPDATE_INTERVAL);
        mLocRequest.setFastestInterval(LOC_FASTEST_UPDATE);
        mLocRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    }

    private Location getLocation() {
        //return null;
        try {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            return location;
        } catch (SecurityException e) {
            return null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {

        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Toast.makeText(this,"Hello",Toast.LENGTH_LONG).show();
        Log.i("Play Services", "onConnected: Play Services onConnected called");
        int permCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            myLocation = getLocation();
            initializeUI();

        }

    }

    private void initializeUI() {
        if (mCurLocation == null) {
            mCurLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            //Toast.makeText(this,""+mCurLocation.getLatitude(),Toast.LENGTH_LONG).show();
        } else {
            lon = String.format("Lat: %f\n", mCurLocation.getLongitude());
            lat = String.format("Lon: %f\n", mCurLocation.getLatitude());
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocRequest, this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                myLocation = getLocation();


            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("Play Services", "onConnected: Play Services onConnected called" + i);
        mGoogleApiClient.connect();


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("Play Services", "onConnected: Play Services onConnected called" + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurLocation = location;


        //updateUI();
    }
}

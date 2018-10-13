package com.example.d.places;


import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFrag extends Fragment implements OnMapReadyCallback, RoutingListener {
    private GoogleMap myMap;
    public static View view = null;
    SupportMapFragment mapFragment;
    private Spinner mySpinner;
    String placeName = "";
    private static JSONObject jsonObject;
    private static Geocoder geocoder;
    Double destLat = 0.0, destLon = 0.0, originLat = 0.0, originLon = 0.0;
    LatLng originLatLng, destLatLng;
    private Polyline line = null;

    public MapFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            view = inflater.inflate(R.layout.fragment_map, container, false);

            return view;
        } catch (Exception e) {
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mySpinner = (Spinner) view.findViewById(R.id.mapSpin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.drivingMode, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }


    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        myMap = googleMap;
        String s = InfoPhoto.res;

        try {
            jsonObject = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            placeName = jsonObject.getJSONObject("result").getString("formatted_address");
            //Toast.makeText(getContext(),placeName,Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        geocoder = new Geocoder(getContext());
        if (geocoder.isPresent()) {
            try {
                List<Address> myList = (List<Address>) geocoder.getFromLocationName(placeName, 1);
                //Toast.makeText(getContext(),""+myList.get(0),Toast.LENGTH_LONG).show();

                if (!myList.isEmpty()) {

                    Address addr = myList.get(0);


                    destLat = addr.getLatitude();
                    destLon = addr.getLongitude();
                    LatLng sydney = new LatLng(destLat, destLon);
                    googleMap.addMarker(new MarkerOptions().position(sydney)
                            .title("A"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    CameraUpdate cam = CameraUpdateFactory.newLatLngZoom(sydney, 18);
                    googleMap.animateCamera(cam);
                } else {
                    Toast.makeText(getContext(), "Android GeoCoding Internal Error. Please Try Again ", Toast.LENGTH_LONG).show();
                    return;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment2);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getAddress());

                if (geocoder.isPresent()) {
                    try {
/                       // Instantiate the RequestQueue.
                        RequestQueue queue = Volley.newRequestQueue(getContext());
                        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + place.getAddress() + "&key=AIzaSyDUhnp_nBVyJapC2UQJxr8GELzuu86tx90";

                        // Request a string response from the provided URL.
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        // Toast.makeText(getContext(),""+response, Toast.LENGTH_LONG).show();
                                        try {

                                            JSONObject jsonObject = new JSONObject(response);
                                            String sourceLat = jsonObject.getJSONArray("results").getJSONObject(0).
                                                    getJSONObject("geometry").getJSONObject("location").getString("lat");
                                            Log.d("LAT", sourceLat);
                                            String sourceLon = jsonObject.getJSONArray("results").getJSONObject(0).
                                                    getJSONObject("geometry").getJSONObject("location").getString("lng");
                                            double myLat = Double.parseDouble(sourceLat);
                                            double myLon = Double.parseDouble(sourceLon);

                                            originLatLng = new LatLng(myLat, myLon);
                                            destLatLng = new LatLng(destLat, destLon);
                                            Toast.makeText(getContext(), destLat + " " + destLon, Toast.LENGTH_LONG).show();

                                            myMap.addMarker(new MarkerOptions().position(originLatLng)
                                                    .title("A"));
                                            myMap.moveCamera(CameraUpdateFactory.newLatLng(originLatLng));
                                            CameraUpdate cam = CameraUpdateFactory.newLatLngZoom(originLatLng, 10);
                                            myMap.animateCamera(cam);

                                            myMap.addMarker(new MarkerOptions().position(destLatLng)
                                                    .title("B"));
                                            myMap.moveCamera(CameraUpdateFactory.newLatLng(destLatLng));
                                            cam = CameraUpdateFactory.newLatLngZoom(destLatLng, 10);
                                            myMap.animateCamera(cam);


                                            Routing routing = new Routing.Builder()
                                                    .travelMode(Routing.TravelMode.DRIVING)
                                                    .withListener(MapFrag.this)
                                                    .waypoints(originLatLng, destLatLng)
                                                    .build();
                                            routing.execute();


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

// Add the request to the RequestQueue.
                        queue.add(stringRequest);
                        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (mySpinner.getSelectedItem().toString().equals("DRIVING")) {
                                    Routing routing = new Routing.Builder()
                                            .travelMode(Routing.TravelMode.DRIVING)
                                            .withListener(MapFrag.this)
                                            .waypoints(originLatLng, destLatLng)
                                            .build();
                                    routing.execute();

                                }
                                if (mySpinner.getSelectedItem().toString().equals("WALKING")) {
                                    Routing routing = new Routing.Builder()
                                            .travelMode(Routing.TravelMode.WALKING)
                                            .withListener(MapFrag.this)
                                            .waypoints(originLatLng, destLatLng)
                                            .build();
                                    routing.execute();

                                }
                                if (mySpinner.getSelectedItem().toString().equals("BICYCLING")) {
                                    Routing routing = new Routing.Builder()
                                            .travelMode(Routing.TravelMode.BIKING)
                                            .withListener(MapFrag.this)
                                            .waypoints(originLatLng, destLatLng)
                                            .build();
                                    routing.execute();

                                }
                                if (mySpinner.getSelectedItem().toString().equals("TRANSIT")) {
                                    Routing routing = new Routing.Builder()
                                            .travelMode(Routing.TravelMode.TRANSIT)
                                            .withListener(MapFrag.this)
                                            .waypoints(originLatLng, destLatLng)
                                            .build();
                                    routing.execute();

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                    } catch (Exception e) {

                    }

                }
                //searchPlace.setText(""+place.getName());

            }

            private String getRquestUrl(LatLng originLatLng, LatLng destLatLng) {
                String org = "origin=" + originLatLng.latitude + "," + originLatLng.longitude;
                String dest = "origin=" + destLatLng.latitude + "," + destLatLng.longitude;
                String sensor = "sensor=false";
                String mode = "mode=driving";
                String param = org + "&" + dest + "&" + sensor + "&" + mode;
                String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + org + "&destination=" + dest + "&avoid=highways&mode=bicycling&key=AIzaSyDUhnp_nBVyJapC2UQJxr8GELzuu86tx90";
                return url;
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> arrayList, int i) {
        List<LatLng> listPoints = arrayList.get(0).getPoints();
        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        Iterator<LatLng> iterator = listPoints.iterator();
        while (iterator.hasNext()) {
            LatLng data = iterator.next();
            options.add(data);
        }

        //If line not null then remove old polyline routing.
        if (line != null) {
            line.remove();

        }
        line = myMap.addPolyline(options);

        //Show distance and duration.


        //Focus on map bounds
        myMap.moveCamera(CameraUpdateFactory.newLatLng(originLatLng));
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(originLatLng);
        builder.include(destLatLng);
        LatLngBounds bounds = builder.build();
        myMap.animateCamera(CameraUpdateFactory.newLatLng(originLatLng));
    }


    @Override
    public void onRoutingCancelled() {

    }
}


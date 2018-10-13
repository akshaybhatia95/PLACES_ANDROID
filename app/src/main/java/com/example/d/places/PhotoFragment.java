package com.example.d.places;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoFragment extends Fragment {
    public static PlacePhotoMetadataBuffer photoMetadataBuffer;
    public static GeoDataClient mGeoDataClient;
    private JSONObject jsonObject;
    private RecyclerView recyclerView2;
    ArrayList<Bitmap> bitmap;
    PhotoAdapter adapter;

    // Request photos and metadata for the specified place.
    private void getPhotos() {
        try {
            jsonObject = new JSONObject(InfoPhoto.res);

        } catch (Exception e) {

        }

        String placeId = "";
        try {
            placeId = jsonObject.getJSONObject("result").getString("place_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient.getPlacePhotos(placeId);
        photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                // Get the list of photos.
                PlacePhotoMetadataResponse photos = task.getResult();
                // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
                photoMetadataBuffer = photos.getPhotoMetadata();

            }
        });
    }

    public PhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mGeoDataClient = Places.getGeoDataClient(getContext(), null);
        bitmap = new ArrayList<>();

        getPhotos();
        // Toast.makeText(getContext(),""+bitmap.size(),Toast.LENGTH_SHORT).show();

        return inflater.inflate(R.layout.fragment_photo, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PhotoAdapter(bitmap, getContext());
        recyclerView2.setAdapter(adapter);


    }
}

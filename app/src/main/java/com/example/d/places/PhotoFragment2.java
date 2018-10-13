package com.example.d.places;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PhotoFragment2 extends Fragment {

    View view = null;
    ImageView[] imageViews;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView imageView10;


    private GeoDataClient mGeoDataClient;
    private JSONObject jsonObject;
    private RecyclerView recyclerView2;
    ArrayList<Bitmap> bitmap;

    public PhotoFragment2() {
    }

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
                PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                for (int i = 0; i < photoMetadataBuffer.getCount(); i++) {
                    PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(i);
                    // Get the attribution text.
                    CharSequence attribution = photoMetadata.getAttributions();
                    // Get a full-size bitmap for the photo.
                    Task<PlacePhotoResponse> photoResponse = mGeoDataClient.getPhoto(photoMetadata);
                    photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                            PlacePhotoResponse photo = task.getResult();
                            bitmap.add(photo.getBitmap());

                            if (bitmap.size() == 1) {
                                if (bitmap.get(0) != null) {
                                    imageViews[0].setImageBitmap(bitmap.get(0));
                                }
                            }

                            if (bitmap.size() == 2) {
                                if (bitmap.get(1) != null) {
                                    imageViews[1].setImageBitmap(bitmap.get(1));
                                }
                            }
                            if (bitmap.size() == 3) {
                                if (bitmap.get(2) != null) {
                                    imageViews[2].setImageBitmap(bitmap.get(2));
                                }
                            }
                            if (bitmap.size() == 4) {
                                if (bitmap.get(3) != null) {
                                    imageViews[3].setImageBitmap(bitmap.get(3));
                                }
                            }


                            if (bitmap.size() == 5) {
                                if (bitmap.get(4) != null) {
                                    imageViews[4].setImageBitmap(bitmap.get(4));
                                }
                            }
                            if (bitmap.size() == 6) {
                                if (bitmap.get(5) != null) {
                                    imageViews[5].setImageBitmap(bitmap.get(5));
                                }
                            }
                            if (bitmap.size() == 7) {
                                if (bitmap.get(6) != null) {
                                    imageViews[6].setImageBitmap(bitmap.get(6));
                                }
                            }
                            if (bitmap.size() == 8) {
                                if (bitmap.get(7) != null) {
                                    imageViews[7].setImageBitmap(bitmap.get(7));
                                }
                            }
                            if (bitmap.size() == 9) {
                                if (bitmap.get(8) != null) {
                                    imageViews[8].setImageBitmap(bitmap.get(8));
                                }
                            }
                            if (bitmap.size() == 10) {
                                if (bitmap.get(9) != null) {
                                    imageViews[9].setImageBitmap(bitmap.get(9));
                                }
                            }


                            // Toast.makeText(getContext(),""+bitmap.isEmpty(),Toast.LENGTH_SHORT).show();
                            // Toast.makeText(getContext(),""+bitmap.size(),Toast.LENGTH_SHORT).show();

                        }


                    });
                }
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGeoDataClient = Places.getGeoDataClient(getContext(), null);
        bitmap = new ArrayList<>();

        view = inflater.inflate(R.layout.fragment_photo_fragment2, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        imageViews = new ImageView[10];
        imageView1 = (ImageView) view.findViewById(R.id.ImageView1);
        imageView2 = (ImageView) view.findViewById(R.id.ImageView2);
        imageView3 = (ImageView) view.findViewById(R.id.ImageView3);
        imageView4 = (ImageView) view.findViewById(R.id.ImageView4);
        imageView5 = (ImageView) view.findViewById(R.id.ImageView5);
        imageView6 = (ImageView) view.findViewById(R.id.ImageView6);
        imageView7 = (ImageView) view.findViewById(R.id.ImageView7);
        imageView8 = (ImageView) view.findViewById(R.id.ImageView8);
        imageView9 = (ImageView) view.findViewById(R.id.ImageView9);
        imageView10 = (ImageView) view.findViewById(R.id.ImageView10);


        imageViews[0] = imageView1;
        imageViews[1] = imageView2;
        imageViews[2] = imageView3;
        imageViews[3] = imageView4;
        imageViews[4] = imageView5;
        imageViews[5] = imageView6;
        imageViews[6] = imageView7;
        imageViews[7] = imageView8;
        imageViews[8] = imageView9;
        imageViews[9] = imageView10;


        super.onViewCreated(view, savedInstanceState);
        getPhotos();

    }
}

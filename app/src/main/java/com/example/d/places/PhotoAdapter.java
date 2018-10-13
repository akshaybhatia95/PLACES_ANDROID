package com.example.d.places;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import static com.example.d.places.PhotoFragment.mGeoDataClient;
import static com.example.d.places.PhotoFragment.photoMetadataBuffer;

/**
 * Created by D on 4/20/2018.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private ImageView imageView;
    private ArrayList<Bitmap> bitList;
    private Context context;
    private Bitmap bitmap;

    public PhotoAdapter(ArrayList<Bitmap> bitList, Context context) {
        this.bitList = bitList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_row, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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
                    bitList.add(photo.getBitmap());
                    // Toast.makeText(getContext(),""+bitmap.size(),Toast.LENGTH_SHORT).show();

                }
            });
        }

        bitmap = bitList.get(position);
        holder.bind(bitmap);

    }

    @Override
    public int getItemCount() {
        return bitList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.placeImage);

        }

        public void bind(Bitmap item) {
            imageView.setImageBitmap(item);
        }
    }
}

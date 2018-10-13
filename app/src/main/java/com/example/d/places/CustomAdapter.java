package com.example.d.places;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by D on 4/18/2018.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    ArrayList<MyPlaces> placeItems;
    Context context;

    public interface OnItemClickListener {
        void onItemClick(MyPlaces item);
    }

    private final OnItemClickListener listener;

    public CustomAdapter(ArrayList<MyPlaces> placeItems, Context context, OnItemClickListener listener) {
        this.placeItems = placeItems;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.place_row_item, parent, false);
        //RecyclerView.ViewHolder viewHolder=new ViewHolder(myView);
        return new ViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.bind(placeItems.get(position), listener);

    }

    @Override
    public int getItemCount() {
        return placeItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iconView;
        public TextView nameView;
        //   public ImageView favView;
        public TextView vicView;

        public ViewHolder(View itemView) {
            super(itemView);
            iconView = (ImageView) itemView.findViewById(R.id.icon);
            //  favView=(ImageView)itemView.findViewById(R.id.favImage);
            nameView = (TextView) itemView.findViewById(R.id.placeName);
            vicView = (TextView) itemView.findViewById(R.id.vicinityName);

        }

        public void bind(final MyPlaces item, final OnItemClickListener listener) {

            nameView.setText(item.getName());
            vicView.setText(item.getVicinity());

            Picasso.with(context).load(item.getIcon()).into(iconView);
            // Picasso.with(context).load(item.getIcon()).into(favView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });


        }
    }
}

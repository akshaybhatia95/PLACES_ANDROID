package com.example.d.places;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by D on 4/18/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    ArrayList<Author> authorItems;
    Context context;
    public ImageView authorPic;
    public TextView authorName;
    public RatingBar authorRate;
    public TextView authorTime;
    public TextView authorText;


    public ReviewAdapter(ArrayList<Author> authorItems, Context context) {
        this.authorItems = authorItems;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_row, parent, false);
        //RecyclerView.ViewHolder viewHolder=new ViewHolder(myView);
        return new ViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.bind(authorItems.get(position));

    }

    @Override
    public int getItemCount() {
        return authorItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            authorPic = (ImageView) itemView.findViewById(R.id.authorProfile);
            authorName = (TextView) itemView.findViewById(R.id.authorName);
            authorRate = (RatingBar) itemView.findViewById(R.id.authorRating);
            authorTime = (TextView) itemView.findViewById(R.id.authorTime);
            authorText = (TextView) itemView.findViewById(R.id.authorText);

        }

        public void bind(Author item) {

            authorName.setText(item.getName());
            authorText.setText(item.getText());
            authorTime.setText(item.getTime());
            authorRate.setRating(Float.parseFloat(item.getRating()));


            Picasso.with(context).load(item.getProfilePhoto()).into(authorPic);


        }
    }
}

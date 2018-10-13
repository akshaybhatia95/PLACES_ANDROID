package com.example.d.places;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFrag extends Fragment {
    public static String res = "";
    public static ArrayList<Author> myAuthors;
    public static JSONObject jsonObject;
    String placeName = "", placeCity = "", placeState = "", placeCountry = "", placeAddress = "";

    public ReviewFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        res = InfoPhoto.res;
        myAuthors = new ArrayList<>();
        try {
            jsonObject = new JSONObject(res);
            JSONArray reviews = jsonObject.getJSONObject("result").getJSONArray("reviews");
            if (reviews.length() == 0) {
                Toast.makeText(getContext(), "No Reviews", Toast.LENGTH_LONG).show();
                return inflater.inflate(R.layout.fragment_review, container, false);
            }
            for (int i = 0; i < reviews.length(); i++) {

                String authorName = reviews.getJSONObject(i).getString("author_name");
                String authorURL = reviews.getJSONObject(i).getString("author_url");
                String profileURL = reviews.getJSONObject(i).getString("profile_photo_url");
                String rating = reviews.getJSONObject(i).getString("rating");
                String text = reviews.getJSONObject(i).getString("text");
                String time = reviews.getJSONObject(i).getString("time");
                Author author = new Author(authorName, authorURL, profileURL, rating, text, time);
                myAuthors.add(author);
                //Toast.makeText(getContext(),time,Toast.LENGTH_SHORT).show();


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return inflater.inflate(R.layout.fragment_review, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView3);
        final ReviewAdapter adapter = new ReviewAdapter(myAuthors, getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        final Spinner spinner = (Spinner) view.findViewById(R.id.typeOfReview);
        final Spinner spinner1 = (Spinner) view.findViewById(R.id.typeOfSort);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.reviewtype, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> arrayAdapter1 = ArrayAdapter.createFromResource(getContext(), R.array.sorting, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner1.setAdapter(arrayAdapter1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner.getSelectedItem().toString().equals("Google Reviews")) {
                }
                if (spinner.getSelectedItem().toString().equals("Yelp Reviews")) {
                    Toast.makeText(getContext(), "No Yelp Reviews", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                String sortingType = spinner1.getSelectedItem().toString();
                //Toast.makeText(getContext(),sortingType,Toast.LENGTH_SHORT).show();
                if (sortingType.equals("Default")) {
                    Collections.sort(myAuthors, new SortByDefault());
                    adapter.notifyDataSetChanged();

                } else if (sortingType.equals("Lowest Rating")) {
                    Collections.sort(myAuthors, new SortByLowestRating());
                    adapter.notifyDataSetChanged();


                } else if (sortingType.equals("Highest Rating")) {
                    Collections.sort(myAuthors, new SortByHighestRating());
                    adapter.notifyDataSetChanged();


                } else if (sortingType.equals("Most Recent")) {
                    Collections.sort(myAuthors, new SortByLeastRecent());
                    adapter.notifyDataSetChanged();


                } else if (sortingType.equals("Least Recent")) {
                    Collections.sort(myAuthors, new SortByMostRecent());
                    adapter.notifyDataSetChanged();


                }
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}

package com.example.d.places;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchTable extends AppCompatActivity implements CustomAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private JSONArray resultArray;
    private JSONObject obj;
    ArrayList<MyPlaces> placeDetailArrayList;
    ArrayList<ArrayList<MyPlaces>> placeDetailAlAl;
    ArrayList<JSONObject> myJsonData;

    CustomAdapter.OnItemClickListener listener;
    private Button prevButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_search_table);
        setTitle("Search Results");
        placeDetailArrayList = new ArrayList<>();
        String s = getIntent().getStringExtra("hello");
        //Toast.makeText(this,s,Toast.LENGTH_LONG).show();
        try {
            obj = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            resultArray = obj.getJSONArray("results");
            if (resultArray.length() == 0) {
                Toast.makeText(this, "NO RESULTS", Toast.LENGTH_LONG).show();
                return;
            }
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject currJSONObj = resultArray.getJSONObject(i);
                MyPlaces currPlace = new MyPlaces();
                currPlace.setIcon(currJSONObj.getString("icon"));
                currPlace.setName(currJSONObj.getString("name"));
                currPlace.setVicinity(currJSONObj.getString("vicinity"));
                currPlace.setPlace_id(currJSONObj.getString("place_id"));
                String lat = currJSONObj.getJSONObject("geometry").getJSONObject("location").getString("lat");
                String lon = currJSONObj.getJSONObject("geometry").getJSONObject("location").getString("lng");
                currPlace.setLat(lat);
                currPlace.setLon(lon);
                placeDetailArrayList.add(currPlace);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        placeDetailAlAl = new ArrayList<>();
        placeDetailAlAl.add(placeDetailArrayList);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this) {
        });
        adapter = new CustomAdapter(placeDetailArrayList, this, new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MyPlaces item) {
                ProgressDialog pd = new ProgressDialog(SearchTable.this);
                pd.setMessage("loading");
                pd.show();
                //Toast.makeText(getApplicationContext(),"FUCKERS",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), InfoPhoto.class);
                intent.putExtra("item", item);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        nextButton = (Button) findViewById(R.id.nextButton);
        prevButton = (Button) findViewById(R.id.previousButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = "";
                try {
                    token = obj.getString("next_page_token");
                    // Toast.makeText(getApplicationContext(),""+token,Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String url = "http://places3.us-east-2.elasticbeanstalk.com/sendData2?pagetoken=" + token;
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

//                ProgressDialog pd = new ProgressDialog(getApplicationContext());
//                pd.setMessage("loading");
//                pd.show();

// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                //Toast.makeText(getApplicationContext(),"Response is: " + response,Toast.LENGTH_LONG).show();
                                newPage(response);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "That didn't work!", Toast.LENGTH_LONG).show();
                    }
                });

// Add the request to the RequestQueue.
                queue.add(stringRequest);

            }
        });
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (placeDetailAlAl.size() == 1) {
                    Toast.makeText(getApplicationContext(), "This is the first Page", Toast.LENGTH_LONG).show();
                    return;
                }
                //placeDetailAlAl.remove(placeDetailAlAl.size()-1);
                placeDetailArrayList.clear();
                placeDetailArrayList.addAll(placeDetailAlAl.get(0));
                adapter.notifyDataSetChanged();


            }
        });

    }

    private void newPage(String response) {

        placeDetailArrayList.clear();
        try {
            obj = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            resultArray = obj.getJSONArray("results");
            if (resultArray.isNull(0)) {
                Toast.makeText(getApplicationContext(), "No More Results", Toast.LENGTH_SHORT).show();
                return;
            }
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject currJSONObj = resultArray.getJSONObject(i);
                MyPlaces currPlace = new MyPlaces();
                currPlace.setIcon(currJSONObj.getString("icon"));
                currPlace.setName(currJSONObj.getString("name"));
                currPlace.setVicinity(currJSONObj.getString("vicinity"));
                currPlace.setPlace_id(currJSONObj.getString("place_id"));
                String lat = currJSONObj.getJSONObject("geometry").getJSONObject("location").getString("lat");
                String lon = currJSONObj.getJSONObject("geometry").getJSONObject("location").getString("lng");
                currPlace.setLat(lat);
                currPlace.setLon(lon);
                placeDetailArrayList.add(currPlace);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        placeDetailAlAl.add(placeDetailArrayList);
        adapter.notifyDataSetChanged();


    }

    @Override
    public void onItemClick(MyPlaces item) {

    }
}

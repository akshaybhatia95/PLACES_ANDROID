package com.example.d.places;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class InfoPhoto extends AppCompatActivity {
    private TabLayout infoTabLayout;
    private ViewPager infoViewPager;
    public static String res = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipmr);
        Intent i = getIntent();
        MyPlaces item = (MyPlaces) i.getSerializableExtra("item");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://places2.us-east-2.elasticbeanstalk.com/" +
                "newData?id=" + item.getPlace_id() + "&key=AIzaSyDUhnp_nBVyJapC2UQJxr8GELzuu86tx90";
        setTitle(item.getName());

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        res = response;

                        infoTabLayout = (TabLayout) findViewById(R.id.infoPhotoTab);

                        infoViewPager = (ViewPager) findViewById(R.id.infoPhotoViewPager);
                        InfoViewPagerAdapter adapter = new InfoViewPagerAdapter(getSupportFragmentManager());
                        adapter.AddFragment(new InfoTab(), "Info");
                        adapter.AddFragment(new PhotoFragment2(), "Photos");
                        adapter.AddFragment(new MapFrag(), "Maps");
                        adapter.AddFragment(new ReviewFrag(), "Reviews");


                        infoViewPager.setAdapter(adapter);
                        infoTabLayout.setupWithViewPager(infoViewPager);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // mTextView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);


    }
}

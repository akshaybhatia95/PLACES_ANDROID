package com.example.d.places;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by D on 4/16/2018.
 */

public class FragmentSearch extends Fragment implements View.OnClickListener {
    final String TAG = "FragmentSearch";
    EditText searchPlace;
    View myView;
    protected GoogleApiClient mGoogleApiClient;
    protected GeoDataClient geoDataClient;
    private Spinner mySpinner;
    private AutoCompleteTextView locationEditText;
    private static final LatLngBounds myBounds = new LatLngBounds(new LatLng(-0, 0), new LatLng(0, 0));
    private EditText keywordEditText;
    private EditText distanceEditText;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Button searchButton;
    private String lat = "", lon = "";
    private String myJSON = "";
    public static String location = "0";
    // private MyAdapter myAdapter;

    public FragmentSearch() {
        // super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.searchform_fragment, container, false);
        return myView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Location location = MainActivity.mCurLocation;

    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {

        mySpinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.category, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);
        searchPlace = (EditText) view.findViewById(R.id.location);
        keywordEditText = (EditText) view.findViewById(R.id.keyword);
        distanceEditText = (EditText) view.findViewById(R.id.distance);
        locationEditText = (AutoCompleteTextView) view.findViewById(R.id.location);


        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case (R.id.radioHere):
                        searchPlace.setEnabled(false);

                        break;
                    case (R.id.radioCustom):
                        searchPlace.setEnabled(true);
                        break;
                    default:
                        break;
                }
            }
        });
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
                searchPlace.setText("" + place.getName());

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        searchButton = (Button) myView.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);


    }

    public void searchScreen() {
        Intent i = new Intent();
        i.setClass(getContext(), FragmentSearch.class);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {

        if (v == myView.findViewById(R.id.searchButton)) {
            lat = Double.toString(MainActivity.mCurLocation.getLatitude());
            lon = Double.toString(MainActivity.mCurLocation.getLongitude());
            lat = "" + 34.0224;
            lon = "" + (-118.2851);
            String keyword = keywordEditText.getText().toString();
            if (keyword.equals("")) {
                Toast.makeText(getContext(), "Keyword Field is Mandatory", Toast.LENGTH_LONG).show();
                return;

            }
            String distance = distanceEditText.getText().toString();
            if (distance.equals("")) {
                distance = "" + 16090;
            } else {
                int dis = Integer.parseInt(distance);
                dis = 1609 * dis;
                if (dis > 50000) {
                    dis = 50000;
                }
                distance = "" + dis;
            }
            RadioButton radioHere = (RadioButton) myView.findViewById(R.id.radioHere);
            RadioButton radioThere = (RadioButton) myView.findViewById(R.id.radioCustom);
            String category = mySpinner.getSelectedItem().toString();


            if (radioThere.isChecked() == true) {
                location = locationEditText.getText().toString();
                if (location.equals("")) {
                    Toast.makeText(getContext(), "Location Field is Mandatory", Toast.LENGTH_LONG).show();
                    return;
                }
            } else {
                location = "" + 0;
            }


            RequestQueue queue = Volley.newRequestQueue(getContext());
            String url = "http://places3.us-east-2.elasticbeanstalk.com/sendData?keyword=" + keyword + "&distance=" + distance + "&category=" +
                    category + "&location=" + location + "&lat=" + lat + "&lon=" + lon;
            ProgressDialog pd = new ProgressDialog(getContext());
            pd.setMessage("loading");
            pd.show();

// Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            //Toast.makeText(getContext(),"Response is: " + response.substring(0, 500),Toast.LENGTH_LONG).show();
                            myJSON = response;
                            // Toast.makeText(getContext(),myJSON,Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getContext(), SearchTable.class);
                            intent.putExtra("hello", myJSON.toString());
                            intent.putExtra("location", location);
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "That didn't work!", Toast.LENGTH_LONG).show();
                }
            });

// Add the request to the RequestQueue.
            queue.add(stringRequest);


        }
    }
}

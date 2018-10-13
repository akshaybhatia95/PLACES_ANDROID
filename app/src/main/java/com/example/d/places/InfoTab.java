package com.example.d.places;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class InfoTab extends Fragment {
    private TextView address;
    private TextView phoneNo;
    private TextView price;
    private RatingBar rating;
    private TextView gPage;
    private TextView website;
    private JSONObject jsonObject;

    public InfoTab() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_info_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Toast.makeText(getContext(),InfoPhoto.res,Toast.LENGTH_LONG).show();
        String s = InfoPhoto.res;
        try {
            jsonObject = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        address = (TextView) view.findViewById(R.id.address);
        JSONObject result = new JSONObject();
        try {
            result = jsonObject.getJSONObject("result");
            //Toast.makeText(getContext(),""+result,Toast.LENGTH_LONG).show();
            if (result.getString("formatted_address") != null) {
                address.setText(result.getString("formatted_address"));

            } else {
                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.addressTitle);
                ((ViewGroup) linearLayout.getParent()).removeView(linearLayout);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        phoneNo = (TextView) view.findViewById(R.id.phNumber);

        try {

            if (result.getString("international_phone_number") != null) {
                phoneNo.setText(result.getString("international_phone_number"));
                phoneNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        // callIntent.setData(Uri.parse(phoneNo.getText().toString()));
                        startActivity(callIntent);
                    }
                });

            } else {
                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.phNoTitle);
                ((ViewGroup) linearLayout.getParent()).removeView(linearLayout);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        price = (TextView) view.findViewById(R.id.price);

        try {

            if (result.getString("price_level") != null) {
                int i = Integer.parseInt(result.getString("price_level"));
                String dollar = "";
                while (i != 0) {
                    dollar = dollar + "$";
                    i--;
                }
                price.setText(dollar);

            } else {
                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.pricetitle);
                ((ViewGroup) linearLayout.getParent()).removeView(linearLayout);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        rating = (RatingBar) view.findViewById(R.id.rating);

        try {

            if (result.getString("rating") != null) {
                rating.setRating(Float.parseFloat(result.getString("rating")));


            } else {
                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ratingTitle);
                ((ViewGroup) linearLayout.getParent()).removeView(linearLayout);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        gPage = (TextView) view.findViewById(R.id.gPage);

        try {

            if (result.getString("url") != null) {
                gPage.setText(result.getString("url"));

            } else {
                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.gPageTitle);
                ((ViewGroup) linearLayout.getParent()).removeView(linearLayout);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        website = (TextView) view.findViewById(R.id.website);

        try {

            if (result.getString("website") != null) {
                website.setText(result.getString("website"));

                website.setMovementMethod(LinkMovementMethod.getInstance());


            } else {
                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.websiteTitle);
                ((ViewGroup) linearLayout.getParent()).removeView(linearLayout);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

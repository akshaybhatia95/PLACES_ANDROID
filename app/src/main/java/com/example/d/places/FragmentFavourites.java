package com.example.d.places;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by D on 4/16/2018.
 */

public class FragmentFavourites extends Fragment {
    View myView;
    public FragmentFavourites() {
       // super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        myView=inflater.inflate(R.layout.favourites_fragment,container,false);
        return myView;
    }
}

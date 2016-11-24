package com.example.pcts.bustracker.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pcts.bustracker.R;

/**
 * Created by pcts on 11/23/2016.
 */

public class MainFragment extends Fragment {

    public MainFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        // Inflate the layout for this fragment
        return rootView;
    }


}

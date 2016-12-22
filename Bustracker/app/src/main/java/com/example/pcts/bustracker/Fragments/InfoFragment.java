package com.example.pcts.bustracker.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.pcts.bustracker.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by pcts on 11/23/2016.
 */

public class InfoFragment extends Fragment {

    private final String PARAGENS = "Paragens";
    private final String CARREIRAS = "Carreiras";

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_information, container, false);

        Toolbar toolbar =  (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Infomações");

        TabHost host = (TabHost) rootView.findViewById(R.id.tabInfo);
        host.setup();

        //Tab Carreiras
        TabHost.TabSpec spec = host.newTabSpec(CARREIRAS);
        spec.setContent(R.id.carreiras);
        spec.setIndicator(CARREIRAS);
        host.addTab(spec);

        //Tab Paragens
        spec = host.newTabSpec(PARAGENS);
        spec.setContent(R.id.paragens);
        spec.setIndicator(PARAGENS);
        host.addTab(spec);

        return rootView;

    }

}

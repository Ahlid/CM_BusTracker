package com.example.pcts.bustracker.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pcts.bustracker.Managers.GestorFavoritos;
import com.example.pcts.bustracker.Managers.GestorInformacao;
import com.example.pcts.bustracker.R;

/**
 * Created by pcts on 11/23/2016.
 */

public class FavoritesFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        ((Toolbar) getActivity().findViewById(R.id.toolbar)).setTitle("Favoritos");



        Button b = (Button) rootView.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Scan result");
                builder.setMessage(GestorFavoritos.getInstance(getContext()).toString()) ;
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        Button b2 = (Button) rootView.findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GestorFavoritos.getInstance().addCarreira(GestorInformacao.getInstance().findCarreiraById(1));

            }
        });

        Button b3 = (Button) rootView.findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GestorFavoritos.getInstance().removeCarreira(GestorInformacao.getInstance().findCarreiraById(1));

            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }

}

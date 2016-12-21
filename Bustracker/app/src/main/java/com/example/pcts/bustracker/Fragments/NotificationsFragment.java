package com.example.pcts.bustracker.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pcts.bustracker.Activities.CarreiraActivity;

import com.example.pcts.bustracker.Activities.ParagemActivity;
import com.example.pcts.bustracker.R;



/**
 * Created by pcts on 11/23/2016.
 */

public class NotificationsFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        ((Toolbar) getActivity().findViewById(R.id.toolbar)).setTitle("Notificações");


        Button button = (Button) rootView.findViewById(R.id.button7);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ParagemActivity.class);
                intent.putExtra(ParagemActivity.KEY_PARAGEM_INTENT, 1);
                startActivity(intent);
            }
        });

        Button button2 = (Button) rootView.findViewById(R.id.button8);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CarreiraActivity.class);
                intent.putExtra(CarreiraActivity.KEY_CARREIRA_INTENT, 1);
                startActivity(intent);
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }
}

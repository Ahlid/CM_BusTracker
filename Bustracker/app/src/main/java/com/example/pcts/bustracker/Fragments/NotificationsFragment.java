package com.example.pcts.bustracker.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.pcts.bustracker.Managers.GestorInformacao;
import com.example.pcts.bustracker.Managers.GestorNotificacao;
import com.example.pcts.bustracker.Model.Notificacao;
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



        Button b = (Button) rootView.findViewById(R.id.button4);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notificacao n = new Notificacao(GestorInformacao.getInstance().findParagemById(1),GestorInformacao.getInstance().findCarreiraById(1),5);
                GestorNotificacao.getInstance().addNotificacao(n);

               Toast.makeText(getContext(),n.toString(),Toast.LENGTH_LONG).show();

            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }
}

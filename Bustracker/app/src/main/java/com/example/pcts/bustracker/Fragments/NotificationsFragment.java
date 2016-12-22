package com.example.pcts.bustracker.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.pcts.bustracker.Activities.CarreiraActivity;

import com.example.pcts.bustracker.Activities.ParagemActivity;
import com.example.pcts.bustracker.Lists.FavoritesAdapterCarreira;
import com.example.pcts.bustracker.Lists.NotificacaoAdapter;
import com.example.pcts.bustracker.Managers.GestorFavoritos;
import com.example.pcts.bustracker.Managers.GestorInformacao;
import com.example.pcts.bustracker.Managers.GestorNotificacao;
import com.example.pcts.bustracker.Model.Carreira;
import com.example.pcts.bustracker.Model.Notificacao;
import com.example.pcts.bustracker.Model.Paragem;
import com.example.pcts.bustracker.R;

import java.util.List;


/**
 * Created by pcts on 11/23/2016.
 */

public class NotificationsFragment extends Fragment {

View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        ((Toolbar) getActivity().findViewById(R.id.toolbar)).setTitle("Notificações");


        Button button = (Button) rootView.findViewById(R.id.button7);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ParagemActivity.class);
                intent.putExtra(ParagemActivity.KEY_PARAGEM_INTENT, 2);
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

        actualizarListaCarreiras();
        // Inflate the layout for this fragment
        return rootView;
    }




    private void actualizarListaCarreiras(){


        if(GestorNotificacao.getInstance().getNotificacoes().size() == 0) {
            for (Paragem p: GestorInformacao.getInstance().getParagems()) {
                GestorNotificacao.getInstance().addNotificacao(new Notificacao(p,GestorInformacao.getInstance().findCarreiraById(1),6));
            }
         }

        ListView listViewNotificacao = (ListView) this.rootView.findViewById(R.id.list_view_notificacao);
        List<Notificacao> notificacaos = GestorNotificacao.getInstance().getNotificacoes();

        NotificacaoAdapter adapter = new NotificacaoAdapter(rootView.getContext(), notificacaos);
        listViewNotificacao.setAdapter(adapter);


    }

}

package com.example.pcts.bustracker.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;

import com.example.pcts.bustracker.Lists.FavoritesAdapterCarreira;
import com.example.pcts.bustracker.Lists.FavoritesAdapterParagem;
import com.example.pcts.bustracker.Managers.GestorFavoritos;
import com.example.pcts.bustracker.Managers.GestorInformacao;
import com.example.pcts.bustracker.Model.Carreira;
import com.example.pcts.bustracker.Model.Paragem;
import com.example.pcts.bustracker.R;

import java.util.List;

/**
 * Created by pcts on 11/23/2016.
 */

public class FavoritesFragment extends Fragment {
View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         this.rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        ((Toolbar) getActivity().findViewById(R.id.toolbar)).setTitle("Favoritos");



        TabHost host = (TabHost) rootView.findViewById( R.id.tab_favoritos);
        host.setup();

        //TODO: resolver problema das tabs em tablets

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Carreiras");
        spec.setContent(R.id.Carreiras);
        spec.setIndicator("Carreiras");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Paragens");
        spec.setContent(R.id.Paragens);
        spec.setIndicator("Paragens");
        host.addTab(spec);




        Button b = (Button) rootView.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Scan result");
                builder.setMessage(GestorFavoritos.getInstance(getContext()).toString()) ;
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                actualizarListaCarreiras();
                actualizarListaParagens();

            }
        });

        Button b2 = (Button) rootView.findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                List<Paragem> paragens = GestorInformacao.getInstance().getParagems();
                List<Carreira> carreiras = GestorInformacao.getInstance().getCarreiras();

                for (Paragem p: paragens) {
                    GestorFavoritos.getInstance().addParagem(p);
                }

                for (Carreira c: carreiras) {
                    GestorFavoritos.getInstance().addCarreira(c);
                }


            }
        });

        Button b3 = (Button) rootView.findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                List<Paragem> paragens = GestorInformacao.getInstance().getParagems();
                List<Carreira> carreiras = GestorInformacao.getInstance().getCarreiras();

                for (Paragem p: paragens) {
                    GestorFavoritos.getInstance().removeParagem(p);
                }

                for (Carreira c: carreiras) {
                    GestorFavoritos.getInstance().removeCarreira(c);
                }


            }
        });



        actualizarListaCarreiras();
        actualizarListaParagens();
        // Inflate the layout for this fragment
        return rootView;
    }



    private void actualizarListaCarreiras(){

        ListView listViewFavoritos = (ListView) this.rootView.findViewById(R.id.list_view_favoritos_carreiras);
        List<Carreira> carreiras = GestorFavoritos.getInstance().getCarreiras();

        FavoritesAdapterCarreira adapter = new FavoritesAdapterCarreira(rootView.getContext(), carreiras);
        listViewFavoritos.setAdapter(adapter);

    }

    private void actualizarListaParagens(){
        ListView listViewFavoritos = (ListView) this.rootView.findViewById(R.id.list_view_favoritos_paragens);//List<Map<String, String>> listaParagemFavoritos = new ArrayList<>();
        List<Paragem> paragens = GestorFavoritos.getInstance().getParagens();

        FavoritesAdapterParagem adapter = new FavoritesAdapterParagem(rootView.getContext(), paragens);
        listViewFavoritos.setAdapter(adapter);
    }

}

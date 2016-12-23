package com.example.pcts.bustracker.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pcts.bustracker.Lists.InformacoesCarreiraAdapter;
import com.example.pcts.bustracker.Lists.InformacoesParagemAdapter;
import com.example.pcts.bustracker.Managers.GestorInformacao;
import com.example.pcts.bustracker.Model.Carreira;
import com.example.pcts.bustracker.Model.Paragem;
import com.example.pcts.bustracker.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pcts on 11/23/2016.
 */

public class InfoFragment extends Fragment {

    private final String PARAGENS = "Paragens";
    private final String CARREIRAS = "Carreiras";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_information, container, false);

        Toolbar toolbar =  (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Informações");

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

        inicializarProcuraCarreiras(rootView);
        inicializarProcuraParagens(rootView);
        return rootView;

    }


    private void inicializarProcuraParagens(final View rootView) {
        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) rootView.findViewById(R.id.auto_complete_procura_paragens);
        final ListView listViewResultadoProcuraParagens = (ListView) rootView.findViewById(R.id.list_view_resultado_procura_paragens);
        final GestorInformacao gestorInformacao = GestorInformacao.getInstance();
        ListAdapter adapter = new InformacoesParagemAdapter(getContext(),gestorInformacao.getParagems());
        listViewResultadoProcuraParagens.setAdapter(adapter);

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<Paragem> paragens = GestorInformacao.getInstance().getParagems();
                List<Paragem> paragensFiltradas = new ArrayList<Paragem>();
                for(Paragem paragem : paragens) {

                    if(paragem.getNome().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        //Adicionar a carreira à lista
                        paragensFiltradas.add(paragem);
                    }

                }

                ListAdapter adapter = new InformacoesParagemAdapter(getContext(), paragensFiltradas);
                ListView listViewResultadoProcuraCarreiras = (ListView) rootView.findViewById(R.id.list_view_resultado_procura_paragens);
                listViewResultadoProcuraCarreiras.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void inicializarProcuraCarreiras(final View rootView) {

        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) rootView.findViewById(R.id.auto_complete_procura_carreiras);
        final ListView listViewResultadoProcuraCarreiras = (ListView) rootView.findViewById(R.id.list_view_resultado_procura_carreiras);
        final GestorInformacao gestorInformacao = GestorInformacao.getInstance();
        ListAdapter adapter = new InformacoesCarreiraAdapter(getContext(),gestorInformacao.getCarreiras());
        listViewResultadoProcuraCarreiras.setAdapter(adapter);

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<Carreira> carreiras = GestorInformacao.getInstance().getCarreiras();
                List<Carreira> carreirasFiltradas = new ArrayList<Carreira>();
                for(Carreira carreira : carreiras) {

                    try {

                        Integer.parseInt(charSequence.toString().toLowerCase()); //Para verificar que é número
                        Integer id = carreira.getNumero();

                        if(id.toString().startsWith(charSequence.toString())) {
                            carreirasFiltradas.add(carreira);
                        }

                    } catch (Exception ex) {

                        if (carreira.getNome().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                            //Adicionar a carreira à lista
                            carreirasFiltradas.add(carreira);
                        }

                    }

                }

                ListAdapter adapter = new InformacoesCarreiraAdapter(getContext(),carreirasFiltradas);
                ListView listViewResultadoProcuraCarreiras = (ListView) rootView.findViewById(R.id.list_view_resultado_procura_carreiras);
                listViewResultadoProcuraCarreiras.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}

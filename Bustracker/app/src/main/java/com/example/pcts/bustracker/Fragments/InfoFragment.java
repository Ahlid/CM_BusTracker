package com.example.pcts.bustracker.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.pcts.bustracker.MainActivity;
import com.example.pcts.bustracker.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by pcts on 11/23/2016.
 */

public class InfoFragment extends Fragment {

    private final String PARAGENS;
    private final String CARREIRAS;
    private final String CARREIRAS_PARAGEM;
    private String Selected;


    String[] items;

     ArrayList<String> listItems;

    ArrayAdapter<String> adapter;

    ListView listView;

    EditText editText;
    View rootView;

    public InfoFragment(){

        PARAGENS = "Paragens";
        CARREIRAS = "Carreiras";
        CARREIRAS_PARAGEM = "Carreiras & Paragem";
        Selected =CARREIRAS;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.fragment_information, container, false);



        TabHost host = (TabHost) rootView.findViewById( R.id.tabInfo);
        host.setup();

     //TODO: resolver problema das tabs em tablets

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec(CARREIRAS);
        spec.setContent(R.id.carreiras);
        spec.setIndicator(CARREIRAS);
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec(PARAGENS);
        spec.setContent(R.id.paragens);
        spec.setIndicator(PARAGENS);
        host.addTab(spec);

        spec = host.newTabSpec(CARREIRAS_PARAGEM);
        spec.setContent(R.id.carreirasParagens);
        spec.setIndicator(CARREIRAS_PARAGEM);
        host.addTab(spec);
        init(Selected);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ((TextView) rootView.findViewById(R.id.textView4)).setText(charSequence);
                if (!charSequence.toString().equals("")) {
                    listItems.clear();
                    for (String item : items) {

                        if (item.contains(charSequence.toString())) {

                            listItems.add(item);

                        }
                    }

                } else {
                    init(Selected);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        // Inflate the layout for this fragment

        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                ((TextView) rootView.findViewById(R.id.textView4)).setText(s);

               if(s.equals(PARAGENS)){
                   Selected = PARAGENS;
               }else if(s.equals(CARREIRAS)){
                   Selected = CARREIRAS;
               }else {
                   Selected=CARREIRAS_PARAGEM;
               }

                init(Selected);
            }
        });

        return rootView;
    }

    private void init(String tab){

        initHistory(tab);
        initSearch(tab);




    }

    private void initSearch(String tab){
        listView= (ListView) rootView.findViewById(R.id.searchCarreirasResult);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) rootView.findViewById(R.id.textView4)).setText(i+"");

            }
        });

        editText= (EditText) rootView.findViewById(R.id.searchCarreiras);

        if(tab.equals(CARREIRAS)) {
            items = new String[]{"TST 701", "TST 654", "TST 780", "TST 781"};
        }else{
            items = new String[]{"Loja Cidadão", "Mercado", "IPS", "Pontes"};
        }
        listItems=new ArrayList<>(Arrays.asList(items));

        adapter=new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.list_item, R.id.txt, listItems);

        listView.setAdapter(adapter);
    }

    private void initHistory(String tab){
        //TODO:fazer o metodo
    }
}

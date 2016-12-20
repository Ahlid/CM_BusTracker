package com.example.pcts.bustracker.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.pcts.bustracker.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParagemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paragem);

        ListView listView = (ListView) this.findViewById(R.id.lista_proximas_carreiras);

        List<Map<String, String>> sampleList = new ArrayList<>();
        HashMap<String, String> sampleObjectMap1 = new HashMap<String, String>();

        sampleObjectMap1.put("nome_carreira", "Titulo");
        sampleObjectMap1.put("tempo_restante", "Descrição");

        sampleList.add(sampleObjectMap1);

        HashMap<String, String> sampleObjectMap2 = new HashMap<String, String>();
        sampleObjectMap2.put("nome_carreira", "Titulo2");
        sampleObjectMap2.put("tempo_restante", "Descrição2");

        sampleList.add(sampleObjectMap2);

        ListAdapter adapter = new SimpleAdapter(
                this.getApplicationContext(), sampleList,
                R.layout.proxima_carreira_item, new String[] {
                "nome_carreira", "tempo_restante"},
                new int[] { R.id.nome_carreira,
                        R.id.tempo_restante});

        listView.setAdapter(adapter);
    }
}

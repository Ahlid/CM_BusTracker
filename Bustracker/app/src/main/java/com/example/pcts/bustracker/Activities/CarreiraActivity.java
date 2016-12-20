package com.example.pcts.bustracker.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.pcts.bustracker.Managers.GestorInformacao;
import com.example.pcts.bustracker.Model.Carreira;
import com.example.pcts.bustracker.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarreiraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carreiras);

        int idCarreira = getIntent().getIntExtra("Carreira",-1);

        Carreira carreira = GestorInformacao.getInstance().findCarreiraById(idCarreira);
        Toast.makeText(this, carreira.getNome(), Toast.LENGTH_SHORT).show();




    }
}

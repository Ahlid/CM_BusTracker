package com.example.pcts.bustracker.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.pcts.bustracker.Managers.GestorInformacao;
import com.example.pcts.bustracker.Model.Carreira;
import com.example.pcts.bustracker.R;

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

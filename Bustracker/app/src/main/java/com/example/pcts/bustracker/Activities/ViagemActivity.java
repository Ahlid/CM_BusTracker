package com.example.pcts.bustracker.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.pcts.bustracker.Managers.GestorInformacao;
import com.example.pcts.bustracker.Model.Viagem;
import com.example.pcts.bustracker.Model.ViagemObserver;
import com.example.pcts.bustracker.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by pcts on 12/22/2016.
 */

public class ViagemActivity extends FragmentActivity implements OnMapReadyCallback, ViagemObserver {


    private GoogleMap mMap;
    private Viagem viagem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viagem_main);


        //handle viagem
        viagem = GestorInformacao.getInstance().getViagemById(1);
        viagem.addObserver(this);

        //((Toolbar) findViewById(R.id.toolbar)).setTitle("Viagem Tracker");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_viagem);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // Other supported types include: MAP_TYPE_NORMAL,
        // MAP_TYPE_TERRAIN, MAP_TYPE_HYBRID and MAP_TYPE_NONE
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        setupMap();

    }

    private void setupMap() {


        PolylineOptions lineOptions = new PolylineOptions();
        lineOptions.addAll(viagem.getTrajeto());
        lineOptions.width(10);
        lineOptions.color(Color.BLUE);
        mMap.addPolyline(lineOptions);


    }

    @Override
    public void onChangePosition(Viagem v) {

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                PolylineOptions lineOptions = new PolylineOptions();
                lineOptions.getPoints().clear();
                lineOptions.addAll(viagem.getTrajeto());
                lineOptions.width(10);
                lineOptions.color(Color.BLUE);
                mMap.addPolyline(lineOptions);
            }
        });

        Log.d("Mapa " + viagem.getId(), " viagem changed: ");
    }
}

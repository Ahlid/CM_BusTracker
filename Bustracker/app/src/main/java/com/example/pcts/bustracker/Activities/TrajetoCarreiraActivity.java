package com.example.pcts.bustracker.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.pcts.bustracker.Fragments.Map.FetchUrl;
import com.example.pcts.bustracker.GoogleMapsUtilities.MyItem;
import com.example.pcts.bustracker.GoogleMapsUtilities.OwnIconRendered;
import com.example.pcts.bustracker.Managers.GestorInformacao;
import com.example.pcts.bustracker.Model.Paragem;
import com.example.pcts.bustracker.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcts on 12/23/2016.
 */

public class TrajetoCarreiraActivity extends AppCompatActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    private ClusterManager<MyItem> mClusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trajeto_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_viagem);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(38.528244, -8.882786), 15));

        setUpClusterer(map);
    }



    private String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    private void setUpClusterer(GoogleMap map) {
        // Declare a variable for the cluster manager

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MyItem>(this, map);
        OwnIconRendered i = new OwnIconRendered(this,map,mClusterManager);
        mClusterManager.setRenderer(i);
        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        map.setOnCameraChangeListener(mClusterManager);
        map.setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
       // addItems(map);

        drawPath(GestorInformacao.getInstance().getCarreiras().get(0).getParagem(0).getPosicao(),GestorInformacao.getInstance().getCarreiras().get(0).getParagem(1).getPosicao(),map);
        drawPath(GestorInformacao.getInstance().getCarreiras().get(0).getParagem(1).getPosicao(),GestorInformacao.getInstance().getCarreiras().get(0).getParagem(2).getPosicao(),map);
        drawPath(GestorInformacao.getInstance().getCarreiras().get(0).getParagem(2).getPosicao(),GestorInformacao.getInstance().getCarreiras().get(0).getParagem(3).getPosicao(),map);
        drawPath(GestorInformacao.getInstance().getCarreiras().get(0).getParagem(3).getPosicao(),GestorInformacao.getInstance().getCarreiras().get(0).getParagem(4).getPosicao(),map);
    }


    private void drawPath(LatLng origin,LatLng dest, GoogleMap map){


        // Getting URL to the Google Directions API
        String url = getUrl(origin, dest);
        Log.d("onMapClick", url.toString());
        FetchUrl FetchUrl = new FetchUrl(map);

        // Start downloading json data from Google Directions API
        FetchUrl.execute(url);
        //move map camera
        map.moveCamera(CameraUpdateFactory.newLatLng(origin));
        map.animateCamera(CameraUpdateFactory.zoomTo(11));
    }

    private void addItems(final GoogleMap map) {

        ArrayList<LatLng> points;
        PolylineOptions lineOptions = null;
        points = new ArrayList<>();
        lineOptions = new PolylineOptions();


        List<Paragem> paragens = GestorInformacao.getInstance().getParagems();

        for(Paragem p : paragens){


            MyItem offsetItem = new MyItem(p);

            points.add(p.getPosicao());
            //mClusterManager.addItem(offsetItem);
        }

            lineOptions.addAll(points);
          lineOptions.width(10);
          lineOptions.color(Color.BLUE);
        map.addPolyline(lineOptions);

    }
}

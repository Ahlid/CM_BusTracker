package com.example.pcts.bustracker.Fragments.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * Created by pcts on 11/23/2016.
 */

public class MainFragment extends Fragment {

    private ClusterManager<MyItem> mClusterManager;
    public MainFragment() {
        mClusterManager = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ((Toolbar) getActivity().findViewById(R.id.toolbar)).setTitle("BusTracker");

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(final GoogleMap map) {

                map.setMyLocationEnabled(true);
                map.getUiSettings().setZoomControlsEnabled(true);
                // Other supported types include: MAP_TYPE_NORMAL,
                // MAP_TYPE_TERRAIN, MAP_TYPE_HYBRID and MAP_TYPE_NONE
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                setUpClusterer(map);

            }
        });

        // Inflate the layout for this fragment
        return rootView;
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
        // Declare a variable for the cluster manager.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(38.528244, -8.882786), 15));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MyItem>(getContext(), map);
       OwnIconRendered i = new OwnIconRendered(getContext(),map,mClusterManager);
        mClusterManager.setRenderer(i);
        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        map.setOnCameraChangeListener(mClusterManager);
        map.setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
        addItems(map);

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
            mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
                @Override
                public boolean onClusterItemClick(MyItem myItem) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Scan result");
                    builder.setMessage(myItem.getParagem().toString()+"\n"+GestorInformacao.getInstance().obterProximasPassagens(myItem.getParagem())) ;
                    //TODO: ver detalhes, adicionar favoritos
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    return false;
                }
            });
            points.add(p.getPosicao());
            //mClusterManager.addItem(offsetItem);
        }

         /*   lineOptions.addAll(points);
          lineOptions.width(10);
          lineOptions.color(Color.BLUE);*/
        map.addPolyline(lineOptions);

    }

}

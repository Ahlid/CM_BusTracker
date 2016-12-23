package com.example.pcts.bustracker.Fragments.Map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.example.pcts.bustracker.Model.Viagem;
import com.example.pcts.bustracker.Model.ViagemObserver;
import com.example.pcts.bustracker.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcts on 11/23/2016.
 */

public class MainFragment extends Fragment implements ViagemObserver{

    private ClusterManager<MyItem> mClusterManager;
    private GoogleMap mMap;

    public MainFragment() {
        mClusterManager = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ((Toolbar) getActivity().findViewById(R.id.toolbar)).setTitle("BusTracker");


        List<Viagem> viagems = GestorInformacao.getInstance().getViagens();

        for(Viagem viagem: viagems){
            viagem.addObserver(this);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(final GoogleMap map) {
                mMap = map;
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(38.528244, -8.882786), 15));

                //setUpClusterer(map);
                configuremap();

            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        List<Viagem> viagems = GestorInformacao.getInstance().getViagens();

        for(Viagem viagem: viagems){
            viagem.removeObserver(this);
        }

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
        //FetchUrl.execute(url);
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

    private void configuremap() {

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // Other supported types include: MAP_TYPE_NORMAL,
        // MAP_TYPE_TERRAIN, MAP_TYPE_HYBRID and MAP_TYPE_NONE
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);



        List<Viagem> viagems = GestorInformacao.getInstance().getViagens();

        for(Viagem viagem : viagems){

            if(viagem.getTrajeto().size() >0) {
                LatLng posicao = viagem.getTrajeto().get(viagem.getTrajeto().size()-1);
                CameraPosition cameraPosition = new CameraPosition.Builder().
                        target(posicao).
                        //tilt(60).
                                zoom(19).

                                build();

               // mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.ic_tracker);
                Bitmap b=bitmapdraw.getBitmap();

                b = get_Resized_Bitmap(b, 250, 250);

                final MarkerOptions actual = new MarkerOptions()
                        .position(posicao)
                        .title(viagem.getCarreira().getNome());
                //.icon(BitmapDescriptorFactory.fromBitmap(b));


                mMap.addMarker(actual);

                addCircleToMap(posicao, mMap);


            }

        }


    }



    private void addCircleToMap(LatLng pos, GoogleMap mapView) {

        // circle settings
        int radiusM = 2;
        double latitude = pos.latitude;
        double longitude = pos.longitude;

        // draw circle
        int d = 500; // diameter
        Bitmap bm = Bitmap.createBitmap(d, d, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint();
        p.setColor(getResources().getColor(R.color.colorPrimary));
        c.drawCircle(d/2, d/2, d/2, p);

        // generate BitmapDescriptor from circle Bitmap
        BitmapDescriptor bmD = BitmapDescriptorFactory.fromBitmap(bm);

// mapView is the GoogleMap
        mapView.addGroundOverlay(new GroundOverlayOptions().
                image(bmD).
                position(pos,radiusM*2,radiusM*2));
    }

    public Bitmap get_Resized_Bitmap(Bitmap bmp, int newHeight, int newWidth) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECconREATE" THE NEW BITMAP
        Bitmap newBitmap = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, false);
        return newBitmap ;
    }

    @Override
    public void onChangePosition(Viagem v) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mMap.clear();

               configuremap();

            }
        });
    }
}

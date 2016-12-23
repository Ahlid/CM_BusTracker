package com.example.pcts.bustracker.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;

import com.example.pcts.bustracker.Managers.GestorInformacao;
import com.example.pcts.bustracker.Model.Paragem;
import com.example.pcts.bustracker.Model.Viagem;
import com.example.pcts.bustracker.Model.ViagemObserver;
import com.example.pcts.bustracker.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.System.out;

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


        List<Paragem> paragems = viagem.getCarreira().getTrajeto();

        for(Paragem p : paragems){
            MarkerOptions marker = new MarkerOptions().position(p.getPosicao()).title("Hello Maps");

// Changing marker icon
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bus_stop));

// adding marker
            mMap.addMarker(marker);

        }



    }

    @Override
    public void onChangePosition(Viagem v) {

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mMap.clear();

                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                // Other supported types include: MAP_TYPE_NORMAL,
                // MAP_TYPE_TERRAIN, MAP_TYPE_HYBRID and MAP_TYPE_NONE
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                PolylineOptions lineOptions = new PolylineOptions();
                lineOptions.getPoints().clear();
                lineOptions.addAll(viagem.getTrajeto());
                lineOptions.width(10);
                lineOptions.color(Color.BLUE);
                mMap.addPolyline(lineOptions);
              //  mMap.setBuildingsEnabled(true);


                if(viagem.getTrajeto().size() >0) {
                    LatLng posicao = viagem.getTrajeto().get(viagem.getTrajeto().size()-1);
                    CameraPosition cameraPosition = new CameraPosition.Builder().
                            target(posicao).
                            //tilt(60).
                            zoom(19).

                            build();

                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.ic_action_name);
                    Bitmap b=bitmapdraw.getBitmap();

                    b = get_Resized_Bitmap(b, 250, 250);

                    final MarkerOptions actual = new MarkerOptions()
                            .position(posicao)
                            .title(viagem.getCarreira().getNome())
                            .icon(BitmapDescriptorFactory.fromBitmap(b));


                    mMap.addMarker(actual);

                    addCircleToMap(posicao, mMap);


                }
                List<Paragem> paragems = viagem.getCarreira().getTrajeto();

                for(Paragem p : paragems){
                    MarkerOptions marker = new MarkerOptions().position(p.getPosicao()).title(p.getNome());

// Changing marker icon
                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bus_stop));

// adding marker
                    mMap.addMarker(marker);

                }


            }
        });

        Log.d("Mapa " + viagem.getId(), " viagem changed: ");
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

        // "RECREATE" THE NEW BITMAP
        Bitmap newBitmap = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, false);
        return newBitmap ;
    }


}

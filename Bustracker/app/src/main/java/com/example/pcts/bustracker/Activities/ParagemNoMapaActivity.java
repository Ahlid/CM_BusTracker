package com.example.pcts.bustracker.Activities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.example.pcts.bustracker.Fragments.Map.MainFragment;
import com.example.pcts.bustracker.Managers.GestorInformacao;
import com.example.pcts.bustracker.Model.Paragem;
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

import java.util.List;

/**
 * Created by pcts on 12/23/2016.
 */

public class ParagemNoMapaActivity extends FragmentActivity implements OnMapReadyCallback {

    private Paragem paragem;
    public static String KEY_PARAGEM="pargem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int idParagem = getIntent().getIntExtra(KEY_PARAGEM, -1);
        this.paragem = GestorInformacao.getInstance().findParagemById(idParagem);
        setContentView(R.layout.activity_paragem_mapq);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_paragem);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap mMap) {

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // Other supported types include: MAP_TYPE_NORMAL,
        // MAP_TYPE_TERRAIN, MAP_TYPE_HYBRID and MAP_TYPE_NONE
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        CameraPosition cameraPosition = new CameraPosition.Builder().
                target(this.paragem.getPosicao()).
                //tilt(60).
                        zoom(19).

                        build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


         MarkerOptions actual = new MarkerOptions()
                .position(this.paragem.getPosicao())
                .title(this.paragem.getNome());

        mMap.addMarker(actual);

        addCircleToMap(actual.getPosition(), mMap);



        List<Paragem> paragens = GestorInformacao.getInstance().getParagems();

        for(Paragem p : paragens){
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_bus_stop);
            Bitmap b = MainFragment.castToBitMap(drawable);

            //b = get_Resized_Bitmap(b, 250, 250);

            final MarkerOptions actual22 = new MarkerOptions()
                    .position(p.getPosicao())
                    .title(p.getNome())
                    .icon(BitmapDescriptorFactory.fromBitmap(b));


            mMap.addMarker(actual22);


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
}

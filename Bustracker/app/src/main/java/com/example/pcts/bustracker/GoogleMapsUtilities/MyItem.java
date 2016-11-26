package com.example.pcts.bustracker.GoogleMapsUtilities;

import com.example.pcts.bustracker.Model.Paragem;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by pcts on 11/26/2016.
 */
public class MyItem implements ClusterItem {
    private final Paragem paragem;

    public MyItem(Paragem paragem) {
       this.paragem= paragem;
    }

    public Paragem getParagem() {
        return paragem;
    }

    @Override
    public LatLng getPosition() {
        return paragem.getPosicao();
    }


}
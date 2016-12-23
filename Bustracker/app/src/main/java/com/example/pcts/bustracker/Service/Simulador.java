package com.example.pcts.bustracker.Service;

import android.util.Log;

import com.example.pcts.bustracker.Model.Viagem;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by pcts on 12/22/2016.
 */

public class Simulador extends Thread {

    Viagem viagem;
    ArrayList<LatLng> path;

    public Simulador(Viagem viagem, ArrayList<LatLng> path){
        this.viagem = viagem;
        this.path = path;
    }


    public void run() {
        float sleep =400;
        int i = 0;
        while (true) {


            if(i+1 < path.size()){
                sleep = Viagem.calcularTempo(Viagem.calcularDistancia(path.get(i),path.get(i+1)));
            }else{
                sleep = 400;
            }


            sleep = Math.max(sleep, 600);

            try {
                Thread.sleep((long) sleep);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }



            viagem.addPosition(this.path.get(i));

            i++;
            if ( i == path.size()) {
                i = 0;
                viagem.clearPoints();
            }else {
                if(i+1 != path.size())
                Log.d("simulador "+viagem.getId(), "caminhando: " + i +"Distancia: " + Viagem.calcularDistancia(path.get(i), path.get(i+1) )+ "m");
            }
        }


    }
}

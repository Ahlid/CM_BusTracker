package com.example.pcts.bustracker.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by pcts on 11/24/2016.
 */

public class GestorInformacao  {

   private List<Autocarro> autocarros;
   private List<Carreira> carreiras;
   private List<Paragem> paragems;

   private static GestorInformacao instance;

    private GestorInformacao() {

        autocarros = new ArrayList<>();
        carreiras = new ArrayList<>();
        paragems = new ArrayList<>();


        //paragens
        Paragem p1 = new Paragem(1,"Pontes");
        Paragem p2 = new Paragem(2,"Loja Cidadão");
        Paragem p3 = new Paragem(3, "IPS");
        Paragem p4 = new Paragem(4, "Mercado");

        //autocarros carreira 780
        Autocarro a1 = new Autocarro(null,true,true,new Date(2016,11,24,12,12,12),p1);
        Autocarro a2 = new Autocarro(null,true,true,new Date(2016,11,24,14,12,12),null);
        Autocarro a3 = new Autocarro(null,true,true,new Date(2016,11,24,15,12,12),null);

        Carreira c = new Carreira(780);
        c.addParagem(p1);
        c.addParagem(p2);
        c.addParagem(p3);
        c.addParagem(p4);

        c.addPartida(a1);
        c.addPartida(a2);
        c.addPartida(a3);

        autocarros.add(a1);
        autocarros.add(a2);
        autocarros.add(a3);

        paragems.add(p1);
        paragems.add(p2);
        paragems.add(p3);
        paragems.add(p4);

        carreiras.add(c);

        p1.addCarreira(c);
        p2.addCarreira(c);
        p3.addCarreira(c);
        p4.addCarreira(c);

    }

    public static GestorInformacao getInstance(){
        if(instance == null){
            instance = new GestorInformacao();
        }

        return instance;
    }

    public List<Autocarro> getAutocarros() {
        return autocarros;
    }

    public List<Carreira> getCarreiras() {
        return carreiras;
    }

    public List<Paragem> getParagems() {
        return paragems;
    }

    public Carreira findCarreira(int id){

        for (Carreira c : this.carreiras){
            if(c.getNumero() == id){
                return c;
            }
        }

        return null;
    }

    public Paragem findParagem(int id){

        for (Paragem p : this.paragems){
            if(p.getId() == id){
                return p;
            }
        }

        return null;
    }
}

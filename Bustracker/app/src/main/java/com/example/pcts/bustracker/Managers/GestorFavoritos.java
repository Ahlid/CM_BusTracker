package com.example.pcts.bustracker.Managers;

import android.content.Context;
import android.database.Cursor;

import com.example.pcts.bustracker.Database.DatabaseFavoritos;
import com.example.pcts.bustracker.Model.Carreira;
import com.example.pcts.bustracker.Model.Paragem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcts on 11/24/2016.
 */

public class GestorFavoritos {


    private static GestorFavoritos instance;
    private List<Carreira> carreiras;
    private List<Paragem> paragens;
    private DatabaseFavoritos db;

    private final String TIPO_CARREIRA="C";
    private final String TIPO_PARAGEM="P";
    private static final String COL_2 = "TIPO";
    private static final String COL_3 = "ID_FAV";

    private GestorFavoritos(Context context){

        db = new DatabaseFavoritos(context);
        carreiras = new ArrayList<>();
        paragens = new ArrayList<>();


       Cursor c = db.getAllData();
        while(c.moveToNext()) {


            if(c.getString(c.getColumnIndex(COL_2)).equals(this.TIPO_CARREIRA)) {
                this.addCarreira(GestorInformacao.getInstance().findCarreiraById(Integer.parseInt(c.getString(c.getColumnIndex(COL_3)))));
            }else {
                this.addParagem(GestorInformacao.getInstance().findParagemById(Integer.parseInt(c.getString(c.getColumnIndex(COL_3)))));
            }


        }
    }

    public static GestorFavoritos getInstance(){

        return instance;
    }


    public static GestorFavoritos getInstance(Context context){
        if(instance == null){
            instance = new GestorFavoritos(context);
        }

        return instance;
    }

    private static void getData(){

        //TODO: aqui ir buscar à memória interna
    }



    public List<Carreira> getCarreiras(){
        return new ArrayList<>(this.carreiras);

    }

    public List<Paragem> getParagens() {
        return new ArrayList<>(paragens);
    }

    public boolean addCarreira(Carreira c){

            boolean res = (this.carreiras.contains(c) || c == null) ? false : this.carreiras.add(c);

        if(res){
            this.db.insertData(c.getId(),this.TIPO_CARREIRA);
        }

        return res;
    }

    //TODO: Métodos uteis de getters, adders e removers

    public boolean addParagem(Paragem p){
        boolean res = (this.paragens.contains(p)) ? false : this.paragens.add(p);

        if(res){
            this.db.insertData(p.getId(),this.TIPO_PARAGEM);
        }


        return res;
    }

    public boolean removeCarreira(Carreira c){
        boolean res = this.carreiras.remove(c);

        if(res){
            this.db.deleteData(Integer.toString(c.getId()),this.TIPO_CARREIRA);
        }

        return  res;
    }

    public boolean removeParagem(Paragem p){
        boolean  res = this.paragens.remove(p);

        if(res){
            this.db.deleteData(Integer.toString(p.getId()),this.TIPO_PARAGEM);
        }

        return  res;
    }


    @Override
    public String toString() {
        return "GestorFavoritos{" +
                "paragens=" + paragens +
                ", carreiras=" + carreiras +
                '}';
    }
}

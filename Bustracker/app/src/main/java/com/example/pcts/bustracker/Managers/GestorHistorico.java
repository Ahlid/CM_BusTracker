package com.example.pcts.bustracker.Managers;

import com.example.pcts.bustracker.Model.Carreira;
import com.example.pcts.bustracker.Model.Paragem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcts on 11/29/2016.
 */

public class GestorHistorico {

    private static GestorHistorico instance;

    private List<Carreira> carreiras;
    private List<Paragem> paragens;

    private GestorHistorico(){
        this.carreiras = new ArrayList<>();
        this.paragens = new ArrayList<>();
        loadData();
    }


    public static  GestorHistorico getInstance(){
        if(instance == null){
            instance = new GestorHistorico();
        }
        return instance;
    }


    public List<Carreira> getCarreiras(){
        return this.carreiras;

    }

    public List<Paragem> getParagens() {
        return paragens;
    }

    public boolean addCarreira(Carreira c){

        boolean res = (this.carreiras.contains(c)) ? false : this.carreiras.add(c);
        saveData();
        return res;
    }


    public boolean addParagem(Paragem p){
        boolean res = (this.paragens.contains(p)) ? false : this.paragens.add(p);
        saveData();
        return res;
    }


    private void saveData(){

    }

    private void loadData(){

    }

}

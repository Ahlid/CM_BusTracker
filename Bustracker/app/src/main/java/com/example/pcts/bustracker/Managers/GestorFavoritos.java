package com.example.pcts.bustracker.Managers;

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

    private GestorFavoritos(){

        carreiras = new ArrayList<>();
        paragens = new ArrayList<>();

    }

    public static GestorFavoritos getInstance(){
        if(instance == null){
            getData();
        }

        return instance;
    }

    private static void getData(){

        //TODO: aqui ir buscar à memória interna
    }

    //TODO: Métodos uteis de getters, adders e removers
}
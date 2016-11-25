package com.example.pcts.bustracker.Model;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pcts on 11/24/2016.
 */

public class Carreira {
    private int id;
    private int numero;
    private String nome;
    private List<Paragem> trajeto;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Paragem getParagem (int i){
       Paragem res = null;
        if(i< this.trajeto.size()){
            res = this.trajeto.get(i);
        }

        return res;
    }
    public boolean temParagem(Paragem e){
        return this.trajeto.contains(e);
    }

    public boolean addParagem(Paragem e) {
        return this.trajeto.add(e);
    }

    public Paragem removeParagem(int i) {
        return this.trajeto.remove(i);
    }

    public boolean removeParagem(Paragem p) {
        return this.trajeto.remove(p);
    }

    public boolean verificarPassagem(Paragem p, TipoViagem tv){

        //TODO: acabar metodo
        return true;
    }

    @Override
    public String toString() {
        return "Carreira{" +
                "id=" + id +
                ", numero=" + numero +
                ", nome='" + nome + '\'' +
                ", trajeto=" + trajeto +
                '}';
    }
}


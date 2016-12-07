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

    public Carreira(int id, int numero, String nome) {
        this.id = id;
        this.numero = numero;
        this.nome = nome;
        this.trajeto = new ArrayList<>();
    }

    public Carreira(int id, int numero, String nome, List<Paragem> trajeto) {
        this.id = id;
        this.numero = numero;
        this.nome = nome;
        this.trajeto = trajeto;
    }

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

    public boolean verificarPassagem(Paragem actual, Paragem proxima, TipoViagem tv){

        if(!this.temParagem(actual) || ! this.temParagem(proxima)){
            return false;
        }

        int indexActual = this.trajeto.indexOf(actual);
        int indexProxima = this.trajeto.indexOf(proxima);


        if(TipoViagem.IDA == tv){
            return indexActual < indexProxima;
        }else {
            return indexActual > indexProxima;
        }


    }

    @Override
    public String toString() {
        return "CarreiraActivity{" +
                "id=" + id +
                ", numero=" + numero +
                ", nome='" + nome + '\'' +
                ", trajeto=" + trajeto +
                '}';
    }
}


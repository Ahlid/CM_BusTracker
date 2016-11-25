package com.example.pcts.bustracker.Model;

import java.util.Date;

/**
 * Created by pcts on 11/25/2016.
 */

public class Viagem implements Comparable{

    private int id;
    private Autocarro autocarro;
    private Carreira  carreira;
    private Date partida;
    private Paragem actual;
    private TipoViagem tipoViagem;

    public Viagem(int id,Autocarro autocarro, Carreira carreira, Date partida, TipoViagem tipoViagem) {
        this.id=id;
        this.autocarro = autocarro;
        this.carreira = carreira;
        this.partida = partida;
        this.actual = carreira.getParagem(0);
        this.tipoViagem = tipoViagem;
    }

    public Autocarro getAutocarro() {
        return autocarro;
    }

    public void setAutocarro(Autocarro autocarro) {
        this.autocarro = autocarro;
    }

    public Carreira getCarreira() {
        return carreira;
    }

    public void setCarreira(Carreira carreira) {
        this.carreira = carreira;
    }

    public Date getPartida() {
        return partida;
    }

    public void setPartida(Date partida) {
        this.partida = partida;
    }

    public Paragem getActual() {
        return actual;
    }

    public void setActual(Paragem actual) {
        this.actual = actual;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoViagem getTipoViagem() {
        return tipoViagem;
    }

    public void setTipoViagem(TipoViagem tipoViagem) {
        this.tipoViagem = tipoViagem;
    }

    @Override
    public int compareTo(Object o) {

        Viagem v = (Viagem) o ;

        return this.partida.compareTo(v.partida);
    }

    @Override
    public String toString() {
        return "Viagem{" +
                "id=" + id +
                ", autocarro=" + autocarro +
                ", carreira=" + carreira +
                ", partida=" + partida +
                ", actual=" + actual +
                ", tipoViagem=" + tipoViagem +
                '}';
    }
}

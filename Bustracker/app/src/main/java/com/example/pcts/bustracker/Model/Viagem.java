package com.example.pcts.bustracker.Model;

import java.util.Date;

/**
 * Created by pcts on 11/25/2016.
 */

public class Viagem implements Comparable{

    private int id;
    private Autocarro autocarro;
    private Carreira  carreira;
    private Date dataPartida;
    private Paragem paragemAtual;
    private TipoViagem tipoViagem;

    public Viagem(int id,Autocarro autocarro, Carreira carreira, Date dataPartida, TipoViagem tipoViagem) {
        this.id=id;
        this.autocarro = autocarro;
        this.carreira = carreira;
        this.dataPartida = dataPartida;
        this.paragemAtual = carreira.getParagem(0);
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

    public Date getDataPartida() {
        return dataPartida;
    }

    public void setDataPartida(Date dataPartida) {
        this.dataPartida = dataPartida;
    }

    public Paragem getParagemAtual() {
        return paragemAtual;
    }

    public void setParagemAtual(Paragem paragemAtual) {
        this.paragemAtual = paragemAtual;
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

        int timeO = v.dataPartida.getHours()*60*60 + v.dataPartida.getMinutes() *60 + v.dataPartida.getSeconds();
        int timeT = this.dataPartida.getHours()*60*60 + this.dataPartida.getMinutes()*60 + this.dataPartida.getSeconds();

        return timeT - timeO;

    }

    @Override
    public String toString() {
        return "Viagem{" +
                "id=" + id +
                ", autocarro=" + autocarro +
                ", carreira=" + carreira +
                ", dataPartida=" + dataPartida +
                ", paragemAtual=" + paragemAtual +
                ", tipoViagem=" + tipoViagem +
                '}';
    }
}

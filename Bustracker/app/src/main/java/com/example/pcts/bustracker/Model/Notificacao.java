package com.example.pcts.bustracker.Model;

import java.util.Date;

/**
 * Created by pcts on 11/29/2016.
 */

public class Notificacao {

    private int id;
    private Paragem paragem;
    private Carreira carreira;
    private int minutos;

    public Notificacao(Paragem paragem, Carreira carreira, int minutos) {
        this.paragem = paragem;
        this.carreira = carreira;
        this.minutos = minutos;
    }

    public Paragem getParagem() {
        return paragem;
    }

    public void setParagem(Paragem paragem) {
        this.paragem = paragem;
    }

    public Carreira getCarreira() {
        return carreira;
    }

    public void setCarreira(Carreira carreira) {
        this.carreira = carreira;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

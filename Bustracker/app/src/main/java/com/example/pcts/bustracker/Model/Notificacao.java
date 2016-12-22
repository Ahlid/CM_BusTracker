package com.example.pcts.bustracker.Model;

import java.util.Date;

/**
 * Created by pcts on 11/29/2016.
 */

public class Notificacao implements Comparable<Notificacao> {

    private long id;
    private Paragem paragem;
    private Carreira carreira;
    private int minutos;
    private boolean estado;

    public Notificacao(long id,Paragem paragem, Carreira carreira, int minutos) {
        this.paragem = paragem;
        this.carreira = carreira;
        this.minutos = minutos;
        this.id = id;
        estado=true;
    }

    public Notificacao(Paragem paragem, Carreira carreira, int minutos) {
        this.paragem = paragem;
        this.carreira = carreira;
        this.minutos = minutos;
        estado = true;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Notificacao{" +
                "id=" + id +
                ", paragem=" + paragem +
                ", carreira=" + carreira +
                ", minutos=" + minutos +
                '}';
    }

    @Override
    public int compareTo(Notificacao notificacao) {
       if(this.estado){
           if(this.estado == notificacao.estado){
               return 0;
           }else{
               return -1;
           }

       }else{
           return 1;
       }
    }
}

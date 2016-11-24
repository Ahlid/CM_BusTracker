package com.example.pcts.bustracker.Model;

import java.util.Date;

/**
 * Created by pcts on 11/24/2016.
 */

public class Autocarro {

    private String detalhe;
    private boolean temAcessoCadeiraDeRodas;
    private boolean temAcessoBicicleta;
    private Date partida;
    private Paragem actual;

    public Autocarro(String detalhe, boolean temAcessoCadeiraDeRodas, boolean temAcessoBicicleta, Date partida, Paragem actual) {
        this.detalhe = detalhe;
        this.temAcessoCadeiraDeRodas = temAcessoCadeiraDeRodas;
        this.temAcessoBicicleta = temAcessoBicicleta;
        this.partida = partida;
        this.actual = actual;
    }

    public Paragem getActual() {
        return actual;
    }

    public void setActual(Paragem actual) {
        this.actual = actual;
    }


    public String getDetalhe() {
        return detalhe;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }

    public boolean isTemAcessoCadeiraDeRodas() {
        return temAcessoCadeiraDeRodas;
    }

    public void setTemAcessoCadeiraDeRodas(boolean temAcessoCadeiraDeRodas) {
        this.temAcessoCadeiraDeRodas = temAcessoCadeiraDeRodas;
    }

    public boolean isTemAcessoBicicleta() {
        return temAcessoBicicleta;
    }

    public void setTemAcessoBicicleta(boolean temAcessoBicicleta) {
        this.temAcessoBicicleta = temAcessoBicicleta;
    }

    public Date getPartida() {
        return partida;
    }

    public void setPartida(Date partida) {
        this.partida = partida;
    }

    @Override
    public String toString() {
        return "Autocarro{" +
                "detalhe='" + detalhe + '\'' +
                ", temAcessoCadeiraDeRodas=" + temAcessoCadeiraDeRodas +
                ", temAcessoBicicleta=" + temAcessoBicicleta +
                ", partida=" + partida +
                ", actual=" + actual +
                '}';
    }
}

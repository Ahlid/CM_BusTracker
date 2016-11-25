package com.example.pcts.bustracker.Model;

import java.util.Date;

/**
 * Created by pcts on 11/24/2016.
 */

public class Autocarro {

    private int id;
    private boolean temAcessoCadeiraDeRodas;
    private boolean temAcessoBicicleta;


    public Autocarro(int id, boolean temAcessoCadeiraDeRodas, boolean temAcessoBicicleta) {
        this.id = id;
        this.temAcessoCadeiraDeRodas = temAcessoCadeiraDeRodas;
        this.temAcessoBicicleta = temAcessoBicicleta;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    @Override
    public String toString() {
        return "Autocarro{" +
                "id='" + id + '\'' +
                ", temAcessoCadeiraDeRodas=" + temAcessoCadeiraDeRodas +
                ", temAcessoBicicleta=" + temAcessoBicicleta +

                '}';
    }
}

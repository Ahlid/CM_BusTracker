package com.example.pcts.bustracker.Model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by pcts on 11/24/2016.
 */
public class Paragem {
    private int id;
    private String nome;
    private LatLng posicao;

    public Paragem(int id, String nome, LatLng posicao) {
        this.id = id;
        this.nome = nome;
        this.posicao = posicao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LatLng getPosicao() {
        return posicao;
    }

    public void setPosicao(LatLng posicao) {
        this.posicao = posicao;
    }

    @Override
    public String toString() {
        return "Paragem{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", posicao=" + posicao +
                '}';
    }
}


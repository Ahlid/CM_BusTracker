package com.example.pcts.bustracker.Model;

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
    private List<Carreira> carreiras;

    public Paragem(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.carreiras = new ArrayList<Carreira>();
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

    public List<Carreira> getCarreiras() {
        return carreiras;
    }

    public void setCarreiras(List<Carreira> carreiras) {
        this.carreiras = carreiras;
    }

    public boolean addCarreira(Carreira c){
      return  this.carreiras.add(c);
    }

    public Carreira removeCarreira(int i){
        return this.carreiras.remove(i);
    }

    public boolean removeCarreira(Carreira c){
        return this.carreiras.remove(c);
    }

    public List<Carreira> obterProximasCarreiras(){

        List<Carreira> proximasCarreiras = new ArrayList<>();
        List<CarreiraData> aux = new ArrayList<>();

        for(Carreira c : this.carreiras){
            aux.add(new CarreiraData(c,c.getDataProximaCarreira()));
        }


        Collections.sort(aux);

        proximasCarreiras.add(aux.get(0).carreira);
        proximasCarreiras.add(aux.get(1).carreira);
        proximasCarreiras.add(aux.get(2).carreira);
        proximasCarreiras.add(aux.get(3).carreira);

        return proximasCarreiras;

    }

    @Override
    public String toString() {
        return "Paragem{" +
                "id=" + id +
                ", nome='" + nome + '\'' +

                '}';
    }

    private class CarreiraData implements Comparable {
    public Carreira carreira;
    public Date partida;

    public CarreiraData(Carreira carreira, Date partida) {
        this.carreira = carreira;
        this.partida = partida;
    }

    @Override
    public int compareTo(Object o) {

        CarreiraData aux = (CarreiraData) o;
        return aux.partida.compareTo(this.partida);

    }


}
}


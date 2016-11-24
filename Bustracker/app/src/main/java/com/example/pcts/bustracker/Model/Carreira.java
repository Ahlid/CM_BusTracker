package com.example.pcts.bustracker.Model;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pcts on 11/24/2016.
 */

public class Carreira {
    private int numero;
    private List<Paragem> paragens;
    private List<Autocarro> partidas;


    public Carreira(int numero) {

        this.numero = numero;
        this.paragens = new ArrayList<Paragem>();
        this.partidas = new ArrayList<Autocarro>();

    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public List<Paragem> getParagens() {
        return paragens;
    }

    public void setParagens(List<Paragem> paragens) {
        this.paragens = paragens;
    }

    public List getPartida() {
        return partidas;
    }

    public void addPartida(Autocarro partida) {
        this.partidas.add(partida);
    }

    public boolean addParagem(Paragem e){
        return this.paragens.add(e);
    }
    public Paragem removeParagem(int i){
        return this.paragens.remove(i);
    }
    public boolean removeParagem(Paragem p){
        return this.paragens.remove(p);
    }

    public Date getDataProximaCarreira(){

        Date proxima = null;
        Date actual = new Date();
        for (Autocarro a : this.partidas){
            if(a.getPartida().after(actual)){
                proxima = a.getPartida() ;
                break;
            }
        }

        return proxima;
    }

    public List<Autocarro> getProximasPartidas(){

        List<Autocarro> proximas = new ArrayList<>();
        Date actual = new Date();

        for (Autocarro a : this.partidas){
            if(a.getPartida().after(actual)){
                proximas.add(a);

                if(proximas.size()> 4)
                break;
            }
        }

        return proximas;
    }

    @Override
    public String toString() {
        return "Carreira{" +
                "numero=" + numero +
                ", paragens=" + paragens +
                ", partidas=" + partidas +
                '}';
    }
}

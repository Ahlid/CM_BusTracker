package com.example.pcts.bustracker.Model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
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
    private ArrayList<LatLng> trajeto;
    private ArrayList<ViagemObserver> observers;
    private static final int MILISEGUNDOS_POR_METRO =100;

    public Viagem(int id,Autocarro autocarro, Carreira carreira, Date dataPartida, TipoViagem tipoViagem) {
        this.id=id;
        this.autocarro = autocarro;
        this.carreira = carreira;
        this.dataPartida = dataPartida;
        this.paragemAtual = carreira.getParagem(0);
        this.tipoViagem = tipoViagem;
        this.trajeto = new ArrayList<>();
        this.observers = new ArrayList<>();
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

    public boolean addObserver(ViagemObserver obs) {
       return this.observers.add(obs);
    }

    public boolean removeObserver(ViagemObserver obs) {
        return this.observers.remove(obs);
    }

    public void  notifyObservers(){
        for (ViagemObserver obs: this.observers ) {
            obs.onChangePosition(this);
        }
    }

    public void addPosition(LatLng position){
        this.trajeto.add(position);
        this.notifyObservers();
    }

    public void clearPoints(){
        this.trajeto.clear();
        this.notifyObservers();
    }

    @Override
    public int compareTo(Object o) {

        Viagem v = (Viagem) o ;

        int timeO = v.dataPartida.getHours()*60*60 + v.dataPartida.getMinutes() *60 + v.dataPartida.getSeconds();
        int timeT = this.dataPartida.getHours()*60*60 + this.dataPartida.getMinutes()*60 + this.dataPartida.getSeconds();

        return timeT - timeO;

    }

    public ArrayList<LatLng> getTrajeto() {
        return trajeto;
    }


    public static float calcularDistancia(LatLng p1, LatLng p2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(p2.latitude-p1.latitude);
        double dLng = Math.toRadians(p2.longitude-p1.longitude);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(p1.latitude)) * Math.cos(Math.toRadians(p2.latitude)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return dist;
    }

    public  static float calcularTempo(float distancia){
        return distancia*MILISEGUNDOS_POR_METRO;
    }

    public static int converterParaMinutos(float tempo){
        return (int) tempo/60/60/2;

    }
    public boolean contemPonto(LatLng ponto){
        for (LatLng l : this.trajeto){
            if(l.longitude == ponto.longitude && l.latitude == ponto.latitude){
                return true;
            }
        }

        return false;
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

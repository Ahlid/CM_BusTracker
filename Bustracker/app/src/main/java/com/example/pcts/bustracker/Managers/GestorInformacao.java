package com.example.pcts.bustracker.Managers;

import com.example.pcts.bustracker.Model.Autocarro;
import com.example.pcts.bustracker.Model.Carreira;
import com.example.pcts.bustracker.Model.Paragem;
import com.example.pcts.bustracker.Model.TipoViagem;
import com.example.pcts.bustracker.Model.Viagem;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by pcts on 11/24/2016.
 */

public class GestorInformacao  {

   private List<Autocarro> autocarros;
   private List<Carreira> carreiras;
   private List<Paragem> paragems;
   private List<Viagem> viagens;

   private static GestorInformacao instance;

    private GestorInformacao() {

        this.autocarros = new ArrayList<>();
        this.carreiras = new ArrayList<>();
        this.paragems = new ArrayList<>();
        this.viagens = new ArrayList<>();


        //paragens
        Paragem p1 = new Paragem(1, "Mercado", new LatLng(38.522850, -8.895599));
        Paragem p2 = new Paragem(2,"Lavagem Automatica", new LatLng(38.528479, -8.886475));
        Paragem p3 = new Paragem(3,"Loja Cidadão", new LatLng(38.528244, -8.882786));
        Paragem p4 = new Paragem(4, "Pedro", new LatLng(38.524812, -8.883864));
        Paragem p5 = new Paragem(5, "IPS", new LatLng(38.523601, -8.839417));

        this.addParagens(p1,p2,p3,p4,p5);


        //autocarros
        Autocarro a1 = new Autocarro(1,true,true);
        Autocarro a2 = new Autocarro(2,true,true);
        Autocarro a3 = new Autocarro(3,false,false);

        this.addAutocarros(a1,a2,a3);

        //carreira
        Carreira c = new Carreira(1,780,"Setúbal - IPS");
        c.addParagem(p1);
        c.addParagem(p2);
        c.addParagem(p3);
        c.addParagem(p4);
        c.addParagem(p5);
        carreiras.add(c);

        //viagens
        Viagem v1 = new Viagem(1,a1,c,new Date(2016,11,26,17,10,10),TipoViagem.IDA);
        Viagem v2 = new Viagem(2,a1,c,new Date(2016,11,26,17,10,10),TipoViagem.VOLTA);

        Viagem v3 = new Viagem(3,a2,c,new Date(2016,11,26,10,40,10),TipoViagem.IDA);
        Viagem v4 = new Viagem(4,a2,c,new Date(2016,11,26,11,40,10),TipoViagem.VOLTA);

        Viagem v5 = new Viagem(5,a3,c,new Date(2016,11,26,11,10,10),TipoViagem.IDA);
        Viagem v6 = new Viagem(6,a3,c,new Date(2016,11,26,12,10,10),TipoViagem.VOLTA);

        addViagens(v1,v2,v3,v4,v5,v6);

    }

    public static GestorInformacao getInstance(){
        if(instance == null){
            instance = new GestorInformacao();
        }

        return instance;
    }

    public List<Autocarro> getAutocarros() {
        return autocarros;
    }

    public List<Carreira> getCarreiras() {
        return carreiras;
    }

    public List<Paragem> getParagems() {
        return paragems;
    }

    public List<Viagem> getViagens(){return viagens;}

    public Carreira findCarreiraById(int id){

        for (Carreira c : this.carreiras){
            if(c.getId() == id){
                return c;
            }
        }

        return null;
    }

    public Paragem findParagemById(int id){

        for (Paragem p : this.paragems){
            if(p.getId() == id){
                return p;
            }
        }

        return null;
    }

    public Viagem getViagemById(int id){

        for (Viagem v : this.viagens){
         if(v.getId()==id){
             return v;
         }
        }

        return null;
    }

    public Autocarro getAutocarroById(int id){

        for (Autocarro a : this.autocarros){
            if (a.getId() == id){
                return  a;
            }
        }


        return null;
    }

    public boolean addParagemACarreira(Paragem p,Carreira c){

        if(!c.temParagem(p)){
            return c.addParagem(p);
        }
        return false;
    }

    public boolean addViagem(int id, Carreira c, Autocarro a, Date inicio, TipoViagem tipoViagem){
      return  this.viagens.add(new Viagem(id,a,c,inicio,tipoViagem));
    }

    public List<Viagem> obterProximasPassagens(Paragem p){

        List<Viagem> aux = new ArrayList<>();


        for(Viagem v : this.viagens){
            if(this.isTimeAfter(v.getDataPartida()) && v.getCarreira().verificarPassagem(v.getParagemAtual(),p,v.getTipoViagem())){
             aux.add(v);

                if(aux.size() > 4){
                    break;
                }

            }
        }

        //TODO verificar ordem

        return aux;
    }

    public Date getDataProximaCarreira(Carreira c){

        //TODO verificar para que precisava disto
        return null;
    }

    public List<Viagem> getProximasPartidas(Carreira carreira){

        List<Viagem> viagensOrdenadas = ordenarViagensPorData(new ArrayList<>(this.viagens));
        List<Viagem> resultadoViagens = new ArrayList<>();

        for (Viagem viagem : viagensOrdenadas){
            if(viagem.getCarreira().getId() == carreira.getId() && isTimeAfter(viagem.getDataPartida())){
                resultadoViagens.add(viagem);

                if(resultadoViagens.size() > 4)
                    break;

            }
        }

        return resultadoViagens;
    }

    private List<Viagem> ordenarViagensPorData(List<Viagem> viagens){
        Collections.sort(viagens);
        return viagens;
    }

    private boolean isTimeAfter(Date date){
        Date now = new Date();
        return date.after(now);
    }


    private void addParagens (Paragem ...paragens){

        for (Paragem p : paragens){
            this.paragems.add(p);
        }

    }

    private void addAutocarros (Autocarro ...autocarros){

        for (Autocarro a : autocarros){
            this.autocarros.add(a);
        }

    }
    private void addViagens(Viagem ...viagens){

        for (Viagem v : viagens){
            this.viagens.add(v);
        }
    }

    }

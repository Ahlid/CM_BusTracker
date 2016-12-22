package com.example.pcts.bustracker.Managers;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.example.pcts.bustracker.Database.DatabaseNotificacoes;
import com.example.pcts.bustracker.Model.Carreira;
import com.example.pcts.bustracker.Model.Notificacao;
import com.example.pcts.bustracker.Model.Paragem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pcts on 11/29/2016.
 */

public class GestorNotificacao {

    private static GestorNotificacao instance;
    private DatabaseNotificacoes db;

    private List<Notificacao> notificacoes;

    private GestorNotificacao(Context context){
        this.notificacoes = new ArrayList<>();
        this.db = new DatabaseNotificacoes(context);

        Cursor c = db.getAllData();

        while (c.moveToNext()){

            int id = c.getInt(c.getColumnIndex(DatabaseNotificacoes.COL_1));
            Carreira carreira = GestorInformacao.getInstance().findCarreiraById(c.getInt(c.getColumnIndex(DatabaseNotificacoes.COL_2)));
            Paragem paragem = GestorInformacao.getInstance().findParagemById(c.getInt(c.getColumnIndex(DatabaseNotificacoes.COL_3)));
            int minutos = c.getInt(c.getColumnIndex(DatabaseNotificacoes.COL_4));
            int estado = c.getInt(c.getColumnIndex(DatabaseNotificacoes.COL_5));

            Notificacao n = new Notificacao(id,paragem, carreira,minutos );

            this.notificacoes.add(n);

            n.setEstado(estado == 1? true : false);

        }

        order();


    }

    public static GestorNotificacao  getInstance(Context context){
        if(instance == null){
            instance = new GestorNotificacao(context);
        }

        return instance;
    }

    public static GestorNotificacao getInstance(){
        return instance;
    }

    public boolean addNotificacao(Notificacao n){
        boolean res = (this.notificacoes.contains(n)) ? false : this.notificacoes.add(n);

        if(res){


           Long r= db.insertData(n.getCarreira().getId(), n.getParagem().getId(), n.getMinutos(), n.getEstado());
            n.setId(r);
        }

        return res;
    }

    public boolean removeNotificacao(Notificacao n){

        boolean res =  this.notificacoes.remove(n);

        if(res){
            db.deleteData(Long.toString(n.getId()));
        }

        return res;
    }

    public boolean removeNotificacaoById(Long id){
        boolean res = false;
        for (Notificacao n : this.notificacoes) {
            if(n.getId() == id){
                res = this.removeNotificacao(n);
            }
        }
        return res;
    }

    public List<Notificacao> getNotificacoes() {
        return notificacoes;
    }

    public void order() {

        Collections.sort(this.notificacoes);

    }


    public Notificacao getNotificacaoById(int id) {
        Notificacao n = null;

        for (Notificacao not: notificacoes ) {
            if(not.getId() == id){
                n = not;
                break;
            }
        }

        return n;
    }
}

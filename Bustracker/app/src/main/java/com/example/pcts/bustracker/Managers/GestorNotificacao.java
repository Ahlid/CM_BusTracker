package com.example.pcts.bustracker.Managers;

import com.example.pcts.bustracker.Model.Carreira;
import com.example.pcts.bustracker.Model.Notificacao;
import com.example.pcts.bustracker.Model.Paragem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcts on 11/29/2016.
 */

public class GestorNotificacao {

    private static GestorNotificacao instance;

    private List<Notificacao> notificacoes;

    private GestorNotificacao(){
        this.notificacoes = new ArrayList<>();
    }

    public static GestorNotificacao  getInstance(){
        if(instance == null){
            instance = new GestorNotificacao();
        }

        return instance;
    }

    public boolean addNotificacao(Notificacao n){
        boolean res = (this.notificacoes.contains(n)) ? false : this.notificacoes.add(n);

        return res;
    }

    public boolean removeNotificacao(Notificacao n){
        return this.notificacoes.remove(n);
    }

    public List<Notificacao> getNotificacoes() {
        return notificacoes;
    }
}

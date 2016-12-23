package com.example.pcts.bustracker.Managers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.pcts.bustracker.Database.DatabaseNotificacoes;
import com.example.pcts.bustracker.Model.Carreira;
import com.example.pcts.bustracker.Model.Notificacao;
import com.example.pcts.bustracker.Model.Paragem;
import com.example.pcts.bustracker.Model.Viagem;
import com.example.pcts.bustracker.Model.ViagemObserver;
import com.example.pcts.bustracker.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pcts on 11/29/2016.
 */

public class GestorNotificacao implements ViagemObserver {

    private static GestorNotificacao instance;
    private DatabaseNotificacoes db;
    private Context context;

    private List<Notificacao> notificacoes;

    private GestorNotificacao(Context context){
        this.notificacoes = new ArrayList<>();
        this.db = new DatabaseNotificacoes(context);
        this.context = context;

        GestorInformacao.getInstance().getViagens().get(0).addObserver(this);

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

    protected void showToast(final String msg) {
        //gets the main thread
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                // run this code in the main thread
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onChangePosition(Viagem v) {
        int i =0;
        Paragem p = GestorInformacao.getInstance().findParagemById(2);

        float distancia = v.getTrajeto().size() > 0 ? Viagem.calcularDistancia(v.getTrajeto().get(v.getTrajeto().size()-1), p.getPosicao()) : 9999999;


       Log.d("Notificacao", "Ja tem o "+p.getNome()+"? R: "+v.contemPonto(p.getPosicao()) +"Distancia: "+distancia );


        if(v.getTrajeto().size() > 1){
            LatLng actual = v.getTrajeto().get(v.getTrajeto().size()-1);

            for (Notificacao n: notificacoes) {
                Log.d("Notificacao TEMPO", "tempo: "+ Viagem.calcularTempo(Viagem.calcularDistancia(actual, n.getParagem().getPosicao())) +"devia ser" + n.getMinutos()*60*60*2);

                if(n.getEstado() && !v.contemPonto(n.getParagem().getPosicao())){
                if(n.getCarreira().getId() == v.getCarreira().getId()){//mesma carreira
                    //ver se demora menos do tempo
                    Log.d("Notificacao TEMPO", "tempo: "+ Viagem.calcularTempo(Viagem.calcularDistancia(actual, n.getParagem().getPosicao())) +"devia ser" + n.getMinutos()*60*60*2);

                    if(Viagem.calcularTempo(Viagem.calcularDistancia(actual, n.getParagem().getPosicao())) < n.getMinutos()*60*60*2){

                        //mandar a notificação e meter off o estado
                        n.setEstado(false);
                        Log.d("Notificacao", "sending");

                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
                        mBuilder.setSmallIcon(R.drawable.ic_bus);
                        mBuilder.setContentTitle("Bus is COMING!!!!");
                        mBuilder.setContentText("Menos de "+n.getMinutos()+ "minutos para a carreira "+n.getCarreira().getNumero()+" "+n.getCarreira().getNome()+" chegar á paragem "+n.getParagem().getNome());

                        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

// notificationID allows you to update the notification later on.
                        mNotificationManager.notify(++i, mBuilder.build());

                    }
                }
            }
            }

        }


    }
}

package com.example.pcts.bustracker.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.pcts.bustracker.Managers.GestorInformacao;
import com.example.pcts.bustracker.Managers.GestorNotificacao;
import com.example.pcts.bustracker.Model.Carreira;
import com.example.pcts.bustracker.Model.Notificacao;
import com.example.pcts.bustracker.R;

/**
 * Created by pcts on 12/22/2016.
 */

public class NovaNotificacaoActivity extends AppCompatActivity {

    public final static String KEY_NOTIFICACAO_INTENT = "Notificacao";
    private GestorNotificacao gestorNotificacao;
    private Notificacao notificacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacao);

        this.gestorNotificacao = GestorNotificacao.getInstance();
        int notificacaoId = getIntent().getIntExtra(KEY_NOTIFICACAO_INTENT, -1);
        this.notificacao = gestorNotificacao.getNotificacaoById(notificacaoId);

        if(this.notificacao == null){
            newNotificacao();
        }else{
            editarNotificacao();
        }

    }

    private void editarNotificacao() {
        this.getSupportActionBar().setTitle("Editar Notificação");
    }

    private void newNotificacao() {
        this.getSupportActionBar().setTitle("Criar Notificação");
    }

}

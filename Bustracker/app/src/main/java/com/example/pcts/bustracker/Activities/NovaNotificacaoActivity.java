package com.example.pcts.bustracker.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.pcts.bustracker.Lists.StandartSpinner;
import com.example.pcts.bustracker.Managers.GestorFavoritos;
import com.example.pcts.bustracker.Managers.GestorInformacao;
import com.example.pcts.bustracker.Managers.GestorNotificacao;
import com.example.pcts.bustracker.Model.Carreira;
import com.example.pcts.bustracker.Model.Notificacao;
import com.example.pcts.bustracker.Model.Paragem;
import com.example.pcts.bustracker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcts on 12/22/2016.
 */

public class NovaNotificacaoActivity extends AppCompatActivity {

    public final static String KEY_NOTIFICACAO_INTENT = "Notificacao";
    private GestorNotificacao gestorNotificacao;
    private Notificacao notificacao;
    private Carreira carreiraAtual;
    private Paragem paragemAtual;


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
            this.carreiraAtual = notificacao.getCarreira();
            this.paragemAtual = notificacao.getParagem();

            editarNotificacao();
            
        }

        configSpinner();

    }

    private void configSpinner() {
        List<StandartSpinner> carreirasListaSpinner = new ArrayList<>();
        List<StandartSpinner> paragensListaSpinner = new ArrayList<>();
        List<Carreira> listaCarreiras= GestorInformacao.getInstance().getCarreiras();

        for(Carreira c : listaCarreiras){
            carreirasListaSpinner.add(new StandartSpinner(c.getId(), c.getNumero() + " " + c.getNome()));
        }

        carreirasListaSpinner.add(new StandartSpinner(5, "Teste"));

        if(this.carreiraAtual == null){

            paragensListaSpinner.add(new StandartSpinner(-1, "Escolha a Carreira"));

        }else{
            List<Paragem> listaParagens= this.carreiraAtual.getTrajeto();

            for(Paragem p : listaParagens){
                paragensListaSpinner.add(new StandartSpinner(p.getId(), p.getNome()));
            }
        }


        Spinner spinner = (Spinner)this.findViewById(R.id.spinner_notificacao_carreira);
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, carreirasListaSpinner);
        spinner.setAdapter(spinnerArrayAdapter);

        Spinner spinner2 = (Spinner)this.findViewById(R.id.spinner_notificacao_paragem);
        ArrayAdapter spinnerArrayAdapter2 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, paragensListaSpinner);
        spinner2.setAdapter(spinnerArrayAdapter2);


        if(this.carreiraAtual != null){
            spinner.setSelection(listaCarreiras.indexOf(this.carreiraAtual));
        }

        if(this.paragemAtual != null){
            spinner2.setSelection(this.carreiraAtual.getTrajeto().indexOf(this.paragemAtual));
        }

    }

    private void editarNotificacao() {
        this.getSupportActionBar().setTitle("Editar Notificação");

        Button b = (Button) this.findViewById(R.id.confirmar_notificacao);
        final Spinner spinner = (Spinner)this.findViewById(R.id.spinner_notificacao_paragem);
        final EditText min = ((EditText) this.findViewById(R.id.minutos_notificacao));
        ((EditText)this.findViewById(R.id.minutos_notificacao)).setText(notificacao.getMinutos()+"");

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificacao.setCarreira(GestorInformacao.getInstance().findCarreiraById(1));
                StandartSpinner c = (StandartSpinner) spinner.getSelectedItem();
                notificacao.setParagem( GestorInformacao.getInstance().findParagemById(c.id));

                int minutos = Integer.parseInt(min.getText().toString());
                notificacao.setMinutos(minutos);





                finish();
            }
        });


    }

    private void newNotificacao() {
        this.getSupportActionBar().setTitle("Criar Notificação");

        Button b = (Button) this.findViewById(R.id.confirmar_notificacao);
        final Spinner spinner = (Spinner)this.findViewById(R.id.spinner_notificacao_paragem);
        final EditText min = ((EditText) this.findViewById(R.id.minutos_notificacao));



        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Carreira carreira =  (GestorInformacao.getInstance().findCarreiraById(1));
                StandartSpinner c = (StandartSpinner) spinner.getSelectedItem();
                Paragem paragem = ( GestorInformacao.getInstance().findParagemById(c.id));
                int minutos = Integer.parseInt(min.getText().toString());

                GestorNotificacao.getInstance().addNotificacao(new Notificacao(paragem,carreira,minutos));

                finish();
            }
        });


    }

}

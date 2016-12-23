package com.example.pcts.bustracker.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.pcts.bustracker.Lists.StandartSpinner;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacao);

        this.gestorNotificacao = GestorNotificacao.getInstance();
        int notificacaoId = getIntent().getIntExtra(KEY_NOTIFICACAO_INTENT, -1);

        this.notificacao = gestorNotificacao.getNotificacaoById(notificacaoId);

        if(this.notificacao == null){
            novaNotificacao();
        }else{
            editarNotificacao();
        }

        configSpinner();

    }

    private void configSpinner() {

        final Carreira carreiraAtual = this.notificacao.getCarreira();
        final Paragem paragemAtual = this.notificacao.getParagem();

        List<StandartSpinner> carreirasListaSpinner = new ArrayList<>();
        List<StandartSpinner> paragensListaSpinner = new ArrayList<>();
        List<Carreira> listaCarreiras= GestorInformacao.getInstance().getCarreiras();

        for(Carreira c : listaCarreiras){
            carreirasListaSpinner.add(new StandartSpinner(c.getId(), c.getNumero() + " " + c.getNome()));
        }

        if(carreiraAtual == null){

            for(Carreira carreira : GestorInformacao.getInstance().getCarreiras()){
                carreirasListaSpinner.add(new StandartSpinner(carreira.getId(), carreira.getNome()));
            }

        } else {

            for(Carreira carreira : GestorInformacao.getInstance().getCarreiras()){
                carreirasListaSpinner.add(new StandartSpinner(carreira.getId(), carreira.getNome()));
            }

            List<Paragem> listaParagens= carreiraAtual.getTrajeto();
            for(Paragem p : listaParagens){
                paragensListaSpinner.add(new StandartSpinner(p.getId(), p.getNome()));
            }
        }

        //Adaptação das listas aos spinners
        final Spinner carreiraSpinner = (Spinner)this.findViewById(R.id.spinner_notificacao_carreira);
        ArrayAdapter arrayAdapterCarreira = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, carreirasListaSpinner);
        carreiraSpinner.setAdapter(arrayAdapterCarreira);

        final Spinner paragemSpinner = (Spinner)this.findViewById(R.id.spinner_notificacao_paragem);
        ArrayAdapter arrayAdapterParagem = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, paragensListaSpinner);
        paragemSpinner.setAdapter(arrayAdapterParagem);

        carreiraSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                //Em caso de uma carreira diferente ser escolhida é necessário encontrar novamente o trajeto
                StandartSpinner standartSpinner = (StandartSpinner) parent.getItemAtPosition(pos);
                Carreira carreiraSelecionada = GestorInformacao.getInstance().findCarreiraById(standartSpinner.id);

                List<Paragem> listaParagens= carreiraSelecionada.getTrajeto();
                List<StandartSpinner> paragensListaSpinner = new ArrayList<>();
                for(Paragem p : listaParagens){
                    paragensListaSpinner.add(new StandartSpinner(p.getId(), p.getNome()));
                }
                ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getApplicationContext(),
                        android.R.layout.simple_spinner_item, paragensListaSpinner);
                paragemSpinner.setAdapter(spinnerArrayAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                paragemSpinner.setAdapter(null);

            }
        });


        if(carreiraAtual != null){
            carreiraSpinner.setSelection(listaCarreiras.indexOf(carreiraAtual));
        }

        if(paragemAtual != null){
            paragemSpinner.setSelection(carreiraAtual.getTrajeto().indexOf(paragemAtual));
        }

    }

    private void editarNotificacao() {

        this.getSupportActionBar().setTitle("Editar Notificação");

        Button confirmacaoButton = (Button) this.findViewById(R.id.confirmar_notificacao);
        final Spinner paragensSpinner = (Spinner)this.findViewById(R.id.spinner_notificacao_paragem);
        final Spinner carreirasSpinner = (Spinner)this.findViewById(R.id.spinner_notificacao_carreira);

        final EditText minutosEditText = ((EditText) this.findViewById(R.id.minutos_notificacao));
        minutosEditText.setText(Integer.toString(notificacao.getMinutos()));

        confirmacaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StandartSpinner carreiraSTDSpinner = (StandartSpinner) carreirasSpinner.getSelectedItem();
                Carreira carreira = GestorInformacao.getInstance().findCarreiraById(carreiraSTDSpinner.id);
                notificacao.setCarreira(carreira);

                StandartSpinner paragemSTDSpinner = (StandartSpinner) paragensSpinner.getSelectedItem();
                Paragem paragem = GestorInformacao.getInstance().findParagemById(paragemSTDSpinner.id);
                notificacao.setParagem(paragem);

                int minutos = Integer.parseInt(minutosEditText.getText().toString());
                notificacao.setMinutos(minutos);

                finish();
            }
        });

    }

    private void novaNotificacao() {
        
        this.getSupportActionBar().setTitle("Criar Notificação");

        Button confirmacaoButton = (Button) this.findViewById(R.id.confirmar_notificacao);
        final Spinner paragensSpinner = (Spinner)this.findViewById(R.id.spinner_notificacao_paragem);
        final Spinner carreirasSpinner = (Spinner)this.findViewById(R.id.spinner_notificacao_carreira);
        final EditText minutosEditText = ((EditText) this.findViewById(R.id.minutos_notificacao));

        confirmacaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StandartSpinner carreiraSTDSpinner = (StandartSpinner) carreirasSpinner.getSelectedItem();
                Carreira carreira = GestorInformacao.getInstance().findCarreiraById(carreiraSTDSpinner.id);
                notificacao.setCarreira(carreira);

                StandartSpinner paragemSTDSpinner = (StandartSpinner) paragensSpinner.getSelectedItem();
                Paragem paragem = GestorInformacao.getInstance().findParagemById(paragemSTDSpinner.id);
                notificacao.setParagem(paragem);

                int minutos = Integer.parseInt(minutosEditText.getText().toString());
                notificacao.setMinutos(minutos);

                Notificacao notificacao = new Notificacao(paragem,carreira,minutos);
                GestorNotificacao.getInstance().addNotificacao(notificacao);

                finish();
            }
        });


    }

}

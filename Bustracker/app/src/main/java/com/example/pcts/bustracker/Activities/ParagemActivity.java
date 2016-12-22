package com.example.pcts.bustracker.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.pcts.bustracker.Managers.GestorFavoritos;
import com.example.pcts.bustracker.Managers.GestorInformacao;
import com.example.pcts.bustracker.Managers.GestorNotificacao;
import com.example.pcts.bustracker.Model.Carreira;
import com.example.pcts.bustracker.Model.Paragem;
import com.example.pcts.bustracker.Model.Viagem;
import com.example.pcts.bustracker.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParagemActivity extends AppCompatActivity {

    public final static String KEY_PARAGEM_INTENT = "Paragem";

    private Paragem paragem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paragem);

        int idParagem = getIntent().getIntExtra(KEY_PARAGEM_INTENT,-1);

        this.paragem = GestorInformacao.getInstance().findParagemById(idParagem);
        this.getSupportActionBar().setTitle("Paragem " + this.paragem.getNome());
        this.atualizarListaProximaPassagens();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_paragem_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.ver_no_mapa:
                //TODO - Chamar a atividade ou ir para o fragmento
                return true;
            case R.id.criar_notificacao:
                //TODO - Chamar a atividade de criação de notificação com a paragem
                return true;
            case R.id.adicionar_favoritos:
                GestorFavoritos.getInstance().addParagem(this.paragem);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void atualizarListaProximaPassagens(){

        List<Viagem> viagens = GestorInformacao.getInstance().obterProximasPassagens(paragem);
        ListView listView = (ListView) this.findViewById(R.id.lista_proximas_carreiras);
        List<Map<String, String>> listaDeItems = new ArrayList<>();

        if(viagens.isEmpty()){
            //TODO Indicar que não existem próximas passagens
        }

        for (Viagem viagem : viagens) {
            HashMap<String, String> mapaItem = new HashMap<String, String>();
            mapaItem.put("nome_carreira", viagem.getCarreira().getNome());

            //TODO Calcular o tempo que falta para a chegada da carreira à paragem
            mapaItem.put("tempo_restante", ""); //Vazio propositadamente

            listaDeItems.add(mapaItem);
        }

        ListAdapter adapter = new SimpleAdapter(
                this.getApplicationContext(), listaDeItems,
                R.layout.proxima_carreira_item, new String[] {
                "nome_carreira", "tempo_restante"},
                new int[]{
                        R.id.nome_carreira,
                        R.id.tempo_restante
                }
        );

        listView.setAdapter(adapter);

    }


}

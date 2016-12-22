package com.example.pcts.bustracker.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.pcts.bustracker.Managers.GestorInformacao;
import com.example.pcts.bustracker.Model.Autocarro;
import com.example.pcts.bustracker.Model.Carreira;
import com.example.pcts.bustracker.Model.Viagem;
import com.example.pcts.bustracker.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarreiraActivity extends AppCompatActivity {

    public final static String KEY_CARREIRA_INTENT = "Carreira";
    private GestorInformacao gestorInformacao;
    private Carreira carreira;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carreiras);

        this.gestorInformacao = GestorInformacao.getInstance();
        int idCarreira = getIntent().getIntExtra(KEY_CARREIRA_INTENT, -1);
        this.carreira = gestorInformacao.findCarreiraById(idCarreira);
        this.getSupportActionBar().setTitle("Carreira " + this.carreira.getNome());

        this.atualizarAutocarrosEmCirculacao();
        this.atualizarProximasPartidas();

    }

    private void atualizarAutocarrosEmCirculacao() {

        List<Viagem> viagens = this.gestorInformacao.getViagens();
        ListView emCirculacaolistView = (ListView) this.findViewById(R.id.em_circulacao);
        List<Map<String, Object>> listaDeItemsEmCirculacao = new ArrayList<>();

        for (Viagem viagem: viagens) {
            //Viagem pertence a esta carreira
            if (viagem.getCarreira().getId() == this.carreira.getId()){
                HashMap<String, Object> mapaItem = new HashMap<String, Object>();
                mapaItem.put("nome_paragem", viagem.getParagemAtual().getNome());
                mapaItem.put("tempo_desde_partida", "Partiu às " + viagem.getDataPartida().getHours() + ":" + viagem.getDataPartida().getMinutes());
                mapaItem.put("bicycle_image",viagem.getAutocarro().isTemAcessoBicicleta());
                mapaItem.put("wheelchair_image",viagem.getAutocarro().isTemAcessoCadeiraDeRodas());
                listaDeItemsEmCirculacao.add(mapaItem);
            }
        }

        ListAdapter adapter = new SimpleAdapter(
                this.getApplicationContext(), listaDeItemsEmCirculacao,
                R.layout.em_circulacao_item, new String[] {
                "nome_paragem", "tempo_desde_partida"
                , "bicycle_image" , "wheelchair_image"
                },
                new int[]{
                        R.id.nome_paragem,
                        R.id.tempo_desde_partida,
                        R.id.bicycle_image,
                        R.id.wheelchair_image
                }
        );

        emCirculacaolistView.setAdapter(adapter);

    }

    //TODO calcular a diferença de tempo entre a hora atual e a hora da partida
    private String calcularTempoRestante(Date date){
        Date now = new Date();
        long time = date.getTime() - now.getTime();
        Date subtracao = new Date(time);

        int horas = subtracao.getHours();
        int minutos = subtracao.getMinutes();
        return "Daqui a " + horas + " horas e " +
                minutos + " minutos";
    }

    private void atualizarProximasPartidas() {

        List<Viagem> viagens = this.gestorInformacao.getProximasPartidas(this.carreira);
        ListView proximasPartidasView = (ListView) this.findViewById(R.id.proximas_partidas);
        List<Map<String, String>> listaDeItemsProximasPartidas = new ArrayList<>();
        proximasPartidasView.setEnabled(false);

        for (Viagem viagem: viagens) {
            //Viagem pertence a esta carreira
            if (viagem.getCarreira().getId() == this.carreira.getId()){
                HashMap<String, String> mapaItem = new HashMap<String, String>();
                mapaItem.put("hora", viagem.getDataPartida().getHours() + ":" + viagem.getDataPartida().getMinutes());
                mapaItem.put("tempo_restante", calcularTempoRestante(viagem.getDataPartida()));
                listaDeItemsProximasPartidas.add(mapaItem);
            }
        }

        /*
        //Para testes
        for(int i=0; i<4 ; i++){
            HashMap<String, String> mapaItem = new HashMap<String, String>();
            mapaItem.put("hora", "15:15");
            mapaItem.put("tempo_restante", "Daqui a 10 minutos");
            listaDeItemsProximasPartidas.add(mapaItem);

        }
        */

        ListAdapter adapter = new SimpleAdapter(
                this.getApplicationContext(), listaDeItemsProximasPartidas,
                R.layout.proxima_partida_item, new String[] {
                "hora", "tempo_restante"},
                new int[]{
                        R.id.hora,
                        R.id.tempo_restante
                }
        );

        proximasPartidasView.setAdapter(adapter);

    }

}

package com.example.pcts.bustracker.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.pcts.bustracker.Managers.GestorFavoritos;
import com.example.pcts.bustracker.Managers.GestorInformacao;
import com.example.pcts.bustracker.Managers.GestorNotificacao;
import com.example.pcts.bustracker.Model.Autocarro;
import com.example.pcts.bustracker.Model.Carreira;
import com.example.pcts.bustracker.Model.Notificacao;
import com.example.pcts.bustracker.Model.Viagem;
import com.example.pcts.bustracker.R;

import java.lang.reflect.Field;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_carreira_menu, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu){

        MenuItem itemAdicionarFavoritos = menu.findItem(R.id.adicionar_favoritos);
        MenuItem itemRemoverFavoritos = menu.findItem(R.id.remover_favoritos);

        if(GestorFavoritos.getInstance().getCarreiras().contains(this.carreira)){

            itemAdicionarFavoritos.setVisible(false);
            itemRemoverFavoritos.setVisible(true);
        } else {
            itemAdicionarFavoritos.setVisible(true);
            itemRemoverFavoritos.setVisible(false);
        }


        makeActionOverflowMenuShown();
        return true;
    }

    private void makeActionOverflowMenuShown() {
        //devices with hardware menu button (e.g. Samsung Note) don't show action overflow menu
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            Log.d("D", e.getLocalizedMessage());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.ver_no_mapa:
                Intent intentm = new Intent(this, ViagemActivity.class);
                startActivity(intentm);
                return true;


            case R.id.criar_notificacao:
                Intent intent = new Intent(this, NovaNotificacaoActivity.class);

                Toast.makeText(this, "carreira" + carreira.getId(), Toast.LENGTH_SHORT).show();


                intent.putExtra(NovaNotificacaoActivity.KEY_NOTIFICACAO_INTENT_CARREIRA, this.carreira.getId());
                startActivity(intent);
                return true;
            case R.id.adicionar_favoritos:
                boolean foiIntroduzido = GestorFavoritos.getInstance().addCarreira(this.carreira);
                if(foiIntroduzido){
                    Toast.makeText(this, "A carreira foi adicionada aos favoritos.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Não foi possível adicionar aos favoritos.", Toast.LENGTH_SHORT).show();
                }

                return true;
            case R.id.remover_favoritos:
                boolean foiRemovido = GestorFavoritos.getInstance().removeCarreira(this.carreira);
                if(foiRemovido){
                    Toast.makeText(this, "A carreira foi removida dos favoritos.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Não foi possível remover dos favoritos.", Toast.LENGTH_SHORT).show();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

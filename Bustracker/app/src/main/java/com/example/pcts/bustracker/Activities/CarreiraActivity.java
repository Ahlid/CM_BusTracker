package com.example.pcts.bustracker.Activities;

import android.content.Intent;
import android.net.Uri;
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

import com.example.pcts.bustracker.Lists.AutocarrosEmCirculacaoAdapter;
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
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem itemAdicionarFavoritos = menu.findItem(R.id.adicionar_favoritos);
        MenuItem itemRemoverFavoritos = menu.findItem(R.id.remover_favoritos);

        if (GestorFavoritos.getInstance().getCarreiras().contains(this.carreira)) {

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

            case R.id.criar_notificacao:
                Intent intent = new Intent(this, NovaNotificacaoActivity.class);

                Toast.makeText(this, "carreira" + carreira.getId(), Toast.LENGTH_SHORT).show();


                intent.putExtra(NovaNotificacaoActivity.KEY_NOTIFICACAO_INTENT_CARREIRA, this.carreira.getId());
                startActivity(intent);
                return true;
            case R.id.adicionar_favoritos:
                boolean foiIntroduzido = GestorFavoritos.getInstance().addCarreira(this.carreira);
                if (foiIntroduzido) {
                    Toast.makeText(this, "A carreira foi adicionada aos favoritos.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Não foi possível adicionar aos favoritos.", Toast.LENGTH_SHORT).show();
                }

                return true;
            case R.id.remover_favoritos:
                boolean foiRemovido = GestorFavoritos.getInstance().removeCarreira(this.carreira);
                if (foiRemovido) {
                    Toast.makeText(this, "A carreira foi removida dos favoritos.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Não foi possível remover dos favoritos.", Toast.LENGTH_SHORT).show();
                }

                return true;

            case R.id.ver_horario:
                Uri uri = Uri.parse("http://www.tsuldotejo.pt/ver_horario.php?fx=781_Ida_20160916_20301231_lineScheds.svg.png");
                Intent intent4 = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent4);
                return true;
            case R.id.ver_trajeto:
                Intent traj = new Intent(this, TrajetoCarreiraActivity.class);
                startActivity(traj);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void atualizarAutocarrosEmCirculacao() {

        List<Viagem> viagens = this.gestorInformacao.getViagens();
        ListView emCirculacaolistView = (ListView) this.findViewById(R.id.em_circulacao);
        List<Viagem> listaDeViagensEmCirculacao = new ArrayList<>();

        for (Viagem viagem : viagens) {
            //Viagem pertence a esta carreira
            if (viagem.getCarreira().getId() == this.carreira.getId()) {
                listaDeViagensEmCirculacao.add(viagem);
            }
        }
        AutocarrosEmCirculacaoAdapter adapter = new AutocarrosEmCirculacaoAdapter(this, viagens);

        emCirculacaolistView.setAdapter(adapter);

    }


    private String calcularTempoRestante(Date date) {
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

        for (Viagem viagem : viagens) {
            //Viagem pertence a esta carreira
            if (viagem.getCarreira().getId() == this.carreira.getId()) {
                HashMap<String, String> mapaItem = new HashMap<String, String>();
                mapaItem.put("hora", viagem.getDataPartida().getHours() + ":" + viagem.getDataPartida().getMinutes());
                mapaItem.put("tempo_restante", calcularTempoRestante(viagem.getDataPartida()));
                listaDeItemsProximasPartidas.add(mapaItem);
            }
        }

        ListAdapter adapter = new SimpleAdapter(
                this.getApplicationContext(), listaDeItemsProximasPartidas,
                R.layout.proxima_partida_item, new String[]{
                "hora", "tempo_restante"},
                new int[]{
                        R.id.hora,
                        R.id.tempo_restante
                }
        );

        proximasPartidasView.setAdapter(adapter);

    }

}

package com.example.pcts.bustracker.Lists;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pcts.bustracker.Activities.ViagemActivity;
import com.example.pcts.bustracker.Model.Viagem;
import com.example.pcts.bustracker.R;

import java.util.List;

/**
 * Created by Ricardo Morais on 23/12/2016.
 */

public class AutocarrosEmCirculacaoAdapter  extends BaseAdapter {

    private Context context;
    private List<Viagem> viagens;

    public AutocarrosEmCirculacaoAdapter(Context context, List<Viagem> viagens) {
        this.context = context;
        this.viagens = viagens;
    }

    @Override
    public int getCount() {
        return this.viagens.size();
    }

    @Override
    public Object getItem(int i) {
        return this.viagens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.viagens.get(i).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View v = View.inflate(context, R.layout.em_circulacao_item, null);

        final Viagem viagem = (Viagem) this.getItem(position);
        v.setTag(viagem.getId());

        TextView nomeParagem = (TextView) v.findViewById(R.id.nome_paragem);
        nomeParagem.setText(viagem.getParagemAtual().getNome());

        TextView tempoDestePartida = (TextView) v.findViewById(R.id.tempo_desde_partida);
        tempoDestePartida.setText("Partiu às " + viagem.getDataPartida().getHours() + ":" + viagem.getDataPartida().getMinutes());

        boolean temAcessoBicicleta = viagem.getAutocarro().isTemAcessoBicicleta();
        boolean temAcessoCadeiraDeRodas = viagem.getAutocarro().isTemAcessoCadeiraDeRodas();

        ImageView bicycleImage = (ImageView) v.findViewById(R.id.bicycle_image);
        ImageView wheelchairImage = (ImageView) v.findViewById(R.id.wheelchair_image);

        bicycleImage.setVisibility((temAcessoBicicleta ? View.VISIBLE : View.INVISIBLE));
        wheelchairImage.setVisibility((temAcessoCadeiraDeRodas ? View.VISIBLE : View.INVISIBLE));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViagemActivity.class);
                intent.putExtra(ViagemActivity.KEY_VIAGEM_INTENT, viagem.getId());
                context.startActivity(intent);
            }
        });

        return v;
    }
}
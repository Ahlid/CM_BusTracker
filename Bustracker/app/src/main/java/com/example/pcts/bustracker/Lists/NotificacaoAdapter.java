package com.example.pcts.bustracker.Lists;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pcts.bustracker.Model.Notificacao;
import com.example.pcts.bustracker.R;

import java.util.List;

/**
 * Created by pcts on 12/22/2016.
 */

public class NotificacaoAdapter extends BaseAdapter{



        private Context context;
        private List<Notificacao> noificacoes;

        public NotificacaoAdapter(Context context, List<Notificacao> noificacoes) {
            this.context = context;
            this.noificacoes = noificacoes;

        }

        @Override
        public int getCount() {
            return this.noificacoes.size();
        }

        @Override
        public Object getItem(int i) {
            return this.noificacoes.get(i);
        }

        @Override
        public long getItemId(int i) {
            return this.noificacoes.get(i).getId();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final View v = View.inflate(context, R.layout.list_row_notificacoes, null);

            TextView nomeParagem = (TextView) v.findViewById(R.id.notificacao_paragem);
            TextView nomeCarreira = (TextView) v.findViewById(R.id.notificacao_carreira);
            TextView minutos = (TextView) v.findViewById(R.id.notificacao_tempo);

            nomeParagem.setText(this.noificacoes.get(position).getParagem().getNome());
            nomeCarreira.setText("Carreia " + this.noificacoes.get(position).getCarreira().getNumero());
            minutos.setText(this.noificacoes.get(position).getMinutos()+" Minutos");


            v.setTag(this.noificacoes.get(position).getId());

            return v;
        }
}

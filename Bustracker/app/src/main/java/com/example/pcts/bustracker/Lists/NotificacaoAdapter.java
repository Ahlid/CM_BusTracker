package com.example.pcts.bustracker.Lists;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.pcts.bustracker.Activities.NovaNotificacaoActivity;
import com.example.pcts.bustracker.Activities.ParagemActivity;
import com.example.pcts.bustracker.Managers.GestorFavoritos;
import com.example.pcts.bustracker.Managers.GestorNotificacao;
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
        public View getView(final int position, final View convertView, ViewGroup parent) {

            final View v = View.inflate(context, R.layout.list_row_notificacoes, null);

            TextView nomeParagem = (TextView) v.findViewById(R.id.notificacao_paragem);
            TextView nomeCarreira = (TextView) v.findViewById(R.id.notificacao_carreira);
            TextView minutos = (TextView) v.findViewById(R.id.notificacao_tempo);

            nomeParagem.setText(this.noificacoes.get(position).getParagem().getNome());
            nomeCarreira.setText("Carreia " + this.noificacoes.get(position).getCarreira().getNumero());
            minutos.setText(this.noificacoes.get(position).getMinutos()+" Minutos");


            Switch switc = (Switch) v.findViewById(R.id.switch_notificacao);
            switc.setChecked(this.noificacoes.get(position).getEstado());

            final NotificacaoAdapter that = this;

            switc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Notificacao n = noificacoes.get(position);
                    if(b){
                        n.setEstado(true);

                    }else{
                        n.setEstado(false);
                    }
                    GestorNotificacao.getInstance().removeNotificacao(n);
                    GestorNotificacao.getInstance().addNotificacao(n);
                    GestorNotificacao.getInstance().order();
                    that.notifyDataSetChanged();

                }
            });

            LinearLayout l = (LinearLayout) v.findViewById(R.id.linear_notificacao);
            l.setClickable(true);
            l.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(""+noificacoes.get(position).getId());
                    builder.setMessage(GestorNotificacao.getInstance().getNotificacaoById(1).toString()) ;
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                    Intent intent = new Intent(context, NovaNotificacaoActivity.class);
                    intent.putExtra(NovaNotificacaoActivity.KEY_NOTIFICACAO_INTENT,noificacoes.get(position).getId());
                    context.startActivity(intent);
                }
            });




            v.setTag(this.noificacoes.get(position).getId());

            return v;
        }
}

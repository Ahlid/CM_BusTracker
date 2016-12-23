package com.example.pcts.bustracker.Lists;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.pcts.bustracker.Managers.GestorObserver;
import com.example.pcts.bustracker.Activities.NovaNotificacaoActivity;
import com.example.pcts.bustracker.Managers.GestorNotificacao;
import com.example.pcts.bustracker.Model.Notificacao;
import com.example.pcts.bustracker.R;

import java.util.List;

/**
 * Created by pcts on 12/22/2016.
 */

public class NotificacaoAdapter extends BaseAdapter implements GestorObserver{



    private Context context;
    private List<Notificacao> noificacoes;

    public NotificacaoAdapter(Context context, List<Notificacao> noificacoes) {
        this.context = context;
        this.noificacoes = noificacoes;

        GestorNotificacao.getInstance().addObserver(this);

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

        ImageView trash = (ImageView) v.findViewById(R.id.notificacao_apagar);

        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notificacao n = noificacoes.get(position);
                GestorNotificacao.getInstance().removeNotificacao(n);
                notifyDataSetChanged();
            }
        });

        nomeParagem.setText(this.noificacoes.get(position).getParagem().getNome());
        nomeCarreira.setText("Carreira " + this.noificacoes.get(position).getCarreira().getNumero());
        minutos.setText(this.noificacoes.get(position).getMinutos()+" Minutos");


        Switch switc = (Switch) v.findViewById(R.id.switch_notificacao);
        switc.setChecked(this.noificacoes.get(position).getEstado());
        switc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Notificacao n = noificacoes.get(position);
                if(b){
                    n.setEstado(true);

                }else{
                    n.setEstado(false);
                }
                GestorNotificacao.getInstance().updateNotificacao(n);
                //GestorNotificacao.getInstance().addNotificacao(n);
                //GestorNotificacao.getInstance().order();
                notifyDataSetChanged();

            }
        });

        LinearLayout l = (LinearLayout) v.findViewById(R.id.linear_notificacao);
        l.setClickable(true);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id = (int) noificacoes.get(position).getId();

                Intent intent = new Intent(context, NovaNotificacaoActivity.class);
                intent.putExtra(NovaNotificacaoActivity.KEY_NOTIFICACAO_INTENT,id);
                context.startActivity(intent);
            }
        });




        v.setTag(this.noificacoes.get(position).getId());

        return v;
    }



    @Override
    public void onChange(Object e) {
        notifyDataSetChanged();
    }
}
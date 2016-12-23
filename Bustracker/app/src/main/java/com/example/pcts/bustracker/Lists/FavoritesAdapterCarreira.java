package com.example.pcts.bustracker.Lists;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pcts.bustracker.Activities.CarreiraActivity;
import com.example.pcts.bustracker.Activities.NovaNotificacaoActivity;
import com.example.pcts.bustracker.Activities.ParagemActivity;
import com.example.pcts.bustracker.Activities.TrajetoCarreiraActivity;
import com.example.pcts.bustracker.Activities.ViagemActivity;
import com.example.pcts.bustracker.Model.Carreira;
import com.example.pcts.bustracker.R;

import java.util.List;

/**
 * Created by pcts on 12/22/2016.
 */

public class FavoritesAdapterCarreira extends BaseAdapter {

    private Context context;
    private List<Carreira> carreiras;

    public FavoritesAdapterCarreira(Context context, List<Carreira> carreiras) {
        this.context = context;
        this.carreiras = carreiras;
    }

    @Override
    public int getCount() {
        return this.carreiras.size();
    }

    @Override
    public Object getItem(int i) {
        return this.carreiras.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.carreiras.get(i).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View v = View.inflate(context, R.layout.list_row_favoritos_carreira, null);

        TextView nomeCarreira = (TextView) v.findViewById(R.id.nome_carreira_favoritos);
        TextView trajetoCarreira = (TextView) v.findViewById(R.id.trajeto_carreira_favoritos);

        nomeCarreira.setText("Carreira "+this.carreiras.get(position).getNumero());
        trajetoCarreira.setText(this.carreiras.get(position).getNome());

        v.setTag(this.carreiras.get(position).getId());

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CarreiraActivity.class);
                intent.putExtra(CarreiraActivity.KEY_CARREIRA_INTENT, carreiras.get(position).getId());
                context.startActivity(intent);
            }
        });


        ImageView trash = (ImageView) v.findViewById(R.id.remove_carreira_favoritos);
        trash.setClickable(true);


        final FavoritesAdapterCarreira that = this;

        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                that.carreiras.remove(carreiras.get(position));//este lista é a mesma dos favoritos
                that.notifyDataSetChanged();
            }
        });


        final ImageView overflow = (ImageView) v.findViewById(R.id.overflow_carreira_favoritos);
        overflow.setClickable(true);

        overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, overflow, Gravity.RIGHT);
                popup.getMenuInflater().inflate(R.menu.item_paragem_favoritos_menu,
                        popup.getMenu());
                popup.show();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();

                        switch (id){
                            case R.id.detalhes_favoritos :
                                Intent intent = new Intent(context, CarreiraActivity.class);
                                intent.putExtra(CarreiraActivity.KEY_CARREIRA_INTENT, carreiras.get(position).getId());
                                context.startActivity(intent);
                                break;

                            case R.id.ver_no_mapa_favoritos:
                                Intent trj = new Intent(context, ViagemActivity.class);
                                context.startActivity(trj);
                                break;

                            case R.id.criar_notificacao_favoritos:
                                Intent noti = new Intent(context, NovaNotificacaoActivity.class);
                                noti.putExtra(NovaNotificacaoActivity.KEY_NOTIFICACAO_INTENT_CARREIRA, carreiras.get(position).getId());
                                context.startActivity(noti);
                                break;


                            default:break;
                        }

                        return  true;
                    }
                });
            }
        });



        return v;
    }
}

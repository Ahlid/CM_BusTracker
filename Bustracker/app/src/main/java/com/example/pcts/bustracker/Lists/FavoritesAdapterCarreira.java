package com.example.pcts.bustracker.Lists;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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


        ImageView trash = (ImageView) v.findViewById(R.id.remove_carreira_favoritos);
        trash.setClickable(true);


        final FavoritesAdapterCarreira that = this;

        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Scan result");
                builder.setMessage("Remover "+ carreiras.get(position).toString()) ;
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


                that.carreiras.remove(carreiras.get(position));//este lista é a mesma dos favoritos
                that.notifyDataSetChanged();
            }
        });


        ImageView overflow = (ImageView) v.findViewById(R.id.overflow_carreira_favoritos);
        overflow.setClickable(true);

        overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, v, Gravity.RIGHT);
                popup.getMenuInflater().inflate(R.menu.item_paragem_favoritos_menu,
                        popup.getMenu());
                popup.show();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Scan result");
                        builder.setMessage(item.getItemId()) ;
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                        return  true;
                    }
                });
            }
        });



        return v;
    }
}
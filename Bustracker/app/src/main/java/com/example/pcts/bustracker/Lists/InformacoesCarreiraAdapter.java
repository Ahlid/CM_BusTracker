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
import com.example.pcts.bustracker.Model.Carreira;
import com.example.pcts.bustracker.R;

import java.util.List;

/**
 * Created by Ricardo Morais on 23/12/2016.
 */

public class InformacoesCarreiraAdapter extends BaseAdapter {

    private Context context;
    private List<Carreira> carreiras;

    public InformacoesCarreiraAdapter(Context context, List<Carreira> carreiras) {
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

        final View item = View.inflate(context, R.layout.informacoes_carreira_item, null);


        Carreira carreira = this.carreiras.get(position);

        TextView nomeCarreira = (TextView) item.findViewById(R.id.carreira);
        nomeCarreira.setText(carreira.getNome());
        item.setTag(carreira.getId());

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CarreiraActivity.class);
                intent.putExtra(CarreiraActivity.KEY_CARREIRA_INTENT, carreiras.get(position).getId());
                context.startActivity(intent);
            }
        });

        return item;
    }

}

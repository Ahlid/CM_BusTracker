package com.example.pcts.bustracker.Lists;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pcts.bustracker.Activities.CarreiraActivity;
import com.example.pcts.bustracker.Activities.ParagemActivity;
import com.example.pcts.bustracker.Model.Carreira;
import com.example.pcts.bustracker.Model.Paragem;
import com.example.pcts.bustracker.R;

import java.util.List;

/**
 * Created by Ricardo Morais on 23/12/2016.
 */

public class InformacoesParagemAdapter extends BaseAdapter {

    private Context context;
    private List<Paragem> paragens;

    public InformacoesParagemAdapter(Context context, List<Paragem> paragens) {
        this.context = context;
        this.paragens = paragens;
    }

    @Override
    public int getCount() {
        return this.paragens.size();
    }

    @Override
    public Object getItem(int i) {
        return this.paragens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.paragens.get(i).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View item = View.inflate(context, R.layout.informacoes_paragem_item, null);


        Paragem paragem = this.paragens.get(position);

        TextView nomeParagem = (TextView) item.findViewById(R.id.paragem);
        nomeParagem.setText(paragem.getNome());
        item.setTag(paragem.getId());

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ParagemActivity.class);
                intent.putExtra(ParagemActivity.KEY_PARAGEM_INTENT, paragens.get(position).getId());
                context.startActivity(intent);
            }
        });

        return item;
    }

}

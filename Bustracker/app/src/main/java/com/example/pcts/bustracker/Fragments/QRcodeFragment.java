package com.example.pcts.bustracker.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pcts.bustracker.Activities.ParagemActivity;
import com.example.pcts.bustracker.Fragments.Map.MainFragment;
import com.example.pcts.bustracker.Managers.GestorFavoritos;
import com.example.pcts.bustracker.Managers.GestorInformacao;
import com.example.pcts.bustracker.Model.Carreira;
import com.example.pcts.bustracker.Model.Paragem;
import com.example.pcts.bustracker.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by pcts on 11/23/2016.
 */

public class QRcodeFragment extends Fragment implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScanerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((Toolbar) getActivity().findViewById(R.id.toolbar)).setTitle("QRCODE");
        mScanerView = new ZXingScannerView(getContext());

        mScanerView.setResultHandler(this);
        mScanerView.startCamera();
        // Inflate the layout for this fragment
        return mScanerView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mScanerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        //Do anything with result here :D

        mScanerView.stopCamera();


        Paragem p = GestorInformacao.getInstance().findParagemById(Integer.parseInt(result.getText()));
        if (p == null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Scan result");
            builder.setMessage("Não existe nenhuma paragem para esse codigo.") ;
            AlertDialog alertDialog = builder.create();
            alertDialog.show();


        } else {

            Intent intent = new Intent(getContext(), ParagemActivity.class);
            intent.putExtra(ParagemActivity.KEY_PARAGEM_INTENT, p.getId());
            getContext().startActivity(intent);

        }

        Fragment fragment = new MainFragment();

        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

    }

}

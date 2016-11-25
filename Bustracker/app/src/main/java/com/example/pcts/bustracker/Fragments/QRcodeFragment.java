package com.example.pcts.bustracker.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pcts.bustracker.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by pcts on 11/23/2016.
 */

public class QRcodeFragment extends Fragment implements ZXingScannerView.ResultHandler{
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
    public void onDestroyView(){
        super.onDestroyView();
        mScanerView.stopCamera();
    }
    @Override
    public void handleResult(Result result) {
        //Do anything with result here :D

        mScanerView.stopCamera();
        Log.w("handleResult", result.getText());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Scan result");
        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        //Resume scanning
        //mScannerView.resumeCameraPreview(this);
    }
}

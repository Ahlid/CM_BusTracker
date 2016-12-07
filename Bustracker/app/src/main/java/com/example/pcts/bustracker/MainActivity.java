package com.example.pcts.bustracker;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.pcts.bustracker.Fragments.FavoritesFragment;
import com.example.pcts.bustracker.Fragments.InfoFragment;
import com.example.pcts.bustracker.Fragments.Map.MainFragment;
import com.example.pcts.bustracker.Fragments.NotificationsFragment;
import com.example.pcts.bustracker.Fragments.QRcodeFragment;
import com.example.pcts.bustracker.Managers.GestorFavoritos;
import com.example.pcts.bustracker.Managers.GestorInformacao;
import com.example.pcts.bustracker.Managers.GestorNotificacao;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener,ZXingScannerView.ResultHandler {

    private NavigationView navigationView = null;
    private Toolbar toolbar = null;
    private ZXingScannerView mScanerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
        GestorInformacao.getInstance();
        GestorFavoritos.getInstance(this);
        GestorNotificacao.getInstance(this);


        setContentView(R.layout.activity_main);

        MainFragment fragment = new MainFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        //How to change elements in the header programatically


        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_map) {
            //Set the fragment initially
             fragment = new MainFragment();
            // Handle the camera action
        } else if (id == R.id.nav_info) {
            //Set the fragment initially
             fragment = new InfoFragment();
        } else if (id == R.id.nav_qrcode) {
            fragment = new QRcodeFragment();
        } else if (id == R.id.nav_favorites) {
            fragment = new FavoritesFragment();
        } else if (id == R.id.nav_notifications) {
            fragment = new NotificationsFragment();
        }


        if(fragment != null){
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mScanerView != null)
        mScanerView.stopCamera();
    }
    @Override
    public void handleResult(Result result) {
        Log.w("handleResult", result.getText());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan result");
        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}
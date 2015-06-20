package com.test.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by alejandro on 19/06/2015.
 */
public class Internet {

    public Activity activity;

    public Internet(Activity activity){
        this.activity = activity;
    }


    // Retorna si hay conexion a la red o no.
    public boolean isConnectionAvailable() {
        // Se obtiene del gestor de conectividad la informacion de red.
        ConnectivityManager gestorConectividad = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infoRed = gestorConectividad.getActiveNetworkInfo();
        // Se retorna si hay conexión.
        return (infoRed != null && infoRed.isConnected());
    }

}
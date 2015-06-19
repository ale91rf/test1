package com.test.volley;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by alejandro on 19/06/2015.
 */

//Al extender la clase Application la clase RequestQueue sera instanciada automaticamente y una unica vez
    //utilizando asi el patron de diseño SINGLETON
public class App extends Application {

    private static RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        // Se crea la cola de peticiones de Volley.
        mRequestQueue = Volley.newRequestQueue(this);
    }

    // Retorna la cola de peticiones de Volley.
    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue no inicializada");
        }
    }

}

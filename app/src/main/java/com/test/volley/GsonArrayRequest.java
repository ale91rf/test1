package com.test.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by alejandro on 19/06/2015.
 */
public class GsonArrayRequest<T> extends Request<T> {

    private final Response.Listener<T> listener;
    private final Gson gson;
    private final Type type;

    public GsonArrayRequest(int method, String url, Type type,
                            Response.Listener<T> listener, Response.ErrorListener errorListener, Gson gson) {
        super(method, url, errorListener);
        this.type = type;
        this.listener = listener;
        this.gson = gson;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            // Se obtiene la cadena JSON a partir de la respuesta (con el
            // charset adecuado).
            String json = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            // Se procesa la cadena JSON.
            T datos = gson.fromJson(json, type);
            // Se crea y retorna la respuesta.
            return Response.success(datos,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    // Env�a la respuesta al listener
    @Override
    protected void deliverResponse(T response) {
        // Se llama al metodo onResponse del listener pasandole la respuesta.
        listener.onResponse(response);
    }

}
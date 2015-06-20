package com.test.volley;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

// Clase que gestiona la cache de imagenes en memoria.
public class BitmapMemCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    // Constructores.
    public BitmapMemCache() {
        // Se llama al otro constructor con el tamanno en KB, calculado como el
        // maximo posible para la JVM.
        this((int) (Runtime.getRuntime().maxMemory() / 1024) / 8);
    }

    public BitmapMemCache(int sizeInKiloBytes) {
        super(sizeInKiloBytes);
    }

    // Retorna el tamanno en KB de una imagen de la cache.
    // Recibe la key del elemento y la imagen.
    @Override
    protected int sizeOf(String key, Bitmap bitmap) {
        return bitmap.getByteCount() / 1024;
    }

    // Retorna si existe en la cache una imagen con esa clave.
    public boolean contains(String key) {
        return get(key) != null;
    }

    // Retorna la imagen correspondiente a una clave.
    public Bitmap getBitmap(String key) {
        return get(key);
    }

    // Escribe una imagen en la cache.
    // Utiliza la url como clave.
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }

}
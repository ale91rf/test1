package com.test.bd;

import android.provider.BaseColumns;

/**
 * Created by alejandro on 24/06/2015.
 */
public class test1BD {

    private test1BD(){};  //Privado para que no pueda instanciarse esta clase

    //aqui annadimos las constantes generales de la BD.
    public static final String BD_NOMBRE = "test1";
    public static final int BD_VERSION = 1;

    //Tabla Company
    public static abstract class Company implements BaseColumns{
        public static final String TABLE = "company";
        public static final String NAME = "name";
        public static final String IMAGEURL = "imageUrl";
        public static final String LONGITUDE = "longitude";
        public static final String LATITUDE = "latitude";
        public static final String ADDRESS = "address";
        public static final String DATE = "date";
        public static final String EMAIL = "email";

        public static final String[] ALL = new String[]{
            _ID, NAME,IMAGEURL, LONGITUDE, LATITUDE, ADDRESS, DATE, EMAIL
        };

    }
}

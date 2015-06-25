package com.test.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alejandro on 24/06/2015.
 */
public class Helper extends SQLiteOpenHelper{

    public Helper(Context context) {
        // Se llama al constructor del padre, que es quien realmente crea o
        // actualiza la versión de BD si es necesario.
        super(context, test1BD.BD_NOMBRE, null, test1BD.BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //ejecuto la sentencia SQL de creacion de la tabla
        db.execSQL(TABLE_COMPANY_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static final String TABLE_COMPANY_CREATE = "create table " +
            test1BD.Company.TABLE + "(" + test1BD.Company._ID + " long, " +
            test1BD.Company.NAME + " text, " +
            test1BD.Company.IMAGEURL + " text, " + test1BD.Company.LONGITUDE + " float, " +
            test1BD.Company.LATITUDE + " float, " +
            test1BD.Company.ADDRESS + " text, " + test1BD.Company.DATE + " text, " +
            test1BD.Company.EMAIL + " text)";


}

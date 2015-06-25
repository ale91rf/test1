package com.test.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.test.model.Company;

import java.util.ArrayList;

/**
 * Created by alejandro on 24/06/2015.
 */
public class DAO {
    private Context mContext;
    private Helper mHelper;
    private SQLiteDatabase mBd;

    public DAO(Context context)	{
        this.mContext = context;
        mHelper = new Helper(mContext);
    }

    public DAO open() throws SQLException {
        mBd = mHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mHelper.close();
    }

    private Cursor queryAllCompanies(){
        return mBd.query(test1BD.Company.TABLE, test1BD.Company.ALL, null, null, null, null, null);
    }

    public ArrayList<Company> getAllCompanies(){
        ArrayList<Company> list = new ArrayList<Company>();
        
        Cursor cursor = this.queryAllCompanies();
        
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Company company = cursorToCompany(cursor);
            list.add(company);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    private Company cursorToCompany(Cursor cursor) {
        Company company = new Company();

        Long l = cursor.getLong(0);

        company.setId(l.intValue());

        company.setName(cursor.getString(1));
        company.setUrl(cursor.getString(2));
        company.setLongitude(cursor.getFloat(3));

        company.setLatitude(cursor.getFloat(4));
        company.setAddress(cursor.getString(5));
        company.setDate(cursor.getString(6));
        company.setEmail(cursor.getString(7));

        return company;
    }

    public long insertCompany(Company company){
        ContentValues values = new ContentValues();
        Integer i = company.getId();
        values.put(test1BD.Company._ID, i.longValue());
        values.put(test1BD.Company.NAME, company.getName());
        values.put(test1BD.Company.IMAGEURL, company.getUrl());
        values.put(test1BD.Company.LONGITUDE, company.getLongitude());
        values.put(test1BD.Company.LATITUDE, company.getLatitude());
        values.put(test1BD.Company.ADDRESS, company.getAddress());
        values.put(test1BD.Company.DATE, company.getDate());
        values.put(test1BD.Company.EMAIL, company.getEmail());

        return mBd.insert(test1BD.Company.TABLE, null, values);
    }

    public boolean deleteCompany(int id){
        return mBd.delete(test1BD.Company.TABLE, test1BD.Company._ID + " = " + id, null) > 0;
    }

}

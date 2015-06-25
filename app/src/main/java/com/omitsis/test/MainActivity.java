package com.omitsis.test;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.bd.DAO;
import com.test.model.Company;
import com.test.utils.CompaniesAdapter;
import com.test.utils.Constants;
import com.test.utils.Internet;
import com.test.volley.App;
import com.test.volley.GsonArrayRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener{

    private RequestQueue mRequestQueue;
    private ListView mListView;
    private Internet mConnection;
    private ProgressBar mPgBar;
    private Button mBtnTry;
    private ArrayList<Company> companies;
    private CompaniesAdapter mAdapter;
    private Bundle mBundle;
    private Intent mIntent;
    private DAO mDao;
    private ArrayList<Company> companiesBD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    @Override
    protected void onStart() {
        super.onStart();

        mRequestQueue = App.getRequestQueue();
        mConnection = new Internet(this);
        getView();

        if(mConnection != null & mConnection.isConnectionAvailable()){
            sendRequest();
        }else{
            showToast(getString(R.string.cannot));
            mPgBar.setVisibility(View.INVISIBLE);
            mBtnTry.setVisibility(View.VISIBLE);
            mBtnTry.setOnClickListener(this);

        }
    }

    private void getView() {
        mListView = (ListView) findViewById(R.id.company_list);
        mPgBar = (ProgressBar) findViewById(R.id.pg_Bar);
        mBtnTry = (Button) findViewById(R.id.btn_try);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mRequestQueue != null){
            //cancelamos peticiones pendientes si las hubiera
            mRequestQueue.cancelAll(Constants.TAG);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_try:
                tryAgain();
                break;
        }



    }

    private void tryAgain() {
        mBtnTry.setVisibility(View.GONE);
        mPgBar.setVisibility(View.VISIBLE);


        if(mConnection != null & mConnection.isConnectionAvailable()){
            sendRequest();
        }else{
            showToast(getString(R.string.cannot));

            mPgBar.setVisibility(View.INVISIBLE);
            mBtnTry.setVisibility(View.VISIBLE);

        }
    }


    private void sendRequest() {
        Response.Listener<ArrayList<Company>> listener = new Response.Listener<ArrayList<Company>>() {

            @Override
            public void onResponse(ArrayList<Company> companies) {
                paintCompanies(companies);
            }

        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast(getString(R.string.error_download));
                mPgBar.setVisibility(View.INVISIBLE);
                mBtnTry.setVisibility(View.VISIBLE);
                mBtnTry.setOnClickListener(MainActivity.this);
            }
        };

        // Se crea la peticion.
        Gson gson = new Gson();
        Type tipo = new TypeToken<List<Company>>() {
        }.getType();



        GsonArrayRequest<ArrayList<Company>> request = new GsonArrayRequest<ArrayList<Company>>(
                Request.Method.GET, Constants.URL, tipo, listener, errorListener,
                gson);

        request.setTag(Constants.TAG);
        // Se annade la peticion a la cola de Volley.
        mRequestQueue.add(request);


    }


    private void paintCompanies(ArrayList<Company> response){
        mPgBar.setVisibility(View.INVISIBLE);

        companies = response;

        if(companies != null){

            mDao = new DAO(getApplicationContext()).open();
            companiesBD = mDao.getAllCompanies();
            mDao.close();

            //si hay companias guardadas en favoritas, ponemos a true el campo fav de tal compania del
            //ArrayList de companias descargadas
            if (companiesBD != null)    {
                for (Company companyBD: companiesBD){
                    for (Company company: companies){
                        if(company.getId() == companyBD.getId()){
                            company.setFav(true);
                        }
                    }
                }
            }


            mAdapter = new CompaniesAdapter(getApplicationContext(), companies);

            mListView.setAdapter(mAdapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    mBundle = new Bundle();
                    mBundle.putString(Constants.TAG_NAME, companies.get(position).getName());
                    mBundle.putString(Constants.TAG_IMG_URL, companies.get(position).getUrl());
                    mBundle.putFloat(Constants.TAG_LONGITUDE, companies.get(position).getLongitude());
                    mBundle.putInt(Constants.TAG_ID, companies.get(position).getId());
                    mBundle.putFloat(Constants.TAG_LATITUDE, companies.get(position).getLatitude());
                    mBundle.putString(Constants.TAG_ADDRESS, companies.get(position).getAddress());
                    mBundle.putString(Constants.TAG_DATE, companies.get(position).getDate());
                    mBundle.putString(Constants.TAG_EMAIL, companies.get(position).getEmail());
                    mBundle.putBoolean(Constants.TAG_FAV, companies.get(position).getFav());

                    mIntent = new Intent(getApplicationContext(), DetailActivity.class);
                    mIntent.putExtras(mBundle);
                    startActivity(mIntent);

                }
            });
        }


    }



}

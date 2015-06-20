package com.omitsis.test;

import android.app.Activity;
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
import com.test.model.Company;
import com.test.utils.CompaniesAdapter;
import com.test.utils.Internet;
import com.test.volley.App;
import com.test.volley.GsonArrayRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener{

    private RequestQueue mRequestQueue;
    private ListView mListView;
    private static final String TAG = "requests";
    private static final String URL = "http://www.json-generator.com/api/json/get/caZrciQZRu?indent=2";
    private Internet connection;
    private ProgressBar mPgBar;
    private Button mBtnTry;
    private ArrayList<Company> companies;
    private CompaniesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequestQueue = App.getRequestQueue();
        connection = new Internet(this);
        getView();

        if(connection != null & connection.isConnectionAvailable()){
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
            mRequestQueue.cancelAll(TAG);
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


        if(connection != null & connection.isConnectionAvailable()){
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
            }
        };

        // Se crea la peticion.
        Gson gson = new Gson();
        Type tipo = new TypeToken<List<Company>>() {
        }.getType();



        GsonArrayRequest<ArrayList<Company>> request = new GsonArrayRequest<ArrayList<Company>>(
                Request.Method.GET, URL, tipo, listener, errorListener,
                gson);

        request.setTag(TAG);
        // Se annade la peticion a la cola de Volley.
        mRequestQueue.add(request);


    }


    private void paintCompanies(ArrayList<Company> response){
        mPgBar.setVisibility(View.INVISIBLE);

        companies = response;

        if(companies != null){
            adapter = new CompaniesAdapter(getApplicationContext(), companies);

            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    showToast("pulsada la cia: " + companies.get(position).getName());
                }
            });
        }


    }



}

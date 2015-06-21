package com.omitsis.test;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.test.model.Company;
import com.test.utils.Constants;
import com.test.utils.Internet;
import com.test.volley.App;
import com.test.volley.BitmapMemCache;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends Activity implements View.OnClickListener{

    private Bundle mBundle;
    private Company company;
    private TextView lblName;
    private TextView lblAddress;
    private TextView lblDate;
    private ProgressBar pgBar;
    private NetworkImageView imgCompany;
    private TextView lblId;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private Internet mConnection;
    private Button btnMap;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);


        mBundle = getIntent().getExtras();

        if(mBundle != null){
            company = new Company(mBundle.getString(Constants.TAG_NAME), mBundle.getString(Constants.TAG_IMG_URL),
                    mBundle.getFloat(Constants.TAG_LONGITUDE), mBundle.getInt(Constants.TAG_ID),
                    mBundle.getFloat(Constants.TAG_LATITUDE), mBundle.getString(Constants.TAG_ADDRESS),
                    mBundle.getString(Constants.TAG_DATE), mBundle.getString(Constants.TAG_EMAIL));
        }


        getView();

        mConnection = new Internet(this);

        if(mConnection.isConnectionAvailable()){
            new RedirectedUrlTask().execute(company.getUrl());
        }


    }

    private void getView() {

        pgBar = (ProgressBar) findViewById(R.id.pg_bar_img);
        imgCompany = (NetworkImageView) findViewById(R.id.img_company);

        lblName = (TextView) findViewById(R.id.lbl_name);
        lblName.setText(company.getName());

        lblId = (TextView) findViewById(R.id.lbl_id_company);
        lblId.setText(String.valueOf(company.getId()));

        lblAddress = (TextView) findViewById(R.id.lbl_address);
        lblAddress.setText(company.getAddress());

        lblDate = (TextView) findViewById(R.id.lbl_date);
        lblDate.setText(showDate(company.getDate()));

        btnMap = (Button) findViewById(R.id.btn_map);
        btnMap.setOnClickListener(this);


    }

    private void paintImage(String newUrl) {

        pgBar.setVisibility(View.INVISIBLE);
        mRequestQueue = App.getRequestQueue();

        mImageLoader =  new ImageLoader(mRequestQueue, new BitmapMemCache(Constants.MAX_MEMORY_CACHE_SIZE_KB));
        mImageLoader.get(newUrl, ImageLoader.getImageListener(imgCompany, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
        imgCompany.setImageUrl(newUrl, mImageLoader);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_map:
                showMap();
                break;
        }
    }

    private void showMap() {

        if(company != null){

            mIntent = new Intent(Intent.ACTION_VIEW);


            //le pasamos la longitud y latitud que venia con el json
            mIntent.setData(Uri.parse("geo:"+String.valueOf(company.getLatitude())+","+String.valueOf(company.getLongitude())));

            if (mIntent.resolveActivity(getPackageManager()) != null){
                startActivity(mIntent);
            }



        }



    }


    //Tarea en segundo plano encargada de obtener la ruta una vez redireccionada ya.
    public class RedirectedUrlTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... url) {
            String newUrl = url[0];


            URLConnection con = null;
            try {
                con = new URL(url[0].toString()).openConnection();
                System.out.println("Orignal URL: " + con.getURL());
                con.connect();
                System.out.println("Connected URL: " + con.getURL());
                InputStream is = con.getInputStream();
                System.out.println("Redirected URL: " + con.getURL());
                newUrl = con.getURL().toString();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return newUrl;
        }

        @Override
        protected void onPostExecute(String result) {

            paintImage(result);

        }
    }


    private String showDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date str = null ;

        try {
            str = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        return str.toString();
    }
}

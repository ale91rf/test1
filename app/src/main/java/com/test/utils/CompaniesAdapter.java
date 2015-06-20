package com.test.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.omitsis.test.R;
import com.test.model.Company;
import com.test.volley.App;
import com.test.volley.BitmapMemCache;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alejandro on 19/06/2015.
 */
public class CompaniesAdapter extends ArrayAdapter<Company> {

    public static final int MAX_MEMORY_CACHE_SIZE_KB = 25 * 1024;


    private final SimpleDateFormat formatter;
    private ArrayList<Company> companies;
    private final LayoutInflater inflater;
    private ImageLoader mImageLoader;

    public CompaniesAdapter(Context context, ArrayList<Company> companies) {
        super(context, R.layout.activity_main_item, companies);

        this.companies = companies;
        inflater = LayoutInflater.from(context);
        formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.activity_main_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }


        onBindViewHolder(holder, position);

        return convertView;
    }

    private void onBindViewHolder(ViewHolder holder, int position) {
        Company company = companies.get(position);

        holder.lblName.setText(company.getName());
        holder.lblEmail.setText(company.getEmail());
        holder.lblAddress.setText(company.getAddress());
        holder.lblDate.setText(showDate(company.getDate()));

        RequestQueue mRequestQueue = App.getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue, new BitmapMemCache(MAX_MEMORY_CACHE_SIZE_KB));
        mImageLoader.get(company.getUrl(),
                ImageLoader.getImageListener(holder.imgCompany, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
        holder.imgCompany.setImageUrl(company.getUrl(), mImageLoader);

    }



    public class ViewHolder {

        private final NetworkImageView imgCompany;
        private final TextView lblName;
        private final TextView lblEmail;
        private final TextView lblAddress;
        private final TextView lblDate;

        public ViewHolder(View itemView){
            imgCompany = (NetworkImageView) itemView.findViewById(R.id.img_company);
            lblName = (TextView) itemView.findViewById(R.id.lbl_name);
            lblEmail = (TextView) itemView.findViewById(R.id.lbl_email);
            lblAddress = (TextView) itemView.findViewById(R.id.lbl_address);
            lblDate = (TextView) itemView.findViewById(R.id.lbl_date);

        }
    }

    private String showDate(String date) {
        Date str = null ;

        try {
            str = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        return str.toString();
    }

}

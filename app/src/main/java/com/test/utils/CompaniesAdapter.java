package com.test.utils;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.omitsis.test.R;
import com.test.model.Company;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alejandro on 19/06/2015.
 */
public class CompaniesAdapter extends ArrayAdapter<Company> {

    private ArrayList<Company> companies;

    public CompaniesAdapter(Context context, ArrayList<Company> companies) {
        super(context, R.layout.activity_main_item, companies);

        this.companies = companies;
    }
}

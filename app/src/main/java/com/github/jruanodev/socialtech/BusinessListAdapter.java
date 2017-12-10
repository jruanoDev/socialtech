package com.github.jruanodev.socialtech;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.jruanodev.socialtech.dao.Business;
import com.github.jruanodev.socialtech.dao.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

public class BusinessListAdapter extends BaseAdapter {
    List<Business> businessList = new ArrayList<>();
    List<Business> filteredBusinessList = new ArrayList<>();
    TreeSet indexSet = new TreeSet();
    Context context;

    private static final int TYPE_INDEXED = 1;
    private static final int TYPE_UNINDEXED = 0;
    private static final int MAX_TYPE_COUNT = TYPE_INDEXED + 1;

    private TextView businessIndexLetter;
    private TextView businessName;
    private TextView businessEmail;

    private String uiIndex = "";

    public BusinessListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return businessList.size();
    }

    @Override
    public Business getItem(int position) {
        return businessList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return indexSet.contains(position) ? TYPE_INDEXED : TYPE_UNINDEXED;
    }

    public void addUnindexedRow(Business business) {
        filteredBusinessList.add(business);
        businessList.add(business);
        notifyDataSetChanged();
    }

    public void addIndexedRow(Business business, String index) {
        businessList.add(business);
        filteredBusinessList.add(business);

        uiIndex = index;
        indexSet.add(businessList.size() - 1);
        notifyDataSetChanged();
    }

    public static int getMaxTypeCount() {
        return MAX_TYPE_COUNT;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int type = getItemViewType(i);

        if(view == null) {
            switch (type) {
                case TYPE_INDEXED:
                    view = LayoutInflater.from(context).inflate(R.layout.businesslist_adapter_indexed, null);
                    businessIndexLetter = view.findViewById(R.id.indexLetterBusiness);
                    businessName = view.findViewById(R.id.businessNameIndexed);
                    businessEmail = view.findViewById(R.id.businessEmailIndexed);
                    String letra = "" + getItem(i).getName().charAt(0);
                    businessIndexLetter.setText(letra.toUpperCase(Locale.ENGLISH));
                    break;

                case TYPE_UNINDEXED:
                    view = LayoutInflater.from(context).inflate(R.layout.businesslist_adapter_unindexed, null);
                    businessName = view.findViewById(R.id.businessNameUnindexed);
                    businessEmail = view.findViewById(R.id.businessEmailUnindexed);
                    break;
            }

            Log.v("EMPRESA EN LISTA", "" + getItem(i).toString());

            businessName.setText(getItem(i).getName());
            businessEmail.setText(getItem(i).getEmail());
        }

        return view;
    }
}

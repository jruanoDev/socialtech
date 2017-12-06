package com.github.jruanodev.socialtech;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.jruanodev.socialtech.dao.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

public class ContactListAdapter extends BaseAdapter {
    List<Contact> contactList = new ArrayList<>();
    TreeSet indexSet = new TreeSet();
    Context context;

    private static final int TYPE_INDEXED = 1;
    private static final int TYPE_UNINDEXED = 0;
    private static final int MAX_TYPE_COUNT = TYPE_INDEXED + 1;

    private TextView indexLetter;
    private TextView contactName;
    private TextView contactEmail;

    private String uiIndex = "";

    public ContactListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Contact getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        Log.v("POSITION", "LA POSITION ES " + position);
        return indexSet.contains(position) ? TYPE_INDEXED : TYPE_UNINDEXED;
    }

    public void addUnindexedRow(Contact contact) {
        contactList.add(contact);
        notifyDataSetChanged();
    }

    public void addIndexedRow(Contact contact, String index) {
        contactList.add(contact);

        uiIndex = index;
        indexSet.add(contactList.size() - 1);
        Log.v("SIZE", "EL SIZE DEL TREESET ES " + contactList.size());
        notifyDataSetChanged();
    }

    public static int getMaxTypeCount() {
        return MAX_TYPE_COUNT;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int type = getItemViewType(i);
        Log.v("TIPO", "" + type);

        if(view == null) {
            switch (type) {
                case TYPE_INDEXED:
                    view = LayoutInflater.from(context).inflate(R.layout.contactlist_adapter_indexed, null);
                    indexLetter = view.findViewById(R.id.indexLetter);
                    contactName = view.findViewById(R.id.contactNameIndexed);
                    contactEmail = view.findViewById(R.id.contactEmailIndexed);
                    String letra = "" + getItem(i).getName().charAt(0);
                    indexLetter.setText("" + letra.toUpperCase(Locale.ENGLISH));
                    break;

                case TYPE_UNINDEXED:
                    view = LayoutInflater.from(context).inflate(R.layout.contactlist_adapter_unindexed, null);
                    contactName = view.findViewById(R.id.contactNameUnindexed);
                    contactEmail = view.findViewById(R.id.contactEmailUnindexed);
                    break;
            }

            contactName.setText(getItem(i).getName());
            contactEmail.setText(getItem(i).getEmail());
        }


        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}

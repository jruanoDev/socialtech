package com.github.jruanodev.socialtech;

import android.content.Context;
import android.icu.text.AlphabeticIndex;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.jruanodev.socialtech.dao.Contact;

import java.util.List;
import java.util.Locale;

public class ContactListAdapter extends BaseAdapter {
    List<Contact> contactList;
    Context context;

    String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private TextView indexLetter;
    private TextView contactName;


    public ContactListAdapter(Context context, List<Contact> contactList) {
        this.contactList = contactList;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.contactlist_adapter, viewGroup, false);

            indexLetter = view.findViewById(R.id.indexLetter);
            contactName = view.findViewById(R.id.contactName);

            contactName.setText(getItem(i).getName());

        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}

package com.github.jruanodev.socialtech;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.github.jruanodev.socialtech.dao.Contact;

import java.util.List;

import butterknife.ButterKnife;

public class ContactListAdapter extends ArrayAdapter<Contact> {

    public ContactListAdapter(@NonNull Context context, List<Contact> contactList) {
        super(context, 0, contactList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contactlist_adapter,
                    parent, false);

        ButterKnife.bind(convertView);



        return convertView;
    }
}

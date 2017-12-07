package com.github.jruanodev.socialtech;

import android.app.SearchManager;
import android.content.Context;
import android.icu.text.AlphabeticIndex;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.github.jruanodev.socialtech.dao.Contact;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactListFragment extends Fragment {
    View inflatedView;
    List<Contact> contactList = new ArrayList<>();
    ContactListAdapter cAdapter;

    @BindView(R.id.contactListToolbar) Toolbar toolbar;
    @BindView(R.id.contactListFab) FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_contactlist, container, false);

        ButterKnife.bind(this, inflatedView);

        Bundle fragmentArguments = this.getArguments();
        contactList = fragmentArguments.getParcelableArrayList("contactList");

        if(contactList != null) {
            sortContactList();
            populateListView();
        }

        toolbar.setTitle("Contactos");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.menu_icon);
        toolbar.inflateMenu(R.menu.contactlist_menu);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
                ft.replace(R.id.fragmentContainer, new FormFragment()).commit();
            }
        });

        return inflatedView;
    }

    public void sortContactList() {
        contactList.sort(new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
               return o1.getName().compareTo(o2.getName());
            }
        });
    }

    public void populateListView() {
        cAdapter = new ContactListAdapter(getContext());
        ListView listView = inflatedView.findViewById(R.id.contactList);

        AlphabeticIndex<String> index = new AlphabeticIndex<String>(Locale.ENGLISH);

        for(Contact contact : contactList) {
            index.addRecord(contact.getName(), String.valueOf(contactList.indexOf(contact)));
        }

        for(AlphabeticIndex.Bucket<String> bucket : index) {

            String bucketLabel = bucket.getLabel();

            if(bucket.size() != 0) {
                boolean indexed = false;
                for(AlphabeticIndex.Record<String> record : bucket) {
                    if(!indexed) {
                        cAdapter.addIndexedRow(contactList.get(Integer.parseInt(record.getData())), bucketLabel);
                        indexed = true;
                    } else {
                        cAdapter.addUnindexedRow(contactList.get(Integer.parseInt(record.getData())));
                    }
                }
            }
        }

        listView.setAdapter(cAdapter);
    }
}

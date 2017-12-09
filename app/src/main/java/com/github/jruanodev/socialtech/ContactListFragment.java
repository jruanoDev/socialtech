package com.github.jruanodev.socialtech;

import android.content.Context;
import android.content.Intent;
import android.icu.text.AlphabeticIndex;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
    DrawerLayout drawerLayout;

    @BindView(R.id.contactListFab) FloatingActionButton fab;
    @BindView(R.id.contactList) ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        inflatedView = inflater.inflate(R.layout.fragment_contactlist, container, false);
        ButterKnife.bind(this, inflatedView);

        Toolbar toolbar = getActivity().findViewById(R.id.aux_toolbar);
        toolbar.setTitle("Contactos");
        toolbar.setNavigationIcon(R.drawable.menu_icon);

        drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        Bundle fragmentArguments = this.getArguments();
        contactList = fragmentArguments.getParcelableArrayList("contactList");

        if(contactList != null) {
            sortContactList();
            populateListView();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);

                FormFragment formFragment = new FormFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("contactList", (ArrayList<? extends Parcelable>) contactList);
                formFragment.setArguments(bundle);
                ft.replace(R.id.fragmentContainer, formFragment).commit();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ContactDetailActivity.class);

                Bundle bundle = new Bundle();
                bundle.putParcelable("selectedContact", contactList.get(position));

                intent.putExtras(bundle);
                startActivity(intent);
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

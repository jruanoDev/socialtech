package com.github.jruanodev.socialtech;

import android.content.Context;
import android.icu.text.AlphabeticIndex;
import android.os.Bundle;
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

    @BindView(R.id.contactListToolbar) Toolbar toolbar;
    @BindView(R.id.contactListFab) FloatingActionButton fab;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_contactlist, container, false);

        ButterKnife.bind(this, inflatedView);

        if(drawerLayout == null)
            Log.v("ES NULO", "ES NULO");

        if(navigationView == null)
            Log.v("ES NULO NAVVIEW", "ES NULO EL NAVVIEW");

        Bundle fragmentArguments = this.getArguments();
        contactList = fragmentArguments.getParcelableArrayList("contactList");

        if(contactList != null) {
            sortContactList();
            populateListView();
        }

        toolbar.setTitle("Contactos");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.menu_icon);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
                ft.replace(R.id.fragmentContainer, new FormFragment()).commit();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //drawerLayout.openDrawer(GravityCompat.START);
                Toast.makeText(getContext(), "PULSADO MENU", Toast.LENGTH_SHORT).show();
            }
        });

        return inflatedView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.contact_view:
                        Toast.makeText(getContext(), "PULSADO CONTACTOS", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.business_view:
                        Toast.makeText(getContext(), "PULSADO EMPRESAS", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.log_out:
                        Toast.makeText(getContext(), "PULSADO DESCONECTARSE", Toast.LENGTH_SHORT).show();
                        break;
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });
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

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    public void setNavigationView(NavigationView navigationView) {
        this.navigationView = navigationView;
    }
}

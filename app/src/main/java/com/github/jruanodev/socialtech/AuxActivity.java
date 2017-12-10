package com.github.jruanodev.socialtech;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.jruanodev.socialtech.dao.Business;
import com.github.jruanodev.socialtech.dao.Contact;

import java.util.ArrayList;
import java.util.List;

public class AuxActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    List<Business> businessList = new ArrayList<>();
    List<Contact> contactList = new ArrayList<>();
    DrawerLayout drawerLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aux);

        Toolbar toolbar = findViewById(R.id.aux_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setNavigationIcon(R.drawable.menu_icon);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.drawer_navview);
        navigationView.setCheckedItem(R.id.contact_view);
        navigationView.setNavigationItemSelectedListener(this);

        contactList = getIntent().getParcelableArrayListExtra("contactList");
        businessList = getIntent().getParcelableArrayListExtra("businessList");

        ContactListFragment f1 = new ContactListFragment();

        Bundle fragmentArguments = new Bundle();
        fragmentArguments.putParcelableArrayList("contactList", (ArrayList<? extends Parcelable>) contactList);

        f1.setArguments(fragmentArguments);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.addToBackStack("contactListFragment");
        ft.replace(R.id.fragmentContainer, f1).commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contact_view:
                Toast.makeText(getBaseContext(), "TOCADO CONTACTOS", Toast.LENGTH_SHORT).show();
                break;

            case R.id.business_view:
                BusinessListFragment b = new BusinessListFragment();
                Bundle bArguments = new Bundle();
                bArguments.putParcelableArrayList("businessList", (ArrayList<? extends Parcelable>) businessList);

                b.setArguments(bArguments);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.addToBackStack("businessListFragment");
                ft.replace(R.id.fragmentContainer, b).commit();

                drawerLayout.closeDrawers();
                break;

            case R.id.log_out:
                Toast.makeText(getBaseContext(), "TOCADO DESCONECTARSE", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }
}

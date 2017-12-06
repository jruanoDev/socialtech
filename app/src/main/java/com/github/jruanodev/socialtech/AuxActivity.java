package com.github.jruanodev.socialtech;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.github.jruanodev.socialtech.dao.Contact;

import java.util.ArrayList;
import java.util.List;

public class AuxActivity extends AppCompatActivity {
    FragmentManager fm;
    FragmentTransaction ft;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aux);

        List<Contact> contactList = getIntent().getParcelableArrayListExtra("contactList");

        ContactListFragment f1 = new ContactListFragment();

        Bundle fragmentArguments = new Bundle();
        fragmentArguments.putParcelableArrayList("contactList", (ArrayList<? extends Parcelable>) contactList);

        f1.setArguments(fragmentArguments);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        ft.replace(R.id.fragmentContainer, f1).commit();


    }
}

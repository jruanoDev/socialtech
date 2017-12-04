package com.github.jruanodev.socialtech;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.github.jruanodev.socialtech.dao.Contact;

import java.util.List;

public class AuxActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aux);

        ContactListFragment f1 = new ContactListFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.fragmentContainer, f1).commit();
        List<Contact> contactList = getIntent().getParcelableArrayListExtra("contactList");

    }
}

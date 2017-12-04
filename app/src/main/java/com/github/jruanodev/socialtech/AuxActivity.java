package com.github.jruanodev.socialtech;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.jruanodev.socialtech.dao.Contact;

import java.util.List;

public class AuxActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aux);

        FormFragment f1 = new FormFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.fragmentContainer, f1).commit();

        Intent intent = new Intent();
        List<Contact> contactList = intent.getParcelableArrayListExtra("contactList");

        Log.v("DATOS", "LISTA: " + contactList.toString());

    }
}

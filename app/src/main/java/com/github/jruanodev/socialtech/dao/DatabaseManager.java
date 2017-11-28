package com.github.jruanodev.socialtech.dao;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DatabaseManager {
    private Contact contact;
    FirebaseUser user;
    private final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/users");

    public DatabaseManager(Contact contact) {
        this.contact = contact;
    }

    public DatabaseManager(FirebaseUser user) {
        this.user = user;
    }

    public void createUserData() {
        HashMap<String, String> data = new HashMap<>();
        data.put("uid", user.getUid());
        data.put("hola", "hola");

        HashMap<String, Object> finalData = new HashMap<>();
        finalData.put("/" + mDatabase.push().getKey(), data);

        mDatabase.updateChildren(finalData);
    }

    public void createContact() {
    }
}

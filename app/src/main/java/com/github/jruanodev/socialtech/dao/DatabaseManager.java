package com.github.jruanodev.socialtech.dao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseManager {
    private Contact contact;
    private final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/users");;

    public DatabaseManager(Contact contact) {
        this.contact = contact;
    }
}

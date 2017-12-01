package com.github.jruanodev.socialtech.dao;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseManager {
    private Contact contact;
    public static FirebaseUser user;
    private final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/users");

    public DatabaseManager() {};

    public DatabaseManager(Contact contact) {
        this.contact = contact;
    }

    public void createUserData() {
        HashMap<String, String> data = new HashMap<>();
        data.put("uid", user.getUid());

        HashMap<String, Object> finalData = new HashMap<>();
        finalData.put("/" + mDatabase.push().getKey(), data);

        mDatabase.updateChildren(finalData);
    }

    public void createContact() {
        FirebaseDatabase.getInstance().getReference("/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String, Object> uid = (HashMap<String, Object>) snapshot.getValue();

                    for(String key : uid.keySet()) {
                        DatabaseReference dr = FirebaseDatabase.getInstance()
                                .getReference("/users/" + key + "/");

                        dr.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for(DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                                        String data = (String) snapshot1.getValue().toString();

                                        if(data != null && data.equals(user.getUid())) {
                                            DatabaseReference dr2 = snapshot1.getRef().getParent().child("contactos");
                                            dr2.push().setValue(contact.toMap());
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}

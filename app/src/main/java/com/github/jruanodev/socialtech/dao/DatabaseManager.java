package com.github.jruanodev.socialtech.dao;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        FirebaseDatabase.getInstance().getReference("/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String, Object> uid = (HashMap<String, Object>) snapshot.getValue();

                    for(String key : uid.keySet()) {
                        Log.v("KEY", "" + key);

                        FirebaseDatabase.getInstance().getReference("/users/" + key + "/")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for(DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                                        String data = (String) snapshot1.getValue();
                                        Log.v("PRUEBA", "" + data);

                                        //TODO
                                        // Rehacer esto con HashMaps

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

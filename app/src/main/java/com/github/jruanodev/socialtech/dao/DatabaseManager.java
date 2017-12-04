package com.github.jruanodev.socialtech.dao;

import android.util.Log;

import com.github.jruanodev.socialtech.MainActivity;
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
    onTaskCompleteListener taskCheck;
    private Contact contact;
    public static FirebaseUser user;
    private final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/users");
    public static DatabaseReference userDatabaseReference;
    private boolean check = false;

    public DatabaseManager() {
    };

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
        userDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference dr2 = dataSnapshot.getRef().child("contactos");
                dr2.push().setValue(contact.toMap());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getAllContacts() {
        taskCheck = MainActivity._instance;
        final List<Contact> contactList = new ArrayList<>();

        userDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference dr = dataSnapshot.getRef().child("contactos");
                dr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap<String, Object> data = (HashMap<String, Object>) dataSnapshot.getValue();
                        for(String key : data.keySet()) {
                            HashMap<String, String> contactData = (HashMap<String, String>) data.get(key);
                            Log.v("CONTACTO", "" + contactData.get("name"));
                            contactList.add(new Contact(contactData.get("name"), contactData.get("phone"),
                                    contactData.get("email"), Integer.parseInt(contactData.get("age")),
                                    contactData.get("sex"), contactData.get("formation")));
                        }

                        Log.v("LISTA", "" + contactList);
                        taskCheck.isContactImportComplete(contactList);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getCurrentUserDatabaseKey() {
        taskCheck = MainActivity._instance;

        FirebaseDatabase.getInstance().getReference("/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String, Object> uid = (HashMap<String, Object>) snapshot.getValue();

                    for(String key : uid.keySet()) {
                        final DatabaseReference[] dr = {FirebaseDatabase.getInstance()
                                .getReference("/users/" + key + "/")};

                        dr[0].addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                                    String data = (String) snapshot1.getValue().toString();

                                    if(data != null && data.equals(user.getUid())) {
                                        userDatabaseReference = snapshot1.getRef().getParent();
                                        taskCheck.isComplete(true);
                                        Log.v("DATOS", "SE HA PASADO POR AQUI " + userDatabaseReference.toString());
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

    public interface onTaskCompleteListener {
        public void isComplete(boolean check);
        public void isContactImportComplete(List<Contact> contactList);
    }

}

package com.github.jruanodev.socialtech.dao;

import android.util.Log;

import com.github.jruanodev.socialtech.BusinessFormFragment;
import com.github.jruanodev.socialtech.FormFragment;
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
    onTaskCompleteListener createContactCheck;
    onTaskCompleteListener createBusinessCheck;
    private Contact contact;
    private Business business;
    public static FirebaseUser user;
    private final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/users");
    public static DatabaseReference userDatabaseReference;
    private boolean check = false;

    public DatabaseManager() {
    };

    public DatabaseManager(Contact contact) {
        this.contact = contact;
    }
    public DatabaseManager(Business business) {this.business = business;}

    public void createUserData() {
        HashMap<String, String> data = new HashMap<>();
        data.put("uid", user.getUid());

        HashMap<String, Object> finalData = new HashMap<>();
        finalData.put("/" + mDatabase.push().getKey(), data);

        mDatabase.updateChildren(finalData);
    }

    public void createContact() {
        createContactCheck = FormFragment._instance;

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

        createContactCheck.isContactCreated(true);
    }

    public void createBusiness() {
        createBusinessCheck = FormFragment._instance;

        userDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference dr2 = dataSnapshot.getRef().child("empresas");
                dr2.push().setValue(business.toMap());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        createBusinessCheck.isBusinessCreated(true);

    }

    public void getAllContacts(final String callKey) {
        taskCheck = MainActivity._instance;
        createContactCheck = FormFragment._instance;
        createBusinessCheck = BusinessFormFragment._instance;

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

                        if(callKey.equals("MainActivity")) {
                            taskCheck.isContactImportComplete(contactList);
                        } else if(callKey.equals("FormFragment")) {
                            createContactCheck.isContactImportComplete(contactList);
                        }

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

    public void getAllBusinesses(final String callKey) {
        final List<Business> businessList = new ArrayList<>();

        userDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference dr = dataSnapshot.getRef().child("empresas");
                dr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap<String, Object> data = (HashMap<String, Object>) dataSnapshot.getValue();
                        for(String key : data.keySet()) {
                            HashMap<String, String> businessData = (HashMap<String, String>) data.get(key);
                            Log.v("EMPRESA", "" + businessData.get("name"));
                            businessList.add(new Business(businessData.get("name"),
                                    businessData.get("email"), businessData.get("phone"),
                                    businessData.get("address"), businessData.get("city"),
                                    businessData.get("country"), businessData.get("info")));
                        }

                        Log.v("LISTA", "" + businessList);
                        if(callKey.equals("MainActivity")) {
                            taskCheck = MainActivity._instance;
                            taskCheck.isBusinessImportComplete(businessList);
                        } else if(callKey.equals("FormFragment")) {
                            createContactCheck = FormFragment._instance;
                            createContactCheck.isBusinessImportComplete(businessList);
                        } else if(callKey.equals("BusinessFormFragment")) {
                            createBusinessCheck = BusinessFormFragment._instance;
                            createBusinessCheck.isBusinessImportComplete(businessList);
                        }

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

    public void getCurrentUserDatabaseKey(final String callType) {
        taskCheck = MainActivity._instance;
        createContactCheck = FormFragment._instance;
        createBusinessCheck = BusinessFormFragment._instance;

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

                                        if(callType.equals("MainActivity")) {
                                            taskCheck.isComplete(true);

                                        } else if(callType.equals("FormFragment")) {
                                            createContactCheck.isComplete(true);

                                        } else if(callType.equals("BusinessFormFragment")) {
                                            createBusinessCheck.isComplete(true);

                                        }

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
        public void isContactCreated(boolean check);
        public void isBusinessCreated(boolean check);
        public void isContactImportComplete(List<Contact> contactList);
        public void isBusinessImportComplete(List<Business> businessList);
    }

}

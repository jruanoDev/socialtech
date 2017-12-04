package com.github.jruanodev.socialtech;

import android.icu.text.AlphabeticIndex;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.jruanodev.socialtech.dao.Contact;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactListFragment extends Fragment {
    View inflatedView;
    List<Contact> contactList;
    String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    @BindView(R.id.contactListToolbar)
    Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_contactlist, container, false);

        ButterKnife.bind(this, inflatedView);

        Bundle fragmentArguments = this.getArguments();
        contactList = fragmentArguments.getParcelableArrayList("contactList");

        sortContactList();

        toolbar.setTitle("Contactos");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.menu_icon);
        toolbar.inflateMenu(R.menu.contactlist_menu);

        AlphabeticIndex<String> index = new AlphabeticIndex<String>(Locale.ENGLISH);

        int counter = 0;

        for(Contact contact : contactList) {
            index.addRecord(contact.getName(), letters[counter++]);
        }

        for(AlphabeticIndex.Bucket<String> bucket : index) {
            Log.v("INDICE" , "" + bucket.getLabel());

            for(AlphabeticIndex.Record<String> record : bucket) {
                Log.v("CONTENIDO", "" + record.getName());
            }
        }

        /*TODO
            REPASAR TODO SOBRE MENUS Y TOOLBAR
        */

        return inflatedView;
    }

    public void sortContactList() {


        contactList.sort(new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
               return o1.getName().compareTo(o2.getName());
            }
        });

        Log.v("LISTA ORDENADA", "" + contactList.toString());
    }
}

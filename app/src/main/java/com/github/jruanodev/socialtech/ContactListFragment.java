package com.github.jruanodev.socialtech;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactListFragment extends Fragment {
    View inflatedView;

    @BindView(R.id.contactListToolbar)
    Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_contactlist, container, false);

        ButterKnife.bind(this, inflatedView);

        toolbar.setTitle("Contactos");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.menu_icon);
        toolbar.inflateMenu(R.menu.contactlist_menu);

        /*TODO
            REPASAR TODO SOBRE MENUS Y TOOLBAR
         */

        return inflatedView;
    }
}

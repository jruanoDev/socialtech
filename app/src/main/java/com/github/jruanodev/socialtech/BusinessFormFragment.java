package com.github.jruanodev.socialtech;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.jruanodev.socialtech.dao.Business;
import com.github.jruanodev.socialtech.dao.Contact;
import com.github.jruanodev.socialtech.dao.DatabaseManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BusinessFormFragment extends Fragment implements View.OnClickListener, DatabaseManager.onTaskCompleteListener {
    View inflatedView;
    Business business;
    DatabaseManager d;
    public static BusinessFormFragment _instance;

    @BindView(R.id.bNombre) EditText inputName;
    @BindView(R.id.bTelefono) EditText inputTelefono;
    @BindView(R.id.bEmail) EditText inputEmail;
    @BindView(R.id.bAddress) EditText inputAddress;
    @BindView(R.id.bCity) EditText inputCity;
    @BindView(R.id.bCountry) EditText inputCountry;
    @BindView(R.id.bInfo) EditText inputInfo;
    @BindView(R.id.btnSaveB) Button btnSave;
    @BindView(R.id.btnResetB) TextView btnReset;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_businessform, container, false);
        ButterKnife.bind(this, inflatedView);
        _instance = this;

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        resetAllInputs();

        final Toolbar toolbar = getActivity().findViewById(R.id.aux_toolbar);
        toolbar.setTitle("Añadir empresa");
        toolbar.setNavigationIcon(R.drawable.back_arrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getArguments();
                BusinessListFragment businessListFragment = new BusinessListFragment();
                businessListFragment.setArguments(bundle);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right);
                ft.replace(R.id.fragmentContainer, businessListFragment).commit();

                toolbar.setNavigationOnClickListener(null);
            }
        });

        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        btnSave.setOnClickListener(this);
        btnReset.setOnClickListener(this);

        return inflatedView;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnSave:
                boolean a = checkName(inputName.getText().toString());
                boolean b = checkPhone(inputTelefono.getText().toString());
                boolean c = checkEmail(inputEmail.getText().toString());

                String name, phone, email, address, city, country, info;

                if(a && b && c) {
                    name = inputName.getText().toString();
                    phone = inputTelefono.getText().toString();
                    email = inputEmail.getText().toString();
                    address = inputAddress.getText().toString();
                    city = inputCity.getText().toString();
                    country = inputCountry.getText().toString();
                    info = inputInfo.getText().toString();

                    d = new DatabaseManager(business);

                    DatabaseManager.user = FirebaseAuth.getInstance().getCurrentUser();
                    d.getCurrentUserDatabaseKey("BusinessFormFragment");

                }

                break;

            case R.id.btnReset:
                resetAllInputs();
                break;

        }
    }

    public boolean checkName(String name) {
        boolean check = true;
        Pattern p = Pattern.compile("^[A-Za-z]+\\s*[A-Za-z]*\\s*[A-Za-z]*\\s*[A-Za-z]*");

        if(!p.matcher(name).matches()) {
            check = false;
            inputName.setError("Introduce un nombre correcto.");
        }

        return check;
    }

    public boolean checkPhone(String phone) {
        boolean check = true;
        Pattern p = Pattern.compile("^[+34]*\\s*[0-9]{3}\\s*[0-9]{3}\\s*[0-9]{3}");

        if(!p.matcher(phone).matches()) {
            check = false;
            inputTelefono.setError("Introduce un teléfono válido.");
        }

        return check;
    }

    public boolean checkEmail(String email) {
        boolean check = true;
        Pattern p = Pattern.compile("^[A-Za-z0-9._-]+@[A-Za-z0-9._-]+.+[A-Za-z]{2,3}");

        if(!p.matcher(email).matches()) {
            check = false;
            inputEmail.setError("Introduce un email válido.");
        }

        return check;
    }

    public void resetAllInputs() {
        inputName.setText("");
        inputEmail.setText("");
        inputTelefono.setText("");
        inputAddress.setText("");
        inputCity.setText("");
        inputCountry.setText("");
        inputInfo.setText("");
    }

    @Override
    public void isComplete(boolean check) {
        if(check)
            d.createBusiness();

    }

    @Override
    public void isContactCreated(boolean check) {}

    @Override
    public void isBusinessCreated(boolean check) {
        if(check) {
            d.getAllBusinesses("BusinessFormFragment");
        }
    }

    @Override
    public void isContactImportComplete(List<Contact> contactList) {}

    @Override
    public void isBusinessImportComplete(List<Business> businessList) {
        BusinessListFragment f1 = new BusinessListFragment();

        Bundle fragmentArguments = new Bundle();
        fragmentArguments.putParcelableArrayList("businessList", (ArrayList<? extends Parcelable>) businessList);

        f1.setArguments(fragmentArguments);

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.replace(R.id.fragmentContainer, f1).commit();
    }
}

package com.github.jruanodev.socialtech;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jruanodev.socialtech.dao.Contact;
import com.github.jruanodev.socialtech.dao.DatabaseManager;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FormFragment extends Fragment implements View.OnClickListener {
    View inflatedView;

    @BindView(R.id.backArrow) ImageView btnBack;
    @BindView(R.id.iNombre) EditText inputName;
    @BindView(R.id.iTelefono) EditText inputTelefono;
    @BindView(R.id.iEmail) EditText inputEmail;
    @BindView(R.id.sEdad) SeekBar sEdad;
    @BindView(R.id.ageCounter) TextView ageCounter;
    @BindView(R.id.sMasculino) RadioButton sMasculino;
    @BindView(R.id.sFemenino) RadioButton sFemenino;
    @BindView(R.id.formationTextView) AutoCompleteTextView fTextView;
    @BindView(R.id.btnSave) Button btnSave;
    @BindView(R.id.btnReset) TextView btnReset;

    private final String[] profesiones = {"SMR", "DAM", "DAW", "ASIR", "Ingeniería Técnica Informática",
        "Grado", "Otros"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_form, container, false);
        ButterKnife.bind(this, inflatedView);

        resetAllInputs();

        btnSave.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        sMasculino.setChecked(true);

        sEdad.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ageCounter.setText("" + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return inflatedView;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnSave:
                boolean a = checkName(inputName.getText().toString());
                boolean b = checkPhone(inputTelefono.getText().toString());
                boolean c = checkEmail(inputEmail.getText().toString());

                String name, phone, email, sexo = "", formation;
                int edad;

                if(a && b && c) {
                    name = inputName.getText().toString();
                    phone = inputTelefono.getText().toString();
                    email = inputEmail.getText().toString();
                    edad = Integer.parseInt(ageCounter.getText().toString());

                    if(sMasculino.isChecked())
                        sexo = "Masculino";
                    else if(sFemenino.isChecked())
                        sexo = "Femenino";

                    formation = fTextView.getText().toString();

                    Contact contact = new Contact(name, phone, email, edad, sexo, formation);
                    Log.v("CONTACTO", contact.toString());
                    DatabaseManager d = new DatabaseManager(contact);
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
        ageCounter.setText("0");
        fTextView.setText("");
        sEdad.setProgress(0);
    }
}

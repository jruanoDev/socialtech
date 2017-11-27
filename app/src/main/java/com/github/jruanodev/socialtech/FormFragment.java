package com.github.jruanodev.socialtech;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FormFragment extends Fragment {
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
    @BindView(R.id.btnReset) Button btnReset;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_form, container, false);

        ButterKnife.bind(this, inflatedView);
        return inflatedView;
    }
}

package com.github.jruanodev.socialtech;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jruanodev.socialtech.dao.DatabaseManager;
import com.github.jruanodev.socialtech.dao.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateUserFragment extends Fragment implements View.OnClickListener {
    View inflatedView;
    CloseListener mCallback;

    @BindView(R.id.cTitulo) TextView titulo;
    @BindView(R.id.btnClose) ImageView btnClose;
    @BindView(R.id.btnCreate) Button btnCreate;
    @BindView(R.id.cIEmail) EditText inputEmail;
    @BindView(R.id.cIPassword) EditText inputPassword;
    @BindView(R.id.cIName) EditText inputName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_createuser, container, false);
        ButterKnife.bind(this, inflatedView);

        btnClose.setOnClickListener(this);
        btnCreate.setOnClickListener(this);

        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BreeSerif-Regular.ttf");
        titulo.setTypeface(typeFace);

        inputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                inputEmail.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputEmail.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                User.checkValidEmail(inputEmail.getText().toString(), inputEmail);
            }
        });

        inputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                inputPassword.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputPassword.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                User.checkValidPassword(inputPassword.getText().toString(), inputPassword);
            }
        });

        inputName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                inputName.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputName.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                User.checkValidName(inputName.getText().toString(), inputName);
            }
        });

        return inflatedView;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mCallback = (CloseListener) context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnClose:
                mCallback.btnCloseClick(this);
                break;

            case R.id.btnCreate:
                boolean a = User.checkValidEmail(inputEmail.getText().toString(), inputEmail);
                boolean b = User.checkValidPassword(inputPassword.getText().toString(), inputPassword);
                boolean c = User.checkValidName(inputName.getText().toString(), inputName);

                if(a && b && c) {
                    User user = new User(inputEmail.getText().toString(),
                            inputPassword.getText().toString(), inputName.getText().toString());

                    mCallback.createUserClick(user);

                } else {
                    Snackbar.make(inflatedView, "Introduce datos correctos en los campos.",
                            Snackbar.LENGTH_SHORT);
                }

                break;
        }
    }

    public interface CloseListener {
        public void btnCloseClick(Fragment f);
        public void createUserClick(User u);
    }
}

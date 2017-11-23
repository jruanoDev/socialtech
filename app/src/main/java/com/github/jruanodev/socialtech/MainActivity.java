package com.github.jruanodev.socialtech;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jruanodev.socialtech.dao.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CreateUserFragment.CloseListener {

    @BindView(R.id.title) TextView title;
    Typeface typeface;

    @BindView(R.id.inputUsuario) EditText inputUsuario;
    @BindView(R.id.inputPassword) EditText inputPassword;
    @BindView(R.id.btnLogin) Button btnLogin;
    @BindView(R.id.tSignUp) TextView btnCreate;
    @BindView(R.id.mainContent) View view;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(this);
        btnCreate.setOnClickListener(this);

        if(mAuth.getCurrentUser() != null) {
            //Actualizar UI
        }

        AssetManager am = getApplicationContext().getAssets();
        typeface = Typeface.createFromAsset(getAssets(), "fonts/BreeSerif-Regular.ttf");

        title.setTypeface(typeface);

        inputUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                inputUsuario.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputUsuario.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                User.checkValidEmail(inputUsuario.getText().toString(), inputUsuario);
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                String email = inputUsuario.getText().toString();
                String password = inputPassword.getText().toString();

                if(!email.isEmpty() || !password.isEmpty())
                    loginWithUserAndPass(email, password);
                else
                    Snackbar.make(view, "Los campos no pueden estar vacíos.", Snackbar.LENGTH_SHORT).show();

                break;

            case R.id.tSignUp:
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                CreateUserFragment c1 = new CreateUserFragment();
                ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                ft.addToBackStack("MainActivity");
                ft.replace(android.R.id.content, c1).commit();

                break;

        }
    }

    public void loginWithUserAndPass(String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this,
            new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(getBaseContext(), "Login correcto", Toast.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(view, "Usuario o contraseña incorrectos.", Snackbar.LENGTH_SHORT);
                    }
                }
            });
    }

    @Override
    public void btnCloseClick(Fragment f) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        ft.remove(f).commit();
    }

    @Override
    public void createUserClick(User u) {
        mAuth.createUserWithEmailAndPassword(u.getEmail(), u.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            // Hacer algo
                        }
                    }
                });
    }
}

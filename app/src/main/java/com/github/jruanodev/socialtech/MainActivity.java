package com.github.jruanodev.socialtech;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;

    @BindView(R.id.title) TextView title;
    Typeface typeface;

    @BindView(R.id.inputUsuario) EditText inputUsuario;
    @BindView(R.id.inputPassword) EditText inputPassword;
    @BindView(R.id.btnLogin) Button btnLogin;
    @BindView(R.id.tSignUp) TextView btnCreate;
    @BindView(R.id.mainContent) View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        btnLogin.setOnClickListener(this);
        btnCreate.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {
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
                checkValidEmail(inputUsuario.getText().toString());
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
                checkValidPassword(inputPassword.getText().toString());
            }
        });

    }

    public boolean checkValidEmail(String email) {
        boolean check = true;

        int emailLength = email.length();
        if(emailLength >= 20 || emailLength < 6) {
            inputUsuario.setError("El email no puede tener más de 20 caracteres o menos de 6.");
            check = false;
        }

        Pattern pcheck = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        if(!pcheck.matcher(email).matches()) {
            inputUsuario.setError("Introduzca un email válido (example@example.com)");
            check = false;
        }

        return check;
    }

    public boolean checkValidPassword(String password) {
        boolean check = true;
        int passLenght = password.length();

        if(passLenght < 6 || passLenght >= 15) {
            inputPassword.setError("La contraseña no puede contener menos de 6 caracteres ni más de 15");
            check = false;
        }

        Pattern passCheck = Pattern.compile("^[a-zA-Z0-9@._-]+$");
        if(!passCheck.matcher(password).matches()) {
            inputPassword.setError("Los caractéres válidos son A-Z, 0-9, @, ., - y _");
            check = false;
        }

        return check;
    }

    public void loginWithUserAndPass(String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "Login Correcto", Toast.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(view, "Email o contraseña incorrectos.", Snackbar.LENGTH_LONG).show();
                }
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
}

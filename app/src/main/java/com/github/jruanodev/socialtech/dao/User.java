package com.github.jruanodev.socialtech.dao;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.regex.Pattern;

public class User {
    private String email;
    private String password;
    private String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public static boolean checkValidEmail(String email, EditText inputUsuario) {
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

    public static boolean checkValidPassword(String password, EditText inputPassword) {
        boolean check = true;
        int passLenght = password.length();

        if(passLenght < 6 || passLenght >= 15) {
            inputPassword.setError("La contraseña no puede contener menos de 6 caracteres ni más de 15");
            check = false;
        }

        Pattern passCheck = Pattern.compile("^[a-zA-Z0-9@._-]+$");
        if(!passCheck.matcher(password).matches()) {
            inputPassword.setError("Los caracteres válidos son A-Z, 0-9, @, ., - y _");
            check = false;
        }

        return check;
    }

    public static boolean checkValidName(String name, EditText inputName) {
        boolean check = true;

        int nameLength = name.length();
        if(nameLength < 2 || nameLength >= 10) {
            inputName.setError("El nombre no puede contener menos de 2 caracteres ni más de 10");
            check = false;
        }

        Pattern nameCheck = Pattern.compile("^[a-zA-Z]+$");
        if(!nameCheck.matcher(name).matches()) {
            inputName.setError("Los caracteres válidos son A-Z");
            check = false;
        }

        return check;
    }
}

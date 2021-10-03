package com.example.sharedpreferences_a_0181;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sharedpreferences_a_0181.Model.User;
import com.example.sharedpreferences_a_0181.Preferences.UserPreferences;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private MaterialButton btnClear, btnLogin;
    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        userPreferences=new UserPreferences(LoginActivity.this);

        etUsername=findViewById(R.id.etUsername);
        etPassword=findViewById(R.id.etPassword);

        btnClear=findViewById(R.id.btnClear);
        btnLogin=findViewById(R.id.btnLogin);

        checkLogin();

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUsername.setText("");
                etPassword.setText("");
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateForm()) {
                    if(etUsername.getText().toString().trim().equals("190710181") && etPassword.getText().toString().trim().equals("vito")) {
                        userPreferences.setLogin(etUsername.getText().toString().trim(),etPassword.getText().toString().trim());
                        checkLogin();
                    }else {
                        Toast.makeText(LoginActivity.this, "Username Atau Password Salah",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean validateForm() {
        if(etUsername.getText().toString().trim().isEmpty() || etPassword.getText().toString().trim().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Username Atau Password Kosong",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void checkLogin() {
        if(userPreferences.checkLogin()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
}
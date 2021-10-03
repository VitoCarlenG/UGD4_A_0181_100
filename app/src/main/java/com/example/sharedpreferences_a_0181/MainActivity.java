package com.example.sharedpreferences_a_0181;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharedpreferences_a_0181.Model.User;
import com.example.sharedpreferences_a_0181.Preferences.UserPreferences;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private TextView tvWelcome;
    private MaterialButton btnLogout;
    private User user;
    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        userPreferences=new UserPreferences(MainActivity.this);
        tvWelcome=findViewById(R.id.tvWelcome);
        btnLogout=findViewById(R.id.btnLogout);

        user=userPreferences.getUserLogin();

        checkLogin();

        tvWelcome.setText("Selamat datang, "+user.getUsername());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPreferences.logout();
                Toast.makeText(MainActivity.this, "Baiii baiii", Toast.LENGTH_SHORT).show();
                checkLogin();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setSelectedItemId(R.id.page1);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Deprecated
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.page1:
                        return true;
                    case R.id.page2:
                        startActivity(new Intent(MainActivity.this, RoomActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    private void checkLogin() {
        if(!userPreferences.checkLogin()) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }else {
            Toast.makeText(MainActivity.this, "Welcome back !", Toast.LENGTH_SHORT).show();
        }
    }
}
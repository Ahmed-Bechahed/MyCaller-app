package com.example.tp1;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    EditText edmdp, ednom;
    Button quitter, valider, signUp;
    CheckBox stay_connected;
    Boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edmdp = findViewById(R.id.auth_pwd);
        ednom = findViewById(R.id.auth_nom);
        quitter = findViewById(R.id.auth_quit_button);
        valider = findViewById(R.id.auth_validate_button);
        stay_connected = findViewById(R.id.stay_connected_btn);
        signUp = findViewById(R.id.sign_up_button);

        // Set click listener for Sign Up button
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = ednom.getText().toString().trim();
                String pwd = edmdp.getText().toString().trim();

                if (nom.isEmpty() || pwd.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Retrieve the saved password from SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                String savedPassword = sharedPreferences.getString(nom, null);

                if (savedPassword != null && savedPassword.equals(pwd)) {
                    // Correct login, save connection status
                    getSharedPreferences("isConnected", MODE_PRIVATE).edit().putBoolean("isConnected", stay_connected.isChecked()).apply();

                    Intent i = new Intent(MainActivity.this, Accueil.class);
                    startActivity(i);
                } else {
                    Toast.makeText(MainActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });
    }
}

package rs.raf.projekat1.rafplaner.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import rs.raf.projekat1.rafplaner.AppModule;
import rs.raf.projekat1.rafplaner.R;

public class LoginActivity extends AppCompatActivity {


    private Button loginBtn;
    private EditText emailEt;
    private EditText usernameEt;
    private EditText passwordEt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        setListeners();
    }

    private void init() {
        loginBtn = findViewById(R.id.loginBtn);
        usernameEt = findViewById(R.id.usernameEt);
        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
    }

    private void setListeners() {
        loginBtn.setOnClickListener(v -> {

            AppModule.getInstance(getApplication()).getDb().insertUser(usernameEt.getText().toString(), emailEt.getText().toString(), passwordEt.getText().toString());

            SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
            sharedPreferences
                .edit()
                .putString("username", usernameEt.getText().toString())
                .apply();
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        });
    }
}
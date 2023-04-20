package rs.raf.projekat1.rafplaner.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import rs.raf.projekat1.rafplaner.AppModule;
import rs.raf.projekat1.rafplaner.HelperFunctions;
import rs.raf.projekat1.rafplaner.R;
import rs.raf.projekat1.rafplaner.model.User;

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

            if(!requiredFields())
                return;
            if(!HelperFunctions.checkPasswordRequirements(passwordEt.getText().toString())) {
                Snackbar.make(findViewById(R.id.loginRoot), getString(R.string.invalid_password), Snackbar.LENGTH_LONG)
                        .setAction(null, null).setTextMaxLines(10).show();
                return;
            }
            if(!validate()) {
                Snackbar.make(findViewById(R.id.loginRoot), getString(R.string.invalid_credentials), Snackbar.LENGTH_LONG)
                        .setAction(null, null).setTextMaxLines(10).show();
                return;
            }
            AppModule.getInstance(getApplication()).getDb().insertUser(usernameEt.getText().toString(), emailEt.getText().toString(), passwordEt.getText().toString());

            SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
            sharedPreferences
                .edit()
                .putString("username", usernameEt.getText().toString())
                .putString("email", emailEt.getText().toString())
                .apply();
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        });
    }

    private boolean validate() {
        User user = AppModule.getInstance(getApplication()).getDb().getUser(usernameEt.getText().toString());
        return user != null && user.getPassword().equals(passwordEt.getText().toString());
    }

    private boolean requiredFields(){
        boolean valid = true;
        if(usernameEt.getText().toString().isEmpty()) {
            usernameEt.setError(getString(R.string.required_field));
            valid = false;
        }
        if(passwordEt.getText().toString().isEmpty()) {
            passwordEt.setError(getString(R.string.required_field));
            valid = false;
        }
        if(emailEt.getText().toString().isEmpty()) {
            emailEt.setError(getString(R.string.required_field));
            valid = false;
        }

        return valid;
    }
}
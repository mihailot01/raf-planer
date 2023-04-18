package rs.raf.projekat1.rafplaner.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import rs.raf.projekat1.rafplaner.R;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("username", null);

        Toast.makeText(this, user, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Class activityClass = (user == null ? LoginActivity.class: MainActivity.class);
                Intent mainIntent = new Intent(SplashScreenActivity.this, activityClass);
                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();

            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
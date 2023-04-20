package rs.raf.projekat1.rafplaner.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import java.util.Locale;

import rs.raf.projekat1.rafplaner.R;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ((ImageView)findViewById(R.id.imgLogo)).setImageBitmap(getBitmapFromResources(getResources(), R.drawable.planer));

        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("username", null);

        String lang = sharedPreferences.getString("language", "sr");
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());


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

    public static Bitmap getBitmapFromResources(Resources resources, int resImage) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inSampleSize = 1;
        options.inScaled = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        return BitmapFactory.decodeResource(resources, resImage, options);
    }
}
package rs.raf.projekat1.rafplaner.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import rs.raf.projekat1.rafplaner.R;
import rs.raf.projekat1.rafplaner.view.fragments.DailyPlanFragment;

public class DailyPlanHolderActivity extends AppCompatActivity {

    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_plan_holder);


        date = (Date) getIntent().getSerializableExtra("date");

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        getSupportActionBar().setTitle(formatter.format(date));


        Log.d("TEST", "onCreate: "+date.toString());

        Fragment fragment = DailyPlanFragment.newInstance(date);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
    }
}
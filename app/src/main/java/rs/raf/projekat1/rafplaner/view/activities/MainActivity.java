package rs.raf.projekat1.rafplaner.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import rs.raf.projekat1.rafplaner.R;

import rs.raf.projekat1.rafplaner.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    private void init() {
        initNavigation();
    }


    private void initNavigation() {

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.bottomNavigation);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_calendar, R.id.navigation_daily_plan, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);

    }
}
package rs.raf.projekat1.rafplaner.activities.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rs.raf.projekat1.rafplaner.R;
import rs.raf.projekat1.rafplaner.activities.LoginActivity;
import rs.raf.projekat1.rafplaner.activities.SplashScreenActivity;
import rs.raf.projekat1.rafplaner.databinding.FragmentCalendarBinding;

import rs.raf.projekat1.rafplaner.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnLogout.setOnClickListener(v -> {
            Activity activity = getActivity();
            SharedPreferences sharedPreferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
            sharedPreferences
                    .edit()
                    .remove("username")
                    .apply();
            Intent mainIntent = new Intent(activity, LoginActivity.class);
            this.startActivity(mainIntent);
            activity.finish();

        });

        return root;
    }
}
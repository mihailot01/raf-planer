package rs.raf.projekat1.rafplaner.activities.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rs.raf.projekat1.rafplaner.R;
import rs.raf.projekat1.rafplaner.databinding.FragmentCalendarBinding;

import rs.raf.projekat1.rafplaner.databinding.FragmentDailyPlanBinding;
public class DailyPlanFragment extends Fragment {

    FragmentCalendarBinding binding;

    public DailyPlanFragment() {
        // Required empty public constructor
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }
}
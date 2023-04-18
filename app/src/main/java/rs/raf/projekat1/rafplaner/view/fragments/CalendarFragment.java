package rs.raf.projekat1.rafplaner.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import rs.raf.projekat1.rafplaner.R;

import rs.raf.projekat1.rafplaner.databinding.FragmentCalendarBinding;


public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding binding;

    public CalendarFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }
}
package rs.raf.projekat1.rafplaner.view.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rs.raf.projekat1.rafplaner.R;
import rs.raf.projekat1.rafplaner.databinding.FragmentCalendarBinding;

import rs.raf.projekat1.rafplaner.databinding.FragmentDailyPlanBinding;
import rs.raf.projekat1.rafplaner.model.Priority;
import rs.raf.projekat1.rafplaner.model.Task;
import rs.raf.projekat1.rafplaner.view.TasksListAdapter;
import rs.raf.projekat1.rafplaner.view.activities.TaskPreviewActivity;
import rs.raf.projekat1.rafplaner.viewmodel.TasksViewModel;

public class DailyPlanFragment extends Fragment {

    FragmentDailyPlanBinding binding;

    TasksViewModel viewModel;

    TasksListAdapter tasksListAdapter;
    RecyclerView rvDailyPlan;

    boolean lowPriority = true;
    boolean highPriority = true;
    boolean mediumPriority = true;

    public DailyPlanFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDailyPlanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rvDailyPlan = binding.rvDailyPlan;

        ViewModelProvider.Factory factory = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication());
        viewModel = new ViewModelProvider(requireActivity(),factory).get("tasks",TasksViewModel.class);

        viewModel.getTasks().observe(getViewLifecycleOwner(), tasks -> {
            this.updateList();
        });
        Context context = getActivity();
        tasksListAdapter = new TasksListAdapter() {
            @Override
            public void onTaskClick(int position) {
                Intent intent = new Intent(context, TaskPreviewActivity.class);
                intent.putExtra("task_index", position);
                startActivity(intent);
            }
        };


        rvDailyPlan.setAdapter(tasksListAdapter);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        rvDailyPlan.setLayoutManager(lm);

        initListeners();
        return root;
    }

    public void initListeners(){
        binding.btnLowPriority.setOnClickListener(v -> {
            lowPriority = !lowPriority;
            if(!lowPriority)
                binding.btnLowPriority.setAlpha(0.2f);
            else
                binding.btnLowPriority.setAlpha(1f);
            updateList();
        });
        binding.btnMediumPriority.setOnClickListener(v -> {
            mediumPriority = !mediumPriority;
            if(!mediumPriority)
                binding.btnMediumPriority.setAlpha(0.2f);
            else
                binding.btnMediumPriority.setAlpha(1f);
            updateList();
        });
        binding.btnHighPriority.setOnClickListener(v -> {
            highPriority = !highPriority;
            if(!highPriority)
                binding.btnHighPriority.setAlpha(0.2f);
            else
                binding.btnHighPriority.setAlpha(1f);
            updateList();
        });
    }

    public void updateList(){
        List<Task> list = new ArrayList<>(viewModel.getTasks().getValue());
        Log.d("TEST", "updateList: " + list.toString());
        if(!lowPriority){
            list.removeIf(task -> task.getPriority() == Priority.LOW);
        }
        if(!mediumPriority){
            list.removeIf(task -> task.getPriority() == Priority.MEDIUM);
        }
        if(!highPriority){
            list.removeIf(task -> task.getPriority() == Priority.HIGH);
        }
        Log.d("TEST", "updateList: " + list.toString());
        tasksListAdapter.submitList(list);
    }
}
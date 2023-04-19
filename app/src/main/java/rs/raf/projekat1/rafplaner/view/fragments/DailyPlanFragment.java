package rs.raf.projekat1.rafplaner.view.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rs.raf.projekat1.rafplaner.AppModule;
import rs.raf.projekat1.rafplaner.R;

import rs.raf.projekat1.rafplaner.databinding.FragmentDailyPlanBinding;
import rs.raf.projekat1.rafplaner.model.Priority;
import rs.raf.projekat1.rafplaner.model.Task;
import rs.raf.projekat1.rafplaner.view.DeleteDialogClickListener;
import rs.raf.projekat1.rafplaner.view.TasksListAdapter;
import rs.raf.projekat1.rafplaner.view.activities.TaskFormActivity;
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
        AppModule.getInstance(getActivity().getApplication()).setDailyTasksViewModel(viewModel);

        viewModel.getTasks().observe(getViewLifecycleOwner(), tasks -> {
            this.updateList();
        });
        Context context = getActivity();
        Application application = getActivity().getApplication();
        tasksListAdapter = new TasksListAdapter() {
            @Override
            public void onTaskClick(Task task) {
                int position = this.getCurrentList().indexOf(task);
                Intent intent = new Intent(context, TaskPreviewActivity.class);
                intent.putExtra("task_index", position);
                Log.d("TEST", "onTaskClick:  postion "+position+"  item count" + this.getItemCount());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Task task) {
                DialogInterface.OnClickListener dialogClickListener = new DeleteDialogClickListener(application,task);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(context.getString(R.string.are_you_sure)).setPositiveButton(context.getString(R.string.yes), dialogClickListener)
                        .setNegativeButton(context.getString(R.string.no), dialogClickListener).show();
            }

            @Override
            public void onEditClick(Task task) {

                Intent intent = new Intent(context, TaskFormActivity.class);
                intent.putExtra("task_id", task.getId());
                intent.putExtra("date", new Date(System.currentTimeMillis()));
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

        FragmentActivity context = this.getActivity();
        binding.btnAddTask.setOnClickListener(view -> {
//            Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();

            Intent intent = new Intent(context, TaskFormActivity.class);
            intent.putExtra("task_id", -1);
            intent.putExtra("date", new Date(System.currentTimeMillis()));
            startActivity(intent);
        });


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
        Log.d("TEST", "updateList: " + list.size());
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
        viewModel.setFilteredTasks(list);
        tasksListAdapter.submitList(list);
    }
}
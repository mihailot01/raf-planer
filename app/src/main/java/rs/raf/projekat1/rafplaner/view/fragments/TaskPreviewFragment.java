package rs.raf.projekat1.rafplaner.view.fragments;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import rs.raf.projekat1.rafplaner.AppModule;
import rs.raf.projekat1.rafplaner.HelperFunctions;
import rs.raf.projekat1.rafplaner.R;
import rs.raf.projekat1.rafplaner.databinding.FragmentTaskPreviewBinding;
import rs.raf.projekat1.rafplaner.model.Task;
import rs.raf.projekat1.rafplaner.view.DeleteDialogClickListener;
import rs.raf.projekat1.rafplaner.view.activities.TaskFormActivity;
import rs.raf.projekat1.rafplaner.viewmodel.SingleTaskViewModel;
import rs.raf.projekat1.rafplaner.viewmodel.TasksViewModel;


public class TaskPreviewFragment extends Fragment {

    private static final String ARG_TASK_ID = "task_index";

    private int taskIndex;

    FragmentTaskPreviewBinding binding;

    private SingleTaskViewModel taskViewModel;
    private TasksViewModel allTasksviewModel;


    private TextView tvTitle;

    private TextView tvDate;

    private TextView tvTime;

    private TextView tvDescription;

    public TaskPreviewFragment() {
        // Required empty public constructor
    }

    public static TaskPreviewFragment newInstance(int taskIndex) {
        TaskPreviewFragment fragment = new TaskPreviewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TASK_ID, taskIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            taskIndex = getArguments().getInt(ARG_TASK_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        allTasksviewModel = AppModule.getInstance(getActivity().getApplication()).getDailyTasksViewModel();

        Log.d("TEST", "onCreateView: TASK PREVIW FRAGMENT");
        taskViewModel = new ViewModelProvider(requireActivity()).get(SingleTaskViewModel.class);
        taskViewModel.setTask(allTasksviewModel.getFilteredTasks().getValue().get(taskIndex));

        allTasksviewModel.getTasks().observe(getViewLifecycleOwner(), tasks -> {
            taskViewModel.setTask(allTasksviewModel.getFilteredTasks().getValue().get(taskIndex));
            initView();
        });


//        ((TextView) container.findViewById(R.id.tvTitle)).setText(taskViewModel.getTask().getValue().getTitle());
        View view =  (ViewGroup) inflater.inflate(
                R.layout.fragment_task_preview, container, false);

        tvTitle = view.findViewById(R.id.tvTitle);
        tvDate = view.findViewById(R.id.tvDate);
        tvTime = view.findViewById(R.id.tvTime);
        tvDescription = view.findViewById(R.id.tvDescription);

        view.findViewById(R.id.btnEdit).setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), TaskFormActivity.class);
            intent.putExtra("task_id", taskViewModel.getTask().getValue().getId());
            intent.putExtra("date", taskViewModel.getTask().getValue().getDate());
            startActivity(intent);
        });

        Context context = getContext();
        Activity activity = getActivity();

        view.findViewById(R.id.btnDelete).setOnClickListener(v -> {

                DialogInterface.OnClickListener dialogClickListener = new DeleteDialogClickListener(activity.getApplication(),getTask(),activity);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(context.getString(R.string.are_you_sure)).setPositiveButton(context.getString(R.string.yes), dialogClickListener)
                        .setNegativeButton(context.getString(R.string.no), dialogClickListener).show();
        });

        return view;
//        binding = FragmentTaskPreviewBinding.inflate(inflater, container, false);
//        binding.tvTitle.setText(taskViewModel.getTask().getValue().getTitle());

    }

    private void initView(){
        tvTitle.setText(taskViewModel.getTask().getValue().getTitle());
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        tvDate.setText(formatter.format(taskViewModel.getTask().getValue().getDate()));
        tvTime.setText(HelperFunctions.getFormattedTime(getTask().getStartTime()) + " - " + HelperFunctions.getFormattedTime(getTask().getEndTime()));
        tvDescription.setText(taskViewModel.getTask().getValue().getDescription());

    }

    private Task getTask(){
        return taskViewModel.getTask().getValue();
    }

    @Override
    public void onResume() {
        super.onResume();
        taskViewModel.setTask(allTasksviewModel.getFilteredTasks().getValue().get(taskIndex));
        initView();
    }



}
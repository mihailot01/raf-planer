package rs.raf.projekat1.rafplaner.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rs.raf.projekat1.rafplaner.AppModule;
import rs.raf.projekat1.rafplaner.R;
import rs.raf.projekat1.rafplaner.databinding.FragmentProfileBinding;
import rs.raf.projekat1.rafplaner.databinding.FragmentTaskPreviewBinding;
import rs.raf.projekat1.rafplaner.viewmodel.SingleTaskViewModel;
import rs.raf.projekat1.rafplaner.viewmodel.TasksViewModel;


public class TaskPreviewFragment extends Fragment {

    private static final String ARG_TASK_ID = "task_index";

    private int taskIndex;

    FragmentTaskPreviewBinding binding;

    private SingleTaskViewModel taskViewModel;
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

        ViewModelProvider.Factory factory = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication());
        TasksViewModel allTasksviewModel = new ViewModelProvider(requireActivity(),factory).get("tasks", TasksViewModel.class);

        taskViewModel = new ViewModelProvider(requireActivity()).get(SingleTaskViewModel.class);
        taskViewModel.setTask(allTasksviewModel.getTasks().getValue().get(taskIndex));


        binding = FragmentTaskPreviewBinding.inflate(inflater, container, false);
        binding.tvTitle.setText(taskViewModel.getTask().getValue().getTitle());

        return binding.getRoot();
    }
}
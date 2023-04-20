package rs.raf.projekat1.rafplaner.view.fragments;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import rs.raf.projekat1.rafplaner.AppModule;
import rs.raf.projekat1.rafplaner.HelperFunctions;
import rs.raf.projekat1.rafplaner.R;

import rs.raf.projekat1.rafplaner.databinding.ActivityTaskFormBinding;
import rs.raf.projekat1.rafplaner.databinding.FragmentDailyPlanBinding;
import rs.raf.projekat1.rafplaner.model.Priority;
import rs.raf.projekat1.rafplaner.model.Task;
import rs.raf.projekat1.rafplaner.view.DeleteDialogClickListener;
import rs.raf.projekat1.rafplaner.view.TasksListAdapter;
import rs.raf.projekat1.rafplaner.view.activities.TaskFormActivity;
import rs.raf.projekat1.rafplaner.view.activities.TaskPreviewActivity;
import rs.raf.projekat1.rafplaner.viewmodel.DailyPlanViewModel;
import rs.raf.projekat1.rafplaner.viewmodel.TasksViewModel;

public class DailyPlanFragment extends Fragment {
    private static final String ARG_DATE = "date";

    FragmentDailyPlanBinding binding;

    TasksViewModel tasksViewModel;

    DailyPlanViewModel dailyPlanViewModel;

    TasksListAdapter tasksListAdapter;
    RecyclerView rvDailyPlan;

    CheckBox cbShowPast;

    EditText etDailyPlanSearch;

    boolean lowPriority = true;
    boolean highPriority = true;
    boolean mediumPriority = true;


    Date date = HelperFunctions.trimDate(new Date());

    public DailyPlanFragment() {
        // Required empty public constructor
    }

    public static DailyPlanFragment newInstance(Date date) {
        DailyPlanFragment fragment = new DailyPlanFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            date = (Date) getArguments().getSerializable(ARG_DATE);
        }
        //initViewModel();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        binding = FragmentDailyPlanBinding.inflate(inflater, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_daily_plan, container, false);

        View root = binding.getRoot();

        rvDailyPlan = binding.rvDailyPlan;
        cbShowPast = binding.cbShowPast;
        etDailyPlanSearch = binding.etDailyPlanSearch;





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
                DialogInterface.OnClickListener dialogClickListener = new DeleteDialogClickListener(application,task,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(context.getString(R.string.are_you_sure)).setPositiveButton(context.getString(R.string.yes), dialogClickListener)
                        .setNegativeButton(context.getString(R.string.no), dialogClickListener).show();
            }

            @Override
            public void onEditClick(Task task) {

                Intent intent = new Intent(context, TaskFormActivity.class);
                intent.putExtra("task_id", task.getId());
                intent.putExtra("date", date);
                startActivity(intent);
            }

        };

        setButtonsAlpha();

        rvDailyPlan.setAdapter(tasksListAdapter);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        rvDailyPlan.setLayoutManager(lm);

        initViewModel();
        binding.setViewmodel(dailyPlanViewModel);
        binding.setLifecycleOwner(this);
        initListeners();
        return root;
    }

    private void setButtonsAlpha(){
        if(dailyPlanViewModel != null) {
            if (!dailyPlanViewModel.getLowPriority().getValue())
                binding.btnLowPriority.setAlpha(0.2f);

            if (!dailyPlanViewModel.getMediumPriority().getValue())
                binding.btnMediumPriority.setAlpha(0.2f);

            if (!dailyPlanViewModel.getHighPriority().getValue())
                binding.btnHighPriority.setAlpha(0.2f);
        }
    }

    private void initViewModel(){
        Log.d("TEST", "onCreateView: "+(tasksViewModel ==null?"null":"not null"));

        if(tasksViewModel ==null) {
            tasksViewModel = new ViewModelProvider(requireActivity()).get("tasks", TasksViewModel.class);
            tasksViewModel.setTasks(AppModule.getInstance(getActivity().getApplication()).getDb().getTasksByDate(date));
            tasksViewModel.setFilteredTasks(tasksViewModel.getTasks().getValue());
            AppModule.getInstance(getActivity().getApplication()).setDailyTasksViewModel(tasksViewModel);

            tasksViewModel.getTasks().observe(getViewLifecycleOwner(), tasks -> {
                this.updateList();
            });
        }
        else {
            tasksViewModel.getTasks().observe(getViewLifecycleOwner(), tasks -> {
                this.updateList();
            });
        }

        dailyPlanViewModel = new ViewModelProvider(requireActivity()).get("dailyPlan", DailyPlanViewModel.class);
        dailyPlanViewModel.getLowPriority().observe(getViewLifecycleOwner(), priority -> {
            this.updateList();
        });
        dailyPlanViewModel.getMediumPriority().observe(getViewLifecycleOwner(), priority -> {
            this.updateList();
        });
        dailyPlanViewModel.getHighPriority().observe(getViewLifecycleOwner(), priority -> {
            this.updateList();
        });

        dailyPlanViewModel.getShowPastTasksLiveData().observe(getViewLifecycleOwner(), showPast -> {
            this.updateList();
        });

        dailyPlanViewModel.getSearchLiveData().observe(getViewLifecycleOwner(), search -> {
            this.updateList();
        });

    }

    public void initListeners(){

        FragmentActivity context = this.getActivity();
        binding.btnAddTask.setOnClickListener(view -> {
//            Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();

            Intent intent = new Intent(context, TaskFormActivity.class);
            intent.putExtra("task_id", -1);
            intent.putExtra("date", date);
            startActivity(intent);
        });


        binding.btnLowPriority.setOnClickListener(v -> {
            dailyPlanViewModel.setLowPriority(!dailyPlanViewModel.getLowPriority().getValue());
            if(!dailyPlanViewModel.getLowPriority().getValue())
                binding.btnLowPriority.setAlpha(0.2f);
            else
                binding.btnLowPriority.setAlpha(1f);
        });
        binding.btnMediumPriority.setOnClickListener(v -> {
            dailyPlanViewModel.setMediumPriority(!dailyPlanViewModel.getMediumPriority().getValue());
            if(!dailyPlanViewModel.getMediumPriority().getValue())
                binding.btnMediumPriority.setAlpha(0.2f);
            else
                binding.btnMediumPriority.setAlpha(1f);
        });
        binding.btnHighPriority.setOnClickListener(v -> {
            dailyPlanViewModel.setHighPriority(!dailyPlanViewModel.getHighPriority().getValue());
            if(!dailyPlanViewModel.getHighPriority().getValue())
                binding.btnHighPriority.setAlpha(0.2f);
            else
                binding.btnHighPriority.setAlpha(1f);
        });
    }

    public void updateList(){
        List<Task> list = new ArrayList<>(tasksViewModel.getTasks().getValue());
        //Log.d("TEST", "updateList: " + list.get(5).getPriority());
        if(!dailyPlanViewModel.getLowPriority().getValue()){
            list.removeIf(task -> task.getPriority() == Priority.LOW);
        }
        if(!dailyPlanViewModel.getMediumPriority().getValue()){
            list.removeIf(task -> task.getPriority() == Priority.MEDIUM);
        }
        if(!dailyPlanViewModel.getHighPriority().getValue()){
            list.removeIf(task -> task.getPriority() == Priority.HIGH);
        }
        if(!cbShowPast.isChecked())
            list.removeIf(Task::isFinished);

        list = list.stream()
                .filter(task -> task.getTitle().contains(dailyPlanViewModel.getSearch()))     // we dont like mkyong
                .collect(Collectors.toList());

        list.sort(Comparator.comparing(Task::getEndTime));
        tasksViewModel.setFilteredTasks(list);
        tasksListAdapter.submitList(list);
        tasksListAdapter.notifyDataSetChanged();
        Log.d("TEST", "updateList: " + list.toString());

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("TEST", "onResume: ");
        initViewModel();
        this.updateList();
        this.setButtonsAlpha();
    }
}
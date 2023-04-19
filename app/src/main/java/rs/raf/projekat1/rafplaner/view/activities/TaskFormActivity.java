package rs.raf.projekat1.rafplaner.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.material.snackbar.Snackbar;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import rs.raf.projekat1.rafplaner.AppModule;
import rs.raf.projekat1.rafplaner.HelperFunctions;
import rs.raf.projekat1.rafplaner.R;
import rs.raf.projekat1.rafplaner.databinding.ActivityMainBinding;
import rs.raf.projekat1.rafplaner.databinding.ActivityTaskFormBinding;
import rs.raf.projekat1.rafplaner.model.Priority;
import rs.raf.projekat1.rafplaner.model.Task;
import rs.raf.projekat1.rafplaner.viewmodel.SingleTaskViewModel;
import rs.raf.projekat1.rafplaner.viewmodel.TaskFormViewModel;
import rs.raf.projekat1.rafplaner.viewmodel.TasksViewModel;

public class TaskFormActivity extends AppCompatActivity {

    int taskId;
    Date date;

    TaskFormViewModel taskFormViewModel;

    EditText etTitle;
    TimePicker tpStart;
    TimePicker tpEnd;

    Button btnLow;
    Button btnMedium;
    Button btnHigh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_form);

        //ActivityTaskFormBinding binding = ActivityTaskFormBinding.inflate(getLayoutInflater());
        ActivityTaskFormBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_task_form);

        taskId = getIntent().getIntExtra("task_id", -1);
        date = (Date) getIntent().getSerializableExtra("date");
        date = HelperFunctions.trimDate(date);
        initViewModel();

//        Snackbar.make(findViewById(R.id.form_root), taskFormViewModel.getTitle(), Snackbar.LENGTH_LONG)
//                .setAction(null, null).show();

        binding.setLifecycleOwner(this);
        binding.setViewmodel(taskFormViewModel);

        initView();
        intiListeners(binding);
    }

    private void initViewModel() {

        taskFormViewModel = new ViewModelProvider(this).get(TaskFormViewModel.class);
        Task task;
        if (taskId != -1) {
            task = AppModule.getInstance(this.getApplication()).getDb().getTaskById(taskId);
            taskFormViewModel.setTitle(task.getTitle());
            taskFormViewModel.setDescription(task.getDescription());
            taskFormViewModel.setStartTimeHour(task.getStartTime()/60);
            taskFormViewModel.setStartTimeMinute(task.getStartTime()%60);
            taskFormViewModel.setEndTimeHour(task.getEndTime()/60);
            taskFormViewModel.setEndTimeMinute(task.getEndTime()%60);
        }
        else {
            task = new Task();
            task.setDate(date);
        }
    }

    private void initView(){
        etTitle = findViewById(R.id.etTitle);

        tpStart=(TimePicker)findViewById(R.id.tpStart);
        tpStart.setIs24HourView(true);

        tpEnd=(TimePicker)findViewById(R.id.tpEnd);
        tpEnd.setIs24HourView(true);

        btnLow = findViewById(R.id.btnLowPriorityForm);
        btnMedium = findViewById(R.id.btnMediumPriorityForm);
        btnHigh = findViewById(R.id.btnHighPriorityForm);
    }

    private void intiListeners(ActivityTaskFormBinding binding) {
        binding.btnSave.setOnClickListener(v -> {

//            Snackbar.make(findViewById(R.id.form_root), taskFormViewModel.getTitle(), Snackbar.LENGTH_LONG)
//                    .setAction(null, null).show();
            if(taskId==-1)
                createTask();
            else
                updateTask();
        });

        binding.btnCancel.setOnClickListener(v -> {
            finish();
        });
        btnLow.setOnClickListener(v -> {
            taskFormViewModel.setPriority(Priority.LOW);
            btnLow.setAlpha(1);
            btnMedium.setAlpha(0.3f);
            btnHigh.setAlpha(0.3f);
        });
        btnMedium.setOnClickListener(v -> {
            taskFormViewModel.setPriority(Priority.MEDIUM);
            btnLow.setAlpha(0.3f);
            btnMedium.setAlpha(1);
            btnHigh.setAlpha(0.3f);
        });
        btnHigh.setOnClickListener(v -> {
            taskFormViewModel.setPriority(Priority.HIGH);
            btnLow.setAlpha(0.3f);
            btnMedium.setAlpha(0.3f);
            btnHigh.setAlpha(1);
        });

        btnLow.callOnClick();
    }


    private Task prepareForSave(){

        Task task = new Task();
        if(taskId!=-1)
            task.setId(taskId);
        task.setTitle(taskFormViewModel.getTitle());
        task.setDescription(taskFormViewModel.getDescription());
        task.setStartTime(taskFormViewModel.getStartTimeHour()*60 + taskFormViewModel.getStartTimeMinute());
        task.setEndTime(taskFormViewModel.getEndTimeHour()*60 + taskFormViewModel.getEndTimeMinute());
//        if(taskId==-1)
            task.setDate(date);
        task.setPriority(taskFormViewModel.getPriority());
        return task;
    }
    private void createTask(){

//       makeToast(String.valueOf(HelperFunctions.trimDate(new Date())));
        if(!validate())
            return;
        Task task = prepareForSave();
        AppModule.getInstance(this.getApplication()).getDb().insertTask(task);
        TasksViewModel tasksViewModel = AppModule.getInstance(this.getApplication()).getDailyTasksViewModel();
        tasksViewModel.addTask(task);
        finish();
    }

    private void updateTask(){
        if(!validate())
            return;
        Task task = prepareForSave();
        task.setId(taskId);
        AppModule.getInstance(this.getApplication()).getDb().updateTask(task);
        TasksViewModel tasksViewModel = AppModule.getInstance(this.getApplication()).getDailyTasksViewModel();
        tasksViewModel.updateTask(task);
        finish();
    }

    private void makeToast(String text){
        Snackbar.make(findViewById(R.id.form_root), text, Snackbar.LENGTH_LONG)
                .setAction(null, null).show();
    }

    private boolean validate(){
        Log.d("TEST", "validate: ID "+ taskId);
        if(taskFormViewModel.getTitle().isEmpty()){
            makeToast(this.getString(R.string.missing_title));
            return false;
        }
        if(taskFormViewModel.getDescription().isEmpty()){
            makeToast(this.getString(R.string.missing_description));
            return false;
        }
        if(taskFormViewModel.getStartTimeHour() > taskFormViewModel.getEndTimeHour()){
            makeToast(this.getString(R.string.invalid_tiime));
            return false;
        }
        if(Objects.equals(taskFormViewModel.getStartTimeHour(), taskFormViewModel.getEndTimeHour()) && taskFormViewModel.getStartTimeMinute() >= taskFormViewModel.getEndTimeMinute()){
            makeToast(this.getString(R.string.invalid_tiime));
            return false;
        }
        List<Task> tasks = AppModule.getInstance(this.getApplication()).getDb().getTasksByDate(date);
        for(Task task : tasks){
            if(task.getId() == taskId)
                continue;
            Log.d("TEST", "validate: ID 22  "+ task.getId());
            Log.d("TEST", "validate: ID 22  "+ date);
            int startTime = taskFormViewModel.getStartTimeHour()*60 + taskFormViewModel.getStartTimeMinute();
            int endTime = taskFormViewModel.getEndTimeHour()*60 + taskFormViewModel.getEndTimeMinute();
            if(startTime >= task.getStartTime() && startTime < task.getEndTime()){
                makeToast(this.getString(R.string.overlaping_tasks));
                return false;
            }
            if(endTime > task.getStartTime() && endTime <= task.getEndTime()){
                makeToast(this.getString(R.string.overlaping_tasks));
                return false;
            }
            if(startTime <= task.getStartTime() && endTime >= task.getEndTime()){
                makeToast(this.getString(R.string.overlaping_tasks));
                return false;
            }
        }
        return true;
    }

}
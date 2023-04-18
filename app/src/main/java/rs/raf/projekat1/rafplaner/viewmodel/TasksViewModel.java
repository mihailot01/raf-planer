package rs.raf.projekat1.rafplaner.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import rs.raf.projekat1.rafplaner.AppModule;
import rs.raf.projekat1.rafplaner.model.Task;

public class TasksViewModel extends AndroidViewModel{

    public TasksViewModel(Application application) {
        super(application);
        tasksLiveData.setValue(AppModule.getInstance(getApplication()).getDb().getAllTasks());
        Log.d("TEST", "TasksViewModel: " + AppModule.getInstance(getApplication()).getDb().getAllTasks().size());
    }
    private final MutableLiveData<List<Task>> tasksLiveData = new MutableLiveData<>();

    public LiveData<List<Task>> getTasks() {
        return tasksLiveData;
    }

}

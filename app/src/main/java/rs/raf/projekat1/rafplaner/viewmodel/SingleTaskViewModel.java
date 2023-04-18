package rs.raf.projekat1.rafplaner.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import rs.raf.projekat1.rafplaner.AppModule;
import rs.raf.projekat1.rafplaner.model.Task;

public class SingleTaskViewModel extends ViewModel {

    private final MutableLiveData<Task> taskLiveData = new MutableLiveData<>();

    public LiveData<Task> getTask() {
        return taskLiveData;
    }

    public void setTask(Task task) {
        taskLiveData.setValue(task);
    }

}

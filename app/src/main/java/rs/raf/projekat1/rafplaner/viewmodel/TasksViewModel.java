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

public class TasksViewModel extends ViewModel{

    private final MutableLiveData<List<Task>> dailyTasksLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Task>> filteredTasksLiveData = new MutableLiveData<>();

    public LiveData<List<Task>> getTasks() {
        return dailyTasksLiveData;
    }

    public void setTasks(List<Task> tasks) {
        dailyTasksLiveData.setValue(tasks);
    }

    public void addTask(Task task) {
        dailyTasksLiveData.getValue().add(task);
        dailyTasksLiveData.setValue(dailyTasksLiveData.getValue());
    }

    public void removeTask(Task task) {
        dailyTasksLiveData.getValue().remove(task);
        dailyTasksLiveData.setValue(dailyTasksLiveData.getValue());
    }

    public void updateTask(Task task) {
        updateInList(dailyTasksLiveData, task);
        updateInList(filteredTasksLiveData, task);

    }

    private void updateInList(MutableLiveData<List<Task>> list, Task task){
        for (int i = 0; i < list.getValue().size(); i++) {
            if (list.getValue().get(i).getId() == task.getId()) {
                list.getValue().set(i, task);
                list.setValue(list.getValue());
                return;
            }
        }
    }

    public LiveData<List<Task>> getFilteredTasks() {
        return filteredTasksLiveData;
    }

    public void setFilteredTasks(List<Task> tasks) {
        filteredTasksLiveData.setValue(tasks);
    }


}

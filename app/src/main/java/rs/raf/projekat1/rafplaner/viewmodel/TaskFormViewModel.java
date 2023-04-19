package rs.raf.projekat1.rafplaner.viewmodel;

import androidx.databinding.InverseMethod;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import rs.raf.projekat1.rafplaner.model.Priority;
import rs.raf.projekat1.rafplaner.model.Task;

public class TaskFormViewModel extends ViewModel {

    private MutableLiveData<String> title = new MutableLiveData<>("");
    private MutableLiveData<String> description = new MutableLiveData<>("");

    private MutableLiveData<Integer> startTimeHour = new MutableLiveData<>(0);
    private MutableLiveData<Integer> startTimeMinute = new MutableLiveData<>(0);

    private MutableLiveData<Integer> endTimeHour = new MutableLiveData<>(0);
    private MutableLiveData<Integer> endTimeMinute = new MutableLiveData<>(0);

    private MutableLiveData<Priority> priority = new MutableLiveData<>(Priority.LOW);

    public String getTitle() {
        return title.getValue();
    }

    public void setTitle(String t) {
        title.setValue(t);
    }

    public String getDescription() {
        return description.getValue();
    }
    public void setDescription(String d) {
        description.setValue(d);
    }

    public Integer getStartTimeHour() {
        return startTimeHour.getValue();
    }

    public void setStartTimeHour(Integer h) {
        startTimeHour.setValue(h);
    }

    public Integer getStartTimeMinute() {
        return startTimeMinute.getValue();
    }

    public void setStartTimeMinute(Integer m) {
        startTimeMinute.setValue(m);
    }

    public Integer getEndTimeHour() {
        return endTimeHour.getValue();
    }

    public void setEndTimeHour(Integer h) {
        endTimeHour.setValue(h);
    }

    public Integer getEndTimeMinute() {
        return endTimeMinute.getValue();
    }

    public void setEndTimeMinute(Integer m) {
        endTimeMinute.setValue(m);
    }


    public Priority getPriority() {
        return priority.getValue();
    }

    public void setPriority(Priority p) {
        priority.setValue(p);
    }

}
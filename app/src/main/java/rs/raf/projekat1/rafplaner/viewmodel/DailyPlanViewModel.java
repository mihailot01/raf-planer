package rs.raf.projekat1.rafplaner.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import rs.raf.projekat1.rafplaner.model.Priority;

public class DailyPlanViewModel extends ViewModel {

    private final MutableLiveData<Boolean> lowPriorityMutableLiveData = new MutableLiveData<>(Boolean.TRUE);
    private final MutableLiveData<Boolean> mediumPriorityMutableLiveData = new MutableLiveData<>(Boolean.TRUE);
    private final MutableLiveData<Boolean> highPriorityMutableLiveData = new MutableLiveData<>(Boolean.TRUE);

    private final MutableLiveData<Boolean> showPastTasksMutableLiveData = new MutableLiveData<>(Boolean.TRUE);

    private final MutableLiveData<String> searchMutableLiveData = new MutableLiveData<>("");

    public LiveData<Boolean> getLowPriority() {
        return lowPriorityMutableLiveData;
    }

    public LiveData<Boolean> getMediumPriority() {
        return mediumPriorityMutableLiveData;
    }
    public LiveData<Boolean> getHighPriority() {
        return highPriorityMutableLiveData;
    }

    public void setLowPriority(Boolean lowPriority) {
        lowPriorityMutableLiveData.setValue(lowPriority);
    }

    public void setMediumPriority(Boolean mediumPriority) {
        mediumPriorityMutableLiveData.setValue(mediumPriority);
    }

    public void setHighPriority(Boolean highPriority) {
        highPriorityMutableLiveData.setValue(highPriority);
    }


    public LiveData<Boolean> getShowPastTasksLiveData() {
        return showPastTasksMutableLiveData;
    }

    public Boolean getShowPastTasks() {
        return showPastTasksMutableLiveData.getValue();
    }

    public void setShowPastTasks(Boolean showPastTasks) {
        showPastTasksMutableLiveData.setValue(showPastTasks);
    }

    public LiveData<String> getSearchLiveData() {
        return searchMutableLiveData;
    }

    public String getSearch() {
        return searchMutableLiveData.getValue();
    }

    public void setSearch(String search) {
        searchMutableLiveData.setValue(search);
    }

}

package rs.raf.projekat1.rafplaner.view;

import android.app.Application;
import android.content.DialogInterface;

import rs.raf.projekat1.rafplaner.AppModule;
import rs.raf.projekat1.rafplaner.model.Task;

public class DeleteDialogClickListener implements DialogInterface.OnClickListener{

    Application application;
    Task task;
    public DeleteDialogClickListener(Application application, Task task) {
        this.application = application;
        this.task = task;
    }
    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which == DialogInterface.BUTTON_POSITIVE) {
            AppModule.getInstance(application).getDb().deleteTask(task.getId());
            AppModule.getInstance(application).getDailyTasksViewModel().removeTask(task);
        } else if(which == DialogInterface.BUTTON_NEGATIVE) {
            return;
        }
    }
}

package rs.raf.projekat1.rafplaner.database;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rs.raf.projekat1.rafplaner.HelperFunctions;
import rs.raf.projekat1.rafplaner.model.Priority;
import rs.raf.projekat1.rafplaner.model.Task;

public class DatabaseManager {

    private SQLiteDatabase database;

    public DatabaseManager(Application application) {
        this.database = new DatabaseHelper(application).getWritableDatabase();
    }

    public void insertUser(String username, String email, String password) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseModel.UserEntry.COLUMN_USERNAME, username);
        contentValue.put(DatabaseModel.UserEntry.COLUMN_EMAIL, email);
        contentValue.put(DatabaseModel.UserEntry.COLUMN_PASSWORD, password);
        database.insert(DatabaseModel.UserEntry.TABLE_NAME, null, contentValue);
    }

    public void insertTask(Task task) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseModel.TaskEntry.COLUMN_TITLE, task.getTitle());
        contentValue.put(DatabaseModel.TaskEntry.COLUMN_DESCRIPTION, task.getDescription());
        contentValue.put(DatabaseModel.TaskEntry.COLUMN_DATE, HelperFunctions.trimDate(task.getDate()).getTime());
        contentValue.put(DatabaseModel.TaskEntry.COLUMN_START_TIME, task.getStartTime());
        contentValue.put(DatabaseModel.TaskEntry.COLUMN_END_TIME, task.getEndTime());
        contentValue.put(DatabaseModel.TaskEntry.COLUMN_PRIORITY, task.getPriority().toString());
        database.insert(DatabaseModel.TaskEntry.TABLE_NAME, null, contentValue);
    }


    public List<Task> getAllTasks() {


        Log.d("TEST", "getAllTasks: USO U METODU");
        List<Task> list = new ArrayList<>();

        //String[] columns = new String[]{DatabaseModel.TaskEntry._ID, DatabaseModel.TaskEntry.COLUMN_TITLE, DatabaseModel.TaskEntry.COLUMN_DESCRIPTION, DatabaseModel.TaskEntry.COLUMN_DATE, DatabaseModel.TaskEntry.COLUMN_START_TIME, DatabaseModel.TaskEntry.COLUMN_END_TIME, DatabaseModel.TaskEntry.COLUMN_PRIORITY};
        Cursor cursor = database.query(DatabaseModel.TaskEntry.TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Log.d("TEST", "getAllTasks: CURSOR Count: " + cursor.getCount());
        if (cursor == null || cursor.getCount() == 0)
        {
            Log.d("TEST", "getAllTasks: CURSOR NULL");
            insertMockTasks();
            Log.d("TEST", "getAllTasks: PROSO INSERT MOCK");
            return getAllTasks();
        }
        cursor.moveToFirst();
        do {
                list.add(getTaskFromCursor(cursor));
        }   while (cursor.moveToNext());



        cursor.close();
        return list;
    }
    @SuppressLint("Range")

    private Task getTaskFromCursor(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex(DatabaseModel.TaskEntry._ID));
        String title = cursor.getString(cursor.getColumnIndex(DatabaseModel.TaskEntry.COLUMN_TITLE));
        String description = cursor.getString(cursor.getColumnIndex(DatabaseModel.TaskEntry.COLUMN_DESCRIPTION));
        Date date = new Date(cursor.getLong(cursor.getColumnIndex(DatabaseModel.TaskEntry.COLUMN_DATE)));
        int startTime = cursor.getInt(cursor.getColumnIndex(DatabaseModel.TaskEntry.COLUMN_START_TIME));
        int endTime = cursor.getInt(cursor.getColumnIndex(DatabaseModel.TaskEntry.COLUMN_END_TIME));
        Priority priority = Priority.valueOf(cursor.getString(cursor.getColumnIndex(DatabaseModel.TaskEntry.COLUMN_PRIORITY)));
        Log.d("TEST", "getTaskFromCursor: "+title);
        return new Task(id, title, description, date, startTime, endTime, priority);
    }

    public void insertMockTasks(){

        List<Task> list = new ArrayList<>();
        list.add(new Task(1,"low task", "description", new Date(), 10,100, Priority.LOW));
        list.add(new Task(3,"low task 2", "description", new Date(), 10,100, Priority.LOW));
        list.add(new Task(2,"high task", "description2", new Date(), 10,100, Priority.HIGH));
        list.add(new Task(4,"high task 2", "description2", new Date(), 10,100, Priority.HIGH));
        list.add(new Task(5,"medium task", "description", new Date(), 10,100, Priority.MEDIUM));
        for(Task task : list){
            insertTask(task);
        }
    }
    public Task getTaskById(int id) {
        Cursor cursor = database.query(DatabaseModel.TaskEntry.TABLE_NAME, null, DatabaseModel.TaskEntry._ID+" = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;

        return getTaskFromCursor(cursor);

    }

    public List<Task> getTasksByDate(Date date) {
        List<Task> list = new ArrayList<>();
        Cursor cursor = database.query(DatabaseModel.TaskEntry.TABLE_NAME, null, DatabaseModel.TaskEntry.COLUMN_DATE+" = ?", new String[]{String.valueOf(HelperFunctions.trimDate(date).getTime())}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        if (cursor == null || cursor.getCount() == 0)
        {
            return list;
        }
        cursor.moveToFirst();
        do {
            list.add(getTaskFromCursor(cursor));
        }   while (cursor.moveToNext());

        cursor.close();
        return list;
    }

    public void deleteTask(int id) {
        database.delete(DatabaseModel.TaskEntry.TABLE_NAME, DatabaseModel.TaskEntry._ID+" = ?", new String[]{String.valueOf(id)});
    }

    public void updateTask(Task task) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseModel.TaskEntry.COLUMN_TITLE, task.getTitle());
        contentValue.put(DatabaseModel.TaskEntry.COLUMN_DESCRIPTION, task.getDescription());
        contentValue.put(DatabaseModel.TaskEntry.COLUMN_DATE, HelperFunctions.trimDate(task.getDate()).getTime());
        contentValue.put(DatabaseModel.TaskEntry.COLUMN_START_TIME, task.getStartTime());
        contentValue.put(DatabaseModel.TaskEntry.COLUMN_END_TIME, task.getEndTime());
        contentValue.put(DatabaseModel.TaskEntry.COLUMN_PRIORITY, task.getPriority().toString());
        database.update(DatabaseModel.TaskEntry.TABLE_NAME, contentValue, DatabaseModel.TaskEntry._ID+" = ?", new String[]{String.valueOf(task.getId())});
    }

}

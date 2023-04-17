package rs.raf.projekat1.rafplaner.database;

import android.app.Application;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

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


}

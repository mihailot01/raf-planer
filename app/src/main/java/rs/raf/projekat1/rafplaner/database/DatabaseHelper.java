package rs.raf.projekat1.rafplaner.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="rafplaner.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_GROCERYLIST_TABLE = "CREATE TABLE " +

                DatabaseModel.UserEntry.TABLE_NAME + " (" +
                DatabaseModel.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseModel.UserEntry.COLUMN_USERNAME + " TEXT NOT NULL UNIQUE, " +
                DatabaseModel.UserEntry.COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, " +
                DatabaseModel.UserEntry.COLUMN_PASSWORD + " TEXT NOT NULL " +

                ");";

        db.execSQL(SQL_CREATE_GROCERYLIST_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseModel.UserEntry.TABLE_NAME);
        onCreate(db);
    }
}

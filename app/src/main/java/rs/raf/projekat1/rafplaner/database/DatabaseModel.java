package rs.raf.projekat1.rafplaner.database;

import android.provider.BaseColumns;

import java.sql.Time;
import java.util.Date;

import rs.raf.projekat1.rafplaner.model.Priority;

public class DatabaseModel {

    private DatabaseModel() {
    }
    public static final class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";
    }


    private String title;
    private String description;
    private Date date;
    private Time startTime;
    private Time endTime;
    private Priority priority;

    private int id;
    public static final class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "task";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_START_TIME = "start_time";
        public static final String COLUMN_END_TIME = "end_time";
        public static final String COLUMN_PRIORITY = "priority";
    }
}

package rs.raf.projekat1.rafplaner.database;

import android.provider.BaseColumns;

public class DatabaseModel {

    private DatabaseModel() {
    }
    public static final class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";
    }
}

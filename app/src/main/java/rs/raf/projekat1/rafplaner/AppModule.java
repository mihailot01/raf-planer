package rs.raf.projekat1.rafplaner;

import android.app.Application;

import rs.raf.projekat1.rafplaner.database.DatabaseManager;

public class AppModule {

    private static AppModule am;

    private final DatabaseManager db;

    public static AppModule getInstance(Application application) {
        if(am == null) {
            am = new AppModule(application);
        }
        return am;
    }

    public DatabaseManager getDb() {
        return db;
    }

    private AppModule(Application application) {
        db = new DatabaseManager(application);
    }

}

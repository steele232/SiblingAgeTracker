package android.steele.siblingagetracker.service;

/**
 * AppDataStore
 *
 * This is designed to be a Singleton
 * for storing our Store of Data
 * across the entire application.
 *
 * Created by jonathansteele on 1/30/18.
 */

public class AppDataStore {

    private static String appDataJson = null;

    private static AppDataStore single_instance = null;

    private AppDataStore() {
        //get the Data?




    }

    public static AppDataStore getInstance() {

        if(single_instance == null) {
            single_instance = new AppDataStore();
        }

        return single_instance;
    }


    // Save

    // Load

    // Load Asynchronously - for Init


    // Read / Find

    // Add

    // Update

    // Delete


}

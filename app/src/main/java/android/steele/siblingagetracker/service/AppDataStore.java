package android.steele.siblingagetracker.service;

import android.content.Context;
import android.steele.siblingagetracker.R;

import com.google.gson.Gson;

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
    private static AppData appData = null;

    private static AppDataStore single_instance = null;

    private AppDataStore(Context context) {
        //get the Data? YES
        //There may be problems, but we will ignore them for now.

        Gson gson = new Gson();

        String appDataJson = context
            .getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
            .getString(context.getString(R.string.title_main), "");

        if (appDataJson.isEmpty()) {
            appData = new AppData();
        } else {
            appData = gson.fromJson(appDataJson, AppData.class);
        }





    }

    public static AppDataStore getInstance(Context context) {

        if(single_instance == null) {
            single_instance = new AppDataStore(context);
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

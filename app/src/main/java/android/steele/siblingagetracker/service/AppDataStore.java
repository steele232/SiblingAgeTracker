package android.steele.siblingagetracker.service;

import android.content.Context;
import android.steele.siblingagetracker.R;
import android.support.v4.content.ContextCompat;

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

    // Singleton
    private static AppDataStore single_instance = null;

    private AppDataStore(Context context) {  }

    public static AppDataStore getInstance(Context context) {

        if(single_instance == null) {
            single_instance = new AppDataStore(context);
        }

        return single_instance;
    }

    // Load
    public static AppData load(Context context) {

        Gson gson = new Gson();

        String appDataJson = context
                .getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                .getString(context.getString(R.string.title_main), "");

        if (appDataJson.isEmpty()) {
            return new AppData();
        } else {
            return gson.fromJson(appDataJson, AppData.class);
        }

    }

    // Save
    public static void save(Context context, AppData appData) {
        Gson gson = new Gson();
        context
            .getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
            .edit()
            .putString(context.getString(R.string.title_main), gson.toJson(appData))
            .apply();
    }




}

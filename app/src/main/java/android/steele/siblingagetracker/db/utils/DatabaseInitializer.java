package android.steele.siblingagetracker.db.utils;

import android.os.AsyncTask;
import android.steele.siblingagetracker.db.AppDatabase;
import android.steele.siblingagetracker.db.FamilyMember;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by jonathansteele on 3/15/18.
 */

public class DatabaseInitializer {

    private static String TAG = DatabaseInitializer.class.getSimpleName();

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }

    public static void populateAsync(AppDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase _db;

        public PopulateDbAsync(AppDatabase db) {
            _db = db;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            populateWithTestData(_db);
            return null;
        }


    }

    private static void populateWithTestData(AppDatabase db) {

        Log.i(TAG, "Starting to populate");
        db.familyMemberDAO().deleteAll();



        GregorianCalendar calendar = new GregorianCalendar();
        FamilyMember fm = new FamilyMember();
        fm.setBirthdate(calendar);
        fm.setName("Jonathan");

        db.familyMemberDAO().insertAll(fm);

        Log.i(TAG, "Should be finished populating");

    }




}

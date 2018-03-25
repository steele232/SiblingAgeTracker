package steele.apps.siblingagetracker.db.asynctasks;

import android.os.AsyncTask;
import steele.apps.siblingagetracker.db.AppDatabase;
import steele.apps.siblingagetracker.db.FamilyMember;
import android.util.Log;

/**
 * Created by jonathansteele on 3/22/18.
 */

public class UpdateFamilyMemberTask extends AsyncTask<Void, Void, Void> {

    private final String TAG = UpdateFamilyMemberTask.class.getSimpleName();

    private final AppDatabase _db;
    private final FamilyMember _fm;

    public UpdateFamilyMemberTask(AppDatabase db, FamilyMember familyMember) {
        Log.i(TAG, "Constructor called");
        _db = db;
        _fm = familyMember;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.i(TAG, "Do in background called");

        _db.familyMemberDAO().updateFamilyMembers(_fm);

        return null;
    }

}

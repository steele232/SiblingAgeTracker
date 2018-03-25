package steele.apps.siblingagetracker.db.asynctasks;

import android.os.AsyncTask;
import steele.apps.siblingagetracker.db.AppDatabase;
import steele.apps.siblingagetracker.db.FamilyMember;

/**
 * Created by jonathansteele on 3/17/18.
 */

public class InsertFamilyMemberTask extends AsyncTask<Void, Void, Void> {

    private final AppDatabase _db;
    private final FamilyMember _fm;

    public InsertFamilyMemberTask(AppDatabase db, FamilyMember familyMember) {
        _db = db;
        _fm = familyMember;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        _db.familyMemberDAO().insertAll(_fm);

        return null;
    }

}
package steele.apps.siblingagetracker.db.asynctasks;

import android.os.AsyncTask;
import steele.apps.siblingagetracker.db.AppDatabase;
import steele.apps.siblingagetracker.db.FamilyMember;

/**
 * Created by jonathansteele on 3/17/18.
 */

public class DeleteFamilyMemberTask extends AsyncTask<Void, Void, Void> {

    private final AppDatabase _db;
    private final FamilyMember _fm;

    public DeleteFamilyMemberTask(AppDatabase db, FamilyMember familyMemberToDelete) {
        _db = db;
        _fm = familyMemberToDelete;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        _db.familyMemberDAO().delete(_fm);

        return null;
    }

}

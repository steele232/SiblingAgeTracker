package android.steele.siblingagetracker.db.asynctasks;

import android.os.AsyncTask;
import android.steele.siblingagetracker.db.AppDatabase;
import android.steele.siblingagetracker.db.FamilyMember;

/**
 * Created by jonathansteele on 3/22/18.
 */

public class UpdateFamilyMemberTask extends AsyncTask<Void, Void, Void> {

    private final AppDatabase _db;
    private final FamilyMember _fm;

    public UpdateFamilyMemberTask(AppDatabase db, FamilyMember familyMember) {
        _db = db;
        _fm = familyMember;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        _db.familyMemberDAO().updateFamilyMembers(_fm);

        return null;
    }

}

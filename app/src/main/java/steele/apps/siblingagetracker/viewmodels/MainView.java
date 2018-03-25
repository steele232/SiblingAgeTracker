package steele.apps.siblingagetracker.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import steele.apps.siblingagetracker.db.AppDatabase;
import steele.apps.siblingagetracker.db.FamilyMember;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

/**
 * Created by jonathansteele on 2/27/18.
 */

public class MainView extends AndroidViewModel {

    private static final String TAG = MainView.class.getSimpleName();

    private AppDatabase _appDatabase;

    @Nullable
    private LiveData<List<FamilyMember>> _familyMembers = new MutableLiveData<>();

    public MainView(Application application) {
        super(application);

        Log.i(TAG, "MainView Constructor Called");

        _appDatabase = AppDatabase.getDatabase(this.getApplication());

        _familyMembers = _appDatabase.familyMemberDAO().sortByName();

    }

    private void populateDb() {
//        DatabaseInitializer.populateAsync(_appDatabase);
    }

    @Nullable
    public LiveData<List<FamilyMember>> getFamilyMembers() {
        Log.i(TAG, "setFamilyMembers called");
        return _familyMembers;
    }

    public void reloadSortingByAge() {
        _familyMembers = _appDatabase.familyMemberDAO().sortBySeniority();
    }

    public void reloadSortingByName() {
        _familyMembers = _appDatabase.familyMemberDAO().sortByName();
    }

}

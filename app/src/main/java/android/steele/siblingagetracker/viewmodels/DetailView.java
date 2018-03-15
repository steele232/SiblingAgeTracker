package android.steele.siblingagetracker.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.steele.siblingagetracker.db.AppDatabase;
import android.steele.siblingagetracker.db.FamilyMember;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.GregorianCalendar;

/**
 * Created by jonathansteele on 2/27/18.
 */

public class DetailView extends AndroidViewModel {

    private static final String TAG = DetailView.class.getSimpleName();

    private AppDatabase _appDatabase;

    @Nullable
    private MutableLiveData<FamilyMember> _thisFamilyMember = new MutableLiveData<>();

    private MutableLiveData<Boolean> _isInEdittingMode = new MutableLiveData<>();

    public DetailView(Application application) {
        super(application);

        _appDatabase = AppDatabase.getDatabase(this.getApplication());

        Log.i(TAG, "Default Constructor called");
        _isInEdittingMode.postValue(false);
        _thisFamilyMember.postValue(new FamilyMember(
                "John",
                new GregorianCalendar()
        ));
    }

    public DetailView(Application application, FamilyMember fm, boolean isEdittingNotAdding) {
        super(application);

        _appDatabase = AppDatabase.getDatabase(this.getApplication());

        Log.i(TAG, "Non-Default Constructor called");
        _thisFamilyMember.postValue(fm);
        _isInEdittingMode.postValue(isEdittingNotAdding);
    }


    @Nullable
    public MutableLiveData<FamilyMember> getFamilyMember() {
        Log.i(TAG, "GetFamily called");
        return _thisFamilyMember;
    }

    @Nullable
    public MutableLiveData<Boolean> getIsEdittingMode() {
        Log.i(TAG, "Get IsInEdittingMode called");
        return _isInEdittingMode;
    }

    public void saveNewFamilyMember(FamilyMember newFamilyMember) {
        _appDatabase.familyMemberDAO().insertAll(newFamilyMember);
    }
}

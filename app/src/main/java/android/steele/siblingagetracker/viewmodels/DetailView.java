package android.steele.siblingagetracker.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
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
    private LiveData<FamilyMember> _thisFamilyMember = new MutableLiveData<>();

    private MutableLiveData<Boolean> _isInEdittingMode = new MutableLiveData<>();

    public DetailView(Application application) {
        super(application);

        _appDatabase = AppDatabase.getDatabase(this.getApplication());

        Log.i(TAG, "Default Constructor called");
        _isInEdittingMode.postValue(false);
    }


    @Nullable
    public LiveData<FamilyMember> getFamilyMember() {
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

    public void startEditingFamilyMemberWithID(int keyToEdit) {
        // TODO Get Family Member by Id... Make sure it works.
        // set the FM.
        _thisFamilyMember = _appDatabase.familyMemberDAO().getFamilyMemberByID(keyToEdit);

        //DON'T set editting mode because it's already been set in the callback that its the only usage of this function.
        // TODO Actually do set the editting value.. Change in architecture.
        _isInEdittingMode.postValue(true);
    }

    public void startAddingFamilyMember() {
        //TODO
        FamilyMember fm = new FamilyMember();
        fm.setName("");
        fm.setBirthdate(new GregorianCalendar());
        ((MutableLiveData<FamilyMember>)_thisFamilyMember).postValue(fm);
        _isInEdittingMode.postValue(false);
    }
}

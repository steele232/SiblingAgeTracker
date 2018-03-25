package steele.apps.siblingagetracker.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import steele.apps.siblingagetracker.db.AppDatabase;
import steele.apps.siblingagetracker.db.FamilyMember;
import steele.apps.siblingagetracker.db.asynctasks.DeleteFamilyMemberTask;
import steele.apps.siblingagetracker.db.asynctasks.InsertFamilyMemberTask;
import steele.apps.siblingagetracker.db.asynctasks.UpdateFamilyMemberTask;
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

    public void startEditingFamilyMemberWithID(int keyToEdit) {
        Log.i(TAG, "I'm starting to edit a family member");
        // Get Family Member by Id
        // set the FM & Editing Mode

        _thisFamilyMember = _appDatabase.familyMemberDAO().getFamilyMemberByID(keyToEdit);

        _isInEdittingMode.postValue(true);
    }

    public void startAddingFamilyMember() {
        Log.i(TAG, "I'm starting to add a family member");

        //set up a fresh blank Family Member.
        FamilyMember fm = new FamilyMember();
        fm.setName("");
        fm.setBirthdate(new GregorianCalendar());

        _thisFamilyMember = new MutableLiveData<>();
        ((MutableLiveData<FamilyMember>) _thisFamilyMember).postValue(fm);

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

    public void saveNewFamilyMember() {
        Log.i(TAG, "I'm starting to save a new family member");
        InsertFamilyMemberTask task = new InsertFamilyMemberTask(
                _appDatabase,
                _thisFamilyMember.getValue()
        );
        task.execute();
    }

    public void updateFamilyMember() {
        Log.i(TAG, "I'm starting to update a family member");
        UpdateFamilyMemberTask task = new UpdateFamilyMemberTask(
                _appDatabase,
                _thisFamilyMember.getValue()
        );
        task.execute();
    }

    public void deleteFamilyMember() {
        Log.i(TAG, "I'm starting to delete a family member");
        DeleteFamilyMemberTask task = new DeleteFamilyMemberTask(
                _appDatabase,
                _thisFamilyMember.getValue()
        );
        task.execute();
    }
}

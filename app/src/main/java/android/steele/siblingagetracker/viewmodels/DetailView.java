package android.steele.siblingagetracker.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.steele.siblingagetracker.model.FamilyMember;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by jonathansteele on 2/27/18.
 */

public class DetailView extends ViewModel {

    private static final String TAG = DetailView.class.getSimpleName();

    @Nullable
    private MutableLiveData<FamilyMember> _thisFamilyMember;

    public DetailView() {
        Log.i(TAG, "Default Constructor called");
    }

    public DetailView(FamilyMember fm) {
        Log.i(TAG, "Non-Default Constructor called");
        _thisFamilyMember.postValue(fm);
    }

    @Nullable
    public MutableLiveData<FamilyMember> getFamilyMember() {
        Log.i(TAG, "GetFamily called");
        return _thisFamilyMember;
    }

    public void setFamilyMembers(final FamilyMember newFamilyMember) {
        Log.i(TAG, "SetFamily called");
        _thisFamilyMember.postValue(newFamilyMember);
    }




}

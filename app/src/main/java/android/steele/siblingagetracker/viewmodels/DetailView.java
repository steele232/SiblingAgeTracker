package android.steele.siblingagetracker.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.steele.siblingagetracker.model.FamilyMember;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.GregorianCalendar;

/**
 * Created by jonathansteele on 2/27/18.
 */

public class DetailView extends ViewModel {

    private static final String TAG = DetailView.class.getSimpleName();

    @Nullable
    private MutableLiveData<FamilyMember> _thisFamilyMember = new MutableLiveData<>();

    private MutableLiveData<Boolean> _isInEdittingMode = new MutableLiveData<>();

    public DetailView() {
        Log.i(TAG, "Default Constructor called");
        _isInEdittingMode.postValue(false);
        _thisFamilyMember.postValue(new FamilyMember(
                "John",
                new GregorianCalendar()
        ));
    }

//    public DetailView(FamilyMember fm) {
//        Log.i(TAG, "Non-Default Constructor called");
//        _thisFamilyMember.postValue(fm);
//        _isInEdittingMode.postValue(false);
//    }

    public DetailView(FamilyMember fm, boolean isEdittingNotAdding) {
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












}

package android.steele.siblingagetracker.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.steele.siblingagetracker.model.FamilyMember;
import android.steele.siblingagetracker.service.Mockstore;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by jonathansteele on 2/27/18.
 */

public class MainView extends ViewModel {

    private static final String TAG = MainView.class.getSimpleName();

    @Nullable
    private MutableLiveData<ArrayList<FamilyMember>> _familyMembers = new MutableLiveData<>();

    public MainView() {
        Log.i(TAG, "MainView Constructor Called");
        _familyMembers.postValue(
                Mockstore.getList()
        );
    }

    @Nullable
    public MutableLiveData<ArrayList<FamilyMember>> getFamilyMembers() {
        Log.i(TAG, "setFamilyMembers called");
        return _familyMembers;
    }

    public void setFamilyMembers(final ArrayList<FamilyMember> newList) {
        Log.i(TAG, "setFamilyMembers called");
        _familyMembers.postValue(newList);
    }


}

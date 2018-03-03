package android.steele.siblingagetracker.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.steele.siblingagetracker.model.FamilyMember;
import android.steele.siblingagetracker.service.Mockstore;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by jonathansteele on 2/27/18.
 */

public class MainView extends ViewModel {

    private static final int ONE_SECOND = 1000;

    @Nullable
    private MutableLiveData<ArrayList<FamilyMember>> _familyMembers = new MutableLiveData<>();

    public MainView() {
        _familyMembers.postValue(
                Mockstore.getList()
        );


    }

    @Nullable
    public MutableLiveData<ArrayList<FamilyMember>> getFamilyMembers() {
        return _familyMembers;
    }

    public void setFamilyMembers(final ArrayList<FamilyMember> newList) {
        _familyMembers.postValue(newList);
    }


}

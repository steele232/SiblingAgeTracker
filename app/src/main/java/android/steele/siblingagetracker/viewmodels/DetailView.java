package android.steele.siblingagetracker.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.steele.siblingagetracker.model.FamilyMember;
import android.support.annotation.Nullable;

/**
 * Created by jonathansteele on 2/27/18.
 */

public class DetailView extends ViewModel {


    @Nullable
    private MutableLiveData<FamilyMember> _thisFamilyMember;

    @Nullable
    public MutableLiveData<FamilyMember> getFamilyMember() {
        return _thisFamilyMember;
    }

    public void setFamilyMembers(final FamilyMember newFamilyMember) {
        _thisFamilyMember.postValue(newFamilyMember);
    }




}

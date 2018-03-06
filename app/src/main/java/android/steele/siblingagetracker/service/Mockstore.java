package android.steele.siblingagetracker.service;

import android.steele.siblingagetracker.model.FamilyMember;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by jonathansteele on 2/22/18.
 */

public class Mockstore {

    Mockstore() {}

    public static ArrayList<FamilyMember> getList() {
        ArrayList<FamilyMember> familyMembers = new ArrayList<>();
        GregorianCalendar calendar = new GregorianCalendar();
        FamilyMember fm = new FamilyMember();
        fm.name = "Jonathan";
        fm.birthdate = calendar;
        familyMembers.add(fm);

        fm = new FamilyMember();
        fm.name = "Sara";
        fm.birthdate = calendar;
        familyMembers.add(fm);

        fm = new FamilyMember();
        fm.name = "Jonny";
        fm.birthdate = calendar;
        familyMembers.add(fm);
        familyMembers.add(fm);

        return familyMembers;
    }
}

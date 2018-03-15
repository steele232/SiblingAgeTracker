package android.steele.siblingagetracker.service;

import android.steele.siblingagetracker.model.FamilyMember;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by jonathansteele on 2/22/18.
 */

public class Mockstore {

    Mockstore() {}

    public static List<FamilyMember> getList() {
        ArrayList<FamilyMember> familyMembers = new ArrayList<>();
        GregorianCalendar calendar = new GregorianCalendar();
        FamilyMember fm = new FamilyMember();
        fm.name = "Jonathan";
        calendar.set(Calendar.YEAR, 1990);
        fm.birthdate = calendar;
        familyMembers.add(fm);

        fm = new FamilyMember();
        fm.name = "Sara";
        calendar = new GregorianCalendar();
        fm.birthdate = calendar;
        familyMembers.add(fm);

        fm = new FamilyMember();
        fm.name = "Jonny";
        calendar = new GregorianCalendar(1998, 03, 03);
        fm.birthdate = calendar;
        familyMembers.add(fm);
        familyMembers.add(fm);

        return familyMembers;
    }
}

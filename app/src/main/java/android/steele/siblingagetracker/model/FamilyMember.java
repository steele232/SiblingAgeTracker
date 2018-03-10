package android.steele.siblingagetracker.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by jonathansteele on 8/14/17.
 */

public class FamilyMember {

    public FamilyMember() {}

    public FamilyMember(String name, GregorianCalendar birthdate) {
        this.name = name;
        this.birthdate = birthdate;
    }

    public String name;
    public GregorianCalendar birthdate;
}

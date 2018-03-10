package android.steele.siblingagetracker.model;

import android.arch.persistence.room.Entity;

import java.util.GregorianCalendar;

/**
 * Created by jonathansteele on 8/14/17.
 */
@Entity
public class FamilyMember {

    public int uid;
    public String name;
    public GregorianCalendar birthdate;


    public FamilyMember() {}

    public FamilyMember(int uid, String name, GregorianCalendar birthdate) {
        this.uid = uid;
        this.name = name;
        this.birthdate = birthdate;
    }

    public FamilyMember(String name, GregorianCalendar birthdate) {
        this.name = name;
        this.birthdate = birthdate;
    }


}

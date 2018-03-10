package android.steele.siblingagetracker.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.GregorianCalendar;

/**
 * Created by jonathansteele on 8/14/17.
 */
@Entity
public class FamilyMember {

    @PrimaryKey
    private int uid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "birthdate")
    private GregorianCalendar birthdate;

    /*
    Getters
     */

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GregorianCalendar getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(GregorianCalendar birthdate) {
        this.birthdate = birthdate;
    }




    public FamilyMember() {}

    public FamilyMember(String name, GregorianCalendar birthdate) {
        this.name = name;
        this.birthdate = birthdate;
    }

}

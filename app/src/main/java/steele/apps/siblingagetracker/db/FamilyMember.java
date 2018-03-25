package steele.apps.siblingagetracker.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.GregorianCalendar;

/**
 * Created by jonathansteele on 8/14/17.
 */
@Entity
public class FamilyMember {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    private int uid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "birthdate")
    @TypeConverters({GregCalendarConverter.class})
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

    @Ignore
    public FamilyMember(String name, GregorianCalendar birthdate) {
        this.name = name;
        this.birthdate = birthdate;
    }

}

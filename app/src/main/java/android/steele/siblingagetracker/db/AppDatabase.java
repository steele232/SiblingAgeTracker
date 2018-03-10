package android.steele.siblingagetracker.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by jonathansteele on 3/10/18.
 */
@Database(entities = {FamilyMember.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FamilyMemberDAO familyMemberDAO();

}

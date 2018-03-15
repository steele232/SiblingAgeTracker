package android.steele.siblingagetracker.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by jonathansteele on 3/10/18.
 */
@Database(entities = {FamilyMember.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FamilyMemberDAO familyMemberDAO();

    //TODO Set this up as a singleton

    private static AppDatabase _INSTANCE;

    public static AppDatabase getDatabase(Context context) {

        if (_INSTANCE == null) {
            _INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "mydb"
                )
                    .allowMainThreadQueries()
                    .build();
        }

        return _INSTANCE;
    }

}

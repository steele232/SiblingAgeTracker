package android.steele.siblingagetracker.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by jonathansteele on 3/10/18.
 */

@Dao
public interface FamilyMemberDAO {

    @Query("SELECT * FROM familymember")
    LiveData<List<FamilyMember>> getAll();

    @Query("SELECT * FROM familymember WHERE uid IN (:familyIds)")
    LiveData<List<FamilyMember>> loadAllByIds(int[] familyIds);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(FamilyMember... familyMembers);

    @Delete
    void delete(FamilyMember... familyMembers);

    @Update
    void updateFamilyMembers(FamilyMember... familyMembers);

}

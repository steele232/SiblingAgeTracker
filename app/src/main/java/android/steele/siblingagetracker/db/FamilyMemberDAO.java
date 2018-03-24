package android.steele.siblingagetracker.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
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
    LiveData<FamilyMember> getFamilyMemberByID(int familyIds);

    @Query("SELECT * FROM familymember WHERE uid IN (:familyIds)")
    LiveData<List<FamilyMember>> getFamilyMembersByID(int[] familyIds);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(FamilyMember... familyMembers);

    @Delete
    void delete(FamilyMember... familyMembers);

    @Query("DELETE FROM familymember")
    void deleteAll();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFamilyMembers(FamilyMember... familyMembers);

}

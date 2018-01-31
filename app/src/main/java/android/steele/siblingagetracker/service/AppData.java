package android.steele.siblingagetracker.service;

import android.steele.siblingagetracker.model.FamilyMember;
import android.steele.siblingagetracker.model.User;
import android.steele.siblingagetracker.model.UserPreferences;

import java.util.ArrayList;

/**
 *
 * ONE AppData per user || mobile device.
 *
 * This one is responsible for initializing
 * variables in the User
 *
 * Created by jonathansteele on 1/30/18.
 */

public class AppData {

    public User user;


    public AppData() {
        user = new User();
        user.familyMemberList = new ArrayList<>();
        user.userPreferences = new UserPreferences();
    }




}

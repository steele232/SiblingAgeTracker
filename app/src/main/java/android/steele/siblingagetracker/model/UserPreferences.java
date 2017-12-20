package android.steele.siblingagetracker.model;

/**
 * Created by jonathansteele on 8/14/17.
 */

public class UserPreferences {
    public boolean isActivated;
    public boolean thatDay;
    public boolean dayBefore1;
    public boolean dayBefore2;
    public boolean dayBefore3;
    public boolean wkBefore1;
    public boolean wkBefore2;
    public boolean monthBefore1;
    public boolean monthBefore2;


    /**
     * This default constructor has implications in
     * the onCreate hook for SettingsActivity
     */

    public UserPreferences() {
        isActivated = false;
        thatDay = false;
        dayBefore1 = false;
        dayBefore2 = false;
        dayBefore3 = false;
        wkBefore1 = false;
        wkBefore2 = false;
        monthBefore1 = false;
        monthBefore2 = false;
    }

    @Override
    public String toString() {
        return "USER PREFERENCES: " +
                " isActivated: " + isActivated +
                " thatDay: " + thatDay +
                " dayBefore1: " + dayBefore1 +
                " dayBefore2: " + dayBefore2 +
                " dayBefore3: " + dayBefore3 +
                " wkBefore1: " + wkBefore1 +
                " wkBefore1: " + wkBefore2 +
                " monthBefore1: " + monthBefore1 +
                " monthBefore2: " + monthBefore2 + "  " +
                ""
                ;

    }
}

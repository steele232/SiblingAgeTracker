package android.steele.siblingagetracker.service;

import java.util.Calendar;

/**
 * Created by jonathansteele on 1/30/18.
 */

public class AgeCalculator {

    public static int calculateAge(int birthYear, int birthMonth, int birthDay ) {

        /**
         * CALCULATE THE AGE OF THE FAMILY MEMBER
         */
        final Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int currentMonth = now.get(Calendar.MONTH);
        int currentDay = now.get(Calendar.DAY_OF_MONTH);

        //calculating the differences
        int yearDifference = currentYear - birthYear;
        int monthDifference = currentMonth - birthMonth;
        int dayDifference = currentDay - birthDay;

        //whether the last year should be counted...
        //if we've past the birthday of the year, we count the last year.
        //if we've NOT past the birthday of the year, we do not count the last year.
        int negativeOffset = 1;
        //we will subtract 1 from the yearDifference (to end up with the age in years) if we haven't come upon the birthday yet.
        if (
                birthMonth < currentMonth ||
                        (
                                currentMonth == birthMonth &&
                                        currentDay >= birthDay
                        )
                )
        {
            //we HAVE passed the birthDay this year, so we will NOT subtract 1 from the yearDifference.
            //If so, the yearDifference IS the age.
            negativeOffset = 0;
        }
        int age = yearDifference - negativeOffset;
        return age;
    }



}

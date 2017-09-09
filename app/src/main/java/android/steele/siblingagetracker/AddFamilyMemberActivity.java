package android.steele.siblingagetracker;

import android.content.Intent;
import android.graphics.Color;
import android.steele.siblingagetracker.android.steele.siblingagetracker.model.FamilyMember;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.GregorianCalendar;

public class AddFamilyMemberActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int MAX_YEAR = 2200;
    public static final int MIN_YEAR = 1600;
    private boolean inputIsValidated = false;
    private EditText editName;
    private EditText editMonth;
    private EditText editDay;
    private EditText editYear;
    private Button buttonSubmit;
    private int nextFamilyMemberIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family_member);

        this.setTitle("Add New Sibling");

        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(this);

        editName = (EditText) findViewById(R.id.editName);

        editMonth = (EditText) findViewById(R.id.editMonth);
        editDay = (EditText) findViewById(R.id.editDay);
        editYear = (EditText) findViewById(R.id.editYear);

        /**
         * Checking the year input is extracted to the checkInputs function
         */
        editMonth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
                if (editMonth.getText().toString().length() == 2) {
                    editDay.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /**
         * Checking the year input is extracted to the checkInputs function
         */
        editDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
                if (editDay.getText().toString().length() == 2) {
                    editYear.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /**
         * Checking the year input is extracted to the checkInputs function
         */
        editYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
//                if (editYear.getText().toString().length() == 4) {
//                    buttonSubmit.requestFocus();
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user2");
        Log.d("TAG", userRef.getKey().toString());

        DatabaseReference familyMembersRef = userRef.child("familyMembers");
        /**
         * https://firebase.google.com/docs/database/android/read-and-write
         * You can use the onDataChange() method to read a static snapshot
         * of the contents at a given path, as they existed at the time of
         * the event. This method is !!**triggered once when the listener is
         * attached**!! and again every time the data, including children,
         * changes.
         * ...
         * In some cases you may want a callback to be called once and then
         * immediately removed, such as when initializing a UI element that
         * you don't expect to change. You can use the addListenerForSingleValueEvent()
         * method to simplify this scenario: it triggers once and then does
         * not trigger again.
         */
        familyMembersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int childrentCount = (int) dataSnapshot.getChildrenCount();
                int greatestIndex = -1;
                for (int i = 0; i < childrentCount; i++) {
                    if (dataSnapshot.child("familyMember" + i).exists()) {
                        greatestIndex = i;
                    }
                }
                nextFamilyMemberIndex = greatestIndex + 1;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /**
     *  SET THE $ CHANGE DUE TO THE CUSTOMER ($PAID - $PRICE = $CHANGEDUE)
     */
    public void checkInputs() {

        boolean monthIsValid = false;
        boolean dayIsValid = false;
        boolean yearIsValid = false;


        /**
         * Check the month
         */
        String monthString = editMonth.getText().toString();
        Integer monthInt = 0;
        try {
            //get it to an int
            monthInt = Integer.parseInt(monthString);
            //check if it's within the proper range
            monthIsValid = (monthInt < 13 && monthInt > 0);
        } catch (Exception ex) {
            monthIsValid = false;
            monthInt = 0;
        }

        /**
         * Check the year (PLACED IN CODE BEFORE DAY BECAUSE THE YEAR IS USED FOR LEAPYEARS..)
         */
        int yearInt = 0;
        String yearString = editYear.getText().toString();
        try {
            //get it to an int
            yearInt = Integer.parseInt(yearString);
            //check if it's within the proper range
            yearIsValid = (yearInt < MAX_YEAR && yearInt > MIN_YEAR);
        } catch (Exception ex) {
            yearIsValid = false;
        }

        /**
         * Check the day
         */
        String dayString = editDay.getText().toString();
        try {

            int maxDay = 31;
            //if it's worth it to make the month match..
            if (monthInt != 0 && monthIsValid) {
                switch (monthInt)
                {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        maxDay = 31;
                        break;

                    case 6:
                    case 4:
                    case 9:
                    case 11:
                        maxDay = 30;
                        break;

                    case 2:
                        //determine if it's a leapyear

                        //if the year is yet determinable from the year user input
                        if (monthInt != 0 && monthIsValid) {
                            //if it's a leapyear
                            if (yearInt % 4 == 0) {
                                maxDay = 29;
                            } else {
                                maxDay = 28;
                            }
                        } else {
                            maxDay = 28;
                        }
                        break;

                    default:
                        break;
                }
            }

            //get it to an int
            int dayInt = Integer.parseInt(dayString);
            //check if it's within the proper range
            dayIsValid = (dayInt <= maxDay && dayInt > 0);
        } catch (Exception ex) {
            dayIsValid = false;
        }

        //conclusion
        inputIsValidated = (monthIsValid && dayIsValid && yearIsValid);

        showUserValidInputs(monthIsValid, dayIsValid, yearIsValid);

    }

    public void showUserValidInputs(boolean monthIsValid, boolean dayIsValid, boolean yearIsValid) {

        //source of rgb codes: http://www.rapidtables.com/web/color/RGB_Color.htm
            //firebrick : 178,34,34
            //forest green : 34,139,34
        //lime green : 50,205,50
            //dark sea green : 143,188,143
        //rosy brown : 188,143,143
        //slate gray : 112,128,144


        //month
        if (monthIsValid) {
            editMonth.setBackgroundColor(Color.argb(128, 50,205,50));
        } else {
            editMonth.setBackgroundColor(Color.argb(128, 188,143,143));
        }

        //day
        if (dayIsValid) {
            editDay.setBackgroundColor(Color.argb(128, 50,205,50));
        } else {
            editDay.setBackgroundColor(Color.argb(128, 188,143,143));
        }

        //year
        if (yearIsValid) {
            editYear.setBackgroundColor(Color.argb(128, 50,205,50));
        } else {
            editYear.setBackgroundColor(Color.argb(128, 188,143,143));
        }

    }

    @Override
    public void onClick(View v) {
        if (inputIsValidated)
        {
            String nameString = editName.getText().toString();

            String monthString = editMonth.getText().toString();
            String dayString = editDay.getText().toString();
            String yearString = editYear.getText().toString();
            int monthInt = Integer.parseInt(monthString);
            int dayInt = Integer.parseInt(dayString);
            int yearInt = Integer.parseInt(yearString);
            GregorianCalendar birthdate = new GregorianCalendar();
            birthdate.set(yearInt, monthInt, dayInt); //year, month, date

            FamilyMember newFM = new FamilyMember();
            newFM.birthdate = birthdate;
            newFM.name = nameString;

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference userRef = database.getReference("user2");
            Log.d("TAG", userRef.getKey().toString());

            //call a function to get the next familymember index...
//            int nextIndex = getNextFamilyMemberIndex();

            DatabaseReference familyMembersRef = userRef.child("familyMembers");

            DatabaseReference newFamilyMemberRef = familyMembersRef.child("familyMember" + nextFamilyMemberIndex);
            DatabaseReference newNameRef = newFamilyMemberRef.child("name");
            newNameRef.setValue(newFM.name);

            DatabaseReference newBirthdate = newFamilyMemberRef.child("birthdate");
            Gson gson = new Gson();
            String birthDateString = gson.toJson(newFM.birthdate);
            newBirthdate.setValue(birthDateString);

            Intent intent = new Intent(AddFamilyMemberActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }



}

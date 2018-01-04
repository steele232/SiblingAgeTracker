package android.steele.siblingagetracker;

import android.graphics.Color;
import android.steele.siblingagetracker.model.FamilyMember;
import android.support.v4.content.ContextCompat;
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

public class AddFamilyMemberActivity extends AppCompatActivity {

    private static final String TAG = AddFamilyMemberActivity.class.getSimpleName();

    public static final int MAX_YEAR = 2200;
    public static final int MIN_YEAR = 1600;
    private boolean inputIsValidated = false;
    private EditText editNameField;
    private EditText editMonthField;
    private EditText editDayField;
    private EditText editYearField;
    private Button buttonSubmit;
    private int nextFamilyMemberIndex;
    private String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family_member);

        username = getIntent().getStringExtra("username");

        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
//        buttonSubmit.setOnClickListener(this);

        editNameField = (EditText) findViewById(R.id.editName);

        editMonthField = (EditText) findViewById(R.id.editMonth);
        editDayField = (EditText) findViewById(R.id.editDay);
        editYearField = (EditText) findViewById(R.id.editYear);

        /**
         * Checking the year input is extracted to the checkInputs function
         */
        editMonthField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
                if (editMonthField.getText().toString().length() == 2) {
                    editDayField.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /**
         * Checking the year input is extracted to the checkInputs function
         */
        editDayField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
                if (editDayField.getText().toString().length() == 2) {
                    editYearField.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /**
         * Checking the year input is extracted to the checkInputs function
         */
        editYearField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
//                if (editYearField.getText().toString().length() == 4) {
//                    buttonSubmit.requestFocus();
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference(username);
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
                nextFamilyMemberIndex = -1;
                for (int i = 0; i < childrentCount; i++) {
                    if (dataSnapshot.child("familyMember" + i).exists()) {
                        greatestIndex = i;
                    } else {
                        nextFamilyMemberIndex = i;
                    }
                }
                if (nextFamilyMemberIndex == -1) {
                    nextFamilyMemberIndex = greatestIndex + 1;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void checkInputs() {

        boolean monthInputIsValid = false;
        boolean dayInputIsValid = false;
        boolean yearInputIsValid = false;


        /**
         * Check the month
         */
        String monthString = editMonthField.getText().toString();
        Integer monthInt = 0;
        try {
            monthInt = Integer.parseInt(monthString);
            monthInputIsValid = (monthInt < 13 && monthInt > 0);
        } catch (Exception ex) {
            monthInputIsValid = false;
            monthInt = 0;
        }

        /**
         * Check the year (PLACED IN CODE BEFORE DAY BECAUSE THE YEAR IS USED FOR LEAPYEARS..)
         */
        int yearInt = 0;
        String yearString = editYearField.getText().toString();
        try {
            yearInt = Integer.parseInt(yearString);
            yearInputIsValid = (yearInt < MAX_YEAR && yearInt > MIN_YEAR);
        } catch (Exception ex) {
            yearInputIsValid = false;
        }

        /**
         * Check the day
         */
        String dayString = editDayField.getText().toString();
        try {

            int maxDay = 31;
            //if it's worth it to make the month match..
            if (monthInt != 0 && monthInputIsValid) {
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
                        if (monthInt != 0 && monthInputIsValid) {
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

            int dayInt = Integer.parseInt(dayString);
            dayInputIsValid = (dayInt <= maxDay && dayInt > 0);
        } catch (Exception ex) {
            dayInputIsValid = false;
        }

        //conclusion
        inputIsValidated = (monthInputIsValid && dayInputIsValid && yearInputIsValid);

        showUserValidInputs(monthInputIsValid, dayInputIsValid, yearInputIsValid);

    }

    public void showUserValidInputs(boolean monthInputIsValid, boolean dayInputIsValid, boolean yearInputIsValid) {

        int acceptedColorInt =
            Color.argb(
                128,
                50,
                205,
                50
            );

        int refusedColorInt =
            Color.argb(
                128,
                188,
                143,
                143
            );

        if (monthInputIsValid) {
            editMonthField.setBackgroundColor(acceptedColorInt);
        } else {
            editMonthField.setBackgroundColor(refusedColorInt);
        }

        if (dayInputIsValid) {
            editDayField.setBackgroundColor(acceptedColorInt);
        } else {
            editDayField.setBackgroundColor(refusedColorInt);
        }

        if (yearInputIsValid) {
            editYearField.setBackgroundColor(acceptedColorInt);
        } else {
            editYearField.setBackgroundColor(refusedColorInt);
        }

    }

    public void onClick(View v) {
        checkInputs();
        if (inputIsValidated)
        {
            String nameString = editNameField.getText().toString();

            String monthString = editMonthField.getText().toString();
            String dayString = editDayField.getText().toString();
            String yearString = editYearField.getText().toString();
            int monthInt = Integer.parseInt(monthString);
            monthInt = monthInt - 1; // THE MONTH IN GREGORIAN CALENDAR CLASS IS A ZERO-BASED INDEX.
                                     // AND THUS NEEDS TO BE ADJUSTED
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

            finish();
        }
    }



}

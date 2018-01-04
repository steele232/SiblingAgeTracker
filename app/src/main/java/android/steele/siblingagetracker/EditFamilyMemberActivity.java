package android.steele.siblingagetracker;

import android.content.Intent;
import android.graphics.Color;
import android.steele.siblingagetracker.model.FamilyMember;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class EditFamilyMemberActivity extends AppCompatActivity {

    private static final String TAG = EditFamilyMemberActivity.class.getSimpleName();

    public static final int MAX_YEAR = 2200;
    public static final int MIN_YEAR = 1600;
    private boolean inputIsValidated = false;
    private EditText editName;
    private EditText editMonth;
    private EditText editDay;
    private EditText editYear;
    private Button buttonSubmit;

    private String _username = "";
    private String _keyToEdit = "";
    private String _name = "";
    private GregorianCalendar _birthdate = new GregorianCalendar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Right at beginning of OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_family_member);

        Gson gson = new Gson();

        _username = getIntent().getStringExtra("username");
        _keyToEdit = getIntent().getStringExtra("key");
        _name = getIntent().getStringExtra("name");

        String birthdateString = getIntent().getStringExtra("birthdate");
        _birthdate = gson.fromJson(birthdateString, GregorianCalendar.class);

        Log.e("DATA", _keyToEdit);
        Log.e("DATA", _name);
        Log.e("DATA", _birthdate.toString());

        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
//        buttonSubmit.setOnClickListener(this);

        editName = (EditText) findViewById(R.id.editName);

        editMonth = (EditText) findViewById(R.id.editMonth);
        editDay = (EditText) findViewById(R.id.editDay);
        editYear = (EditText) findViewById(R.id.editYear);

        editName.setText(_name) ;

        /**
         * Checking the year input is extracted to the checkInputs function
         */
        editMonth.setText(Integer.toString(_birthdate.get(Calendar.MONTH) + 1));
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
        editDay.setText(Integer.toString(_birthdate.get(Calendar.DAY_OF_MONTH)));
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
        editYear.setText(Integer.toString(_birthdate.get(Calendar.YEAR)));
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
        DatabaseReference userRef = database.getReference(_username);
        Log.d("TAG", userRef.getKey().toString());

        DatabaseReference familyMembersRef = userRef.child("familyMembers");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.i(TAG, "Hit the Action Bar");

        if (item.getItemId() == R.id.action_delete) {
            Log.i(TAG, "Got to the delete click event");

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference userRef = database.getReference(_username);
            Log.i(TAG, userRef.getKey().toString());

            DatabaseReference familyMembersRef = userRef.child("familyMembers");

            DatabaseReference editFamilyMemberRef = familyMembersRef.child(_keyToEdit);
            editFamilyMemberRef.removeValue();

            finish();

        } else {
            finish();
        }

        return true;
//        return super.onOptionsItemSelected(item);
    }

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


        inputIsValidated = (monthIsValid && dayIsValid && yearIsValid);

        showUserValidInputs(monthIsValid, dayIsValid, yearIsValid);

    }

    public void showUserValidInputs(boolean monthIsValid, boolean dayIsValid, boolean yearIsValid) {

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

        //month
        if (monthIsValid) {
            editMonth.setBackgroundColor(acceptedColorInt);
        } else {
            editMonth.setBackgroundColor(refusedColorInt);
        }

        //day
        if (dayIsValid) {
            editDay.setBackgroundColor(acceptedColorInt);
        } else {
            editDay.setBackgroundColor(refusedColorInt);
        }

        //year
        if (yearIsValid) {
            editYear.setBackgroundColor(acceptedColorInt);
        } else {
            editYear.setBackgroundColor(refusedColorInt);
        }

    }

    public void onClick(View v) {
        checkInputs();
        if (inputIsValidated)
        {
            String nameString = editName.getText().toString();

            String monthString = editMonth.getText().toString();
            String dayString = editDay.getText().toString();
            String yearString = editYear.getText().toString();
            int monthInt = Integer.parseInt(monthString);
            monthInt = monthInt - 1; // THE MONTH IN GREGORIAN CALENDAR CLASS IS A ZERO-BASED INDEX.
            // AND THUS NEEDS TO BE ADJUSTED
            int dayInt = Integer.parseInt(dayString);
            int yearInt = Integer.parseInt(yearString);
            GregorianCalendar birthdate = new GregorianCalendar();
            birthdate.set(yearInt, monthInt, dayInt); //year, month, date

            FamilyMember editedFM = new FamilyMember();
            editedFM.birthdate = birthdate;
            editedFM.name = nameString;

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference userRef = database.getReference(_username);
            Log.d("TAG", userRef.getKey().toString());

            DatabaseReference familyMembersRef = userRef.child("familyMembers");

            DatabaseReference editFamilyMemberRef = familyMembersRef.child(_keyToEdit);
            DatabaseReference newNameRef = editFamilyMemberRef.child("name");
            newNameRef.setValue(editedFM.name);

            DatabaseReference editBirthdateRef = editFamilyMemberRef.child("birthdate");
            Gson gson = new Gson();
            String birthDateString = gson.toJson(editedFM.birthdate);
            editBirthdateRef.setValue(birthDateString);

            Log.i(TAG, "Before Finish in OnClick");
            finish();
        }
    }



}

package android.steele.siblingagetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

public class AddFamilyMemberActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int MAX_YEAR = 2200;
    public static final int MIN_YEAR = 1600;
    private boolean inputIsValidated = false;
    private EditText editName;
    private EditText editMonth;
    private EditText editDay;
    private EditText editYear;
    private Button buttonSubmit;

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
            Intent intent = new Intent(AddFamilyMemberActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}

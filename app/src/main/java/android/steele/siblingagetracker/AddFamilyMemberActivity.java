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

    private boolean inputIsValidated = false;
    private EditText editName;
    private EditText editMonth;
    private EditText editDay;
    private EditText editYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family_member);

        Button button = (Button) findViewById(R.id.buttonSubmit);
        button.setOnClickListener(this);

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
        //get it to an int
        int monthInt = Integer.parseInt(monthString);
        //check if it's within the proper range
        monthIsValid = (monthInt < 13 && monthInt > 0);

        /**
         * Check the day
         */
        String dayString = editMonth.getText().toString();
        //get it to an int
        int dayInt = Integer.parseInt(dayString);
        //check if it's within the proper range
        dayIsValid = (dayInt < 13 && dayInt > 0);


        /**
         * Check the year
         */
        String yearString = editMonth.getText().toString();
        //get it to an int
        int yearInt = Integer.parseInt(yearString);
        //check if it's within the proper range
        yearIsValid = (yearInt < 13 && yearInt > 0);

        //conclusion
        inputIsValidated = (monthIsValid && dayIsValid && yearIsValid);

        showUserValidInputs(monthIsValid, dayIsValid, yearIsValid);

    }

    public void showUserValidInputs(boolean monthIsValid, boolean dayIsValid, boolean yearIsValid) {

        //month
        if (monthIsValid) {
            editMonth.setBackgroundColor(0x00000000);
        } else {
            editMonth.setBackgroundColor(Color.RED);
        }

        //day
        if (dayIsValid) {
            editMonth.setBackgroundColor(0x00000000);
        } else {
            editMonth.setBackgroundColor(Color.RED);
        }

        //year
        if (yearIsValid) {
            editMonth.setBackgroundColor(0x00000000);
        } else {
            editMonth.setBackgroundColor(Color.RED);
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

package android.steele.siblingagetracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DetailFamilyMemberActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = DetailFamilyMemberActivity.class.getSimpleName();

    private static final DateFormat localizedDateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());

    public static final int MAX_YEAR = 2200;
    public static final int MIN_YEAR = 1600;
    private boolean inputIsValidated = false;

    private boolean isInEdittingMode = false;

    private EditText editName;
    private TextView textBirthdate;

    private String _username = "";
    private int _keyToEdit;
    private String _name = "";
    private GregorianCalendar _birthdate = new GregorianCalendar();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_family_member);
        if (getIntent().hasExtra("key")) {
            isInEdittingMode = true;
        }
        if (isInEdittingMode) {
            setTitle(R.string.title_edit);
        } else {
            setTitle(R.string.title_add);
            _birthdate = new GregorianCalendar();
        }

        Gson gson = new Gson();

        //username gets sent whether it's edit or add because it's
        //needed in both circumstances.
        _username = getIntent().getStringExtra("username");

        if (getIntent().hasExtra("key")) {
            _keyToEdit = getIntent().getIntExtra("key", 0);
            Log.e(TAG, String.valueOf(_keyToEdit));
        }
        if (getIntent().hasExtra("name")) {
            _name = getIntent().getStringExtra("name");
            Log.e(TAG, _name);
        }
        if (getIntent().hasExtra("birthdate")) {
            String birthdateString = getIntent().getStringExtra("birthdate");
            _birthdate = gson.fromJson(birthdateString, GregorianCalendar.class);
            Log.e(TAG, _birthdate.toString());
        }

        collectReferences();
        setFormData();



    }

    private void collectReferences() {
        editName = (EditText) findViewById(R.id.editName);
        textBirthdate = (TextView) findViewById(R.id.textBirthdayDisplay);
    }

    private void setFormData() {
        editName.setText(_name);
        textBirthdate.setText(localizedDateFormatter.format(_birthdate.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (isInEdittingMode) {
            inflater.inflate(R.menu.menu_edit, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.i(TAG, "Hit the Action Bar");

        if (item.getItemId() == R.id.action_delete) {
            Log.i(TAG, "Got to the delete click event");


            //TODO DELETE THE THING #Architecture Stuff...
            //
            finish();

        } else {
            finish();
        }

        return true;
    }


    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment;
        newFragment = DatePickerFragment.newInstance(
                new Date(
                        _birthdate.get(Calendar.YEAR),
                        _birthdate.get(Calendar.MONTH),
                        _birthdate.get(Calendar.DAY_OF_MONTH)
                ),
                this
        );

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * Submit Button is Pressed and gets us back
     * to Main Activity and ready for saving the data.
     * @param v
     */
    public void onFormSubmit(View v) {

        //What about empty name field?
        //Let's not save it.. Just finish() :D


        finish();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Log.i(TAG, "Date has been Set!!");
        Log.i(TAG, "Year : " + year + " Month : " + month + " Day : " + day);
        //Remember that the month comes out in 0-11;
        //but so does Calendar, it's 1:1
        _birthdate.set(Calendar.YEAR, year);
        _birthdate.set(Calendar.MONTH, month);
        _birthdate.set(Calendar.DAY_OF_MONTH, day);
        Date date = new Date(year, month, day);
        Log.i(TAG, localizedDateFormatter.format(date));

        textBirthdate.setText(localizedDateFormatter.format(date));

    }

    //TODO  How do I autofill the date when it's and EDIT mode? (Difficult Context?)
    //TODO Update the TextView (Difficult Context?)

    public static String BIRTHDAY_BUNDLE_KEY = "MOVE";



    public static class DatePickerFragment extends DialogFragment{

        private DatePickerDialog.OnDateSetListener onDateSetListener;

        static DatePickerFragment newInstance(Date date, DatePickerDialog.OnDateSetListener onDateSetListener) {

            DatePickerFragment pickerFragment = new DatePickerFragment();

            pickerFragment.setOnDateSetListener(onDateSetListener);

            //Pass the date in a bundle.
            Bundle bundle = new Bundle();
            bundle.putSerializable(BIRTHDAY_BUNDLE_KEY, date);
            pickerFragment.setArguments(bundle);
            return pickerFragment;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            super.onCreateDialog(savedInstanceState);

            Date initialDate = (Date) getArguments().getSerializable(BIRTHDAY_BUNDLE_KEY);
            Calendar calendar = Calendar.getInstance();
//            DatePickerDialog dialog = new DatePickerDialog(
//                    getActivity(),
//                    onDateSetListener,
//                    initialDate.getYear(),
//                    initialDate.getMonth(),
//                    initialDate.getDay()
//            );
            DatePickerDialog dialog = new DatePickerDialog(
                    getActivity(),
                    onDateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            return dialog;
        }

        private void setOnDateSetListener(DatePickerDialog.OnDateSetListener listener) {
            this.onDateSetListener = listener;
        }

    }


}




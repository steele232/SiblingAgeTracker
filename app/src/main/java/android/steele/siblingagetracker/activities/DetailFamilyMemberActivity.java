package android.steele.siblingagetracker.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.steele.siblingagetracker.R;
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

public class DetailFamilyMemberActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, LifecycleOwner {

    private static final String TAG = DetailFamilyMemberActivity.class.getSimpleName();

    private static final DateFormat localizedDateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());

    public static final int MAX_YEAR = 2200;
    public static final int MIN_YEAR = 1600;
    private boolean _inputIsValidated = false;

    private boolean _isInEdittingMode = false;

    private EditText _editName;
    private TextView _textBirthdate;

    private String _username = "";
    private int _keyToEdit;
    private String _name = "";
    private GregorianCalendar _birthdate = new GregorianCalendar();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_family_member);
        if (getIntent().hasExtra("key")) {
            _isInEdittingMode = true;
        }
        if (_isInEdittingMode) {
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
            Log.i(TAG, String.valueOf(_keyToEdit));
        }
        if (getIntent().hasExtra("name")) {
            _name = getIntent().getStringExtra("name");
            Log.i(TAG, _name);
        }
        if (getIntent().hasExtra("birthdate")) {
            String birthdateString = getIntent().getStringExtra("birthdate");
            _birthdate = gson.fromJson(birthdateString, GregorianCalendar.class);
            Log.i(TAG, _birthdate.toString());
        }

        collectReferences();
        setFormData();



    }

    private void collectReferences() {
        _editName = (EditText) findViewById(R.id.editName);
        _textBirthdate = (TextView) findViewById(R.id.textBirthdayDisplay);
    }

    private void setFormData() {
        _editName.setText(_name);
        _textBirthdate.setText(localizedDateFormatter.format(_birthdate.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (_isInEdittingMode) {
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
        Log.i(TAG, "Date I'm giving the Dialog: " +
                localizedDateFormatter.format(_birthdate.getTime())
        );
        newFragment = DatePickerFragment.newInstance(
                new Date(
                        _birthdate.getTimeInMillis() //REMEMBER THIS!!! Don't use Deprecated Constructor w/ y/m/d
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

        //TODO Make the Save happen #Architecture Stuff...

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

        _textBirthdate.setText(localizedDateFormatter.format(_birthdate.getTime()));

    }


    public static String BIRTHDAY_BUNDLE_KEY = "MOVE";

    public static class DatePickerFragment extends DialogFragment{

        private DatePickerDialog.OnDateSetListener onDateSetListener;

        static DatePickerFragment newInstance(Date date, DatePickerDialog.OnDateSetListener onDateSetListener) {

            DatePickerFragment pickerFragment = new DatePickerFragment();

            pickerFragment.setOnDateSetListener(onDateSetListener);

            //Pass the date in a bundle.
            Bundle bundle = new Bundle();
            Log.i(TAG, "Date before Serialize: " +
                    localizedDateFormatter.format(date)
            );
            bundle.putSerializable(BIRTHDAY_BUNDLE_KEY, date);
            pickerFragment.setArguments(bundle);
            return pickerFragment;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            super.onCreateDialog(savedInstanceState);

            Date initialDate = (Date) getArguments().getSerializable(BIRTHDAY_BUNDLE_KEY);
            Log.i(TAG, "Date AFTER Serializing: " +
                    localizedDateFormatter.format(initialDate)
            );

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(initialDate.getTime());

            Log.i(TAG, "Initial Year : " + calendar.get(Calendar.YEAR));
            Log.i(TAG, "Initial Month : " + calendar.get(Calendar.MONTH));
            Log.i(TAG, "Initial Day : " + calendar.get(Calendar.DAY_OF_MONTH));
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




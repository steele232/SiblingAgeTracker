package android.steele.siblingagetracker.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.steele.siblingagetracker.R;
import android.steele.siblingagetracker.db.FamilyMember;
import android.steele.siblingagetracker.viewmodels.DetailView;
import android.support.annotation.Nullable;
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

    private boolean _isInEdittingMode = false;

    private EditText _editName;
    private TextView _textBirthdate;

    private DetailView _detailView;

    private int _keyToEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_family_member);
        collectReferences();

        _detailView = ViewModelProviders.of(this).get(DetailView.class);


        // Get the intent and ...
        if (getIntent().hasExtra("key")) {

            setTitle(R.string.title_edit);

            //collect the int UID.
            _keyToEdit = getIntent().getIntExtra("key", 0);

            // Kick it off with 'start Editing #...'
            _detailView.startEditingFamilyMemberWithID(_keyToEdit);

            Log.i(TAG, "Starting to edit FM with ID : " + String.valueOf(_keyToEdit));

        } else {

            setTitle(R.string.title_add);

            // OR Kick it off with a fresh FM
            _detailView.startAddingFamilyMember();

            Log.i(TAG, "Starting to add a FM from Blank");

        }


        // Then subscribe to the rest.
        subscribeFamilyMember();
        subscribeIsEdittingMode();

    }

    private void collectReferences() {
        _editName = (EditText) findViewById(R.id.editName);
        _textBirthdate = (TextView) findViewById(R.id.textBirthdayDisplay);
    }

    private void setFormData(FamilyMember fm) {
        _editName.setText(fm.getName());
        _textBirthdate.setText(localizedDateFormatter.format(fm.getBirthdate().getTime()));
    }

    private void subscribeFamilyMember() {

        final Observer<FamilyMember> familyMemberObserver = new Observer<FamilyMember>() {
            @Override
            public void onChanged(@Nullable FamilyMember familyMember) {
                Log.i(TAG, "Family Member should be updated.");
                setFormData(familyMember);
            }
        };
        _detailView.getFamilyMember().observe(this, familyMemberObserver);

    }

    private void subscribeIsEdittingMode() {

        final Observer<Boolean> isEdittingModeObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isInEdittingMode) {
                Log.i(TAG, "Editting Mode observer is updating the activity's local boolean.");
                _isInEdittingMode = isInEdittingMode;
            }
        };
        _detailView.getIsEdittingMode().observe(this, isEdittingModeObserver);

    }



    /*
        DELETE BUTTON/MENU METHODS
     */

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


    /*
        DATE PICKER METHODS AND CLASSES
     */

    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment;
        Log.i(TAG, "Date I'm giving the Dialog: " +
                localizedDateFormatter.format(
                        _detailView.getFamilyMember().getValue().getBirthdate().getTime()
                )
        );
        newFragment = DatePickerFragment.newInstance(
                new Date(
                        _detailView.getFamilyMember().getValue().getBirthdate()
                                .getTimeInMillis() //REMEMBER THIS!!! Don't use Deprecated Constructor w/ y/m/d
                ),
                this
        );

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Log.i(TAG, "Date has been Set!!");
        Log.i(TAG, "Year : " + year + " Month : " + month + " Day : " + day);
        //Remember that the month comes out in 0-11;
        //but so does Calendar, it's 1:1
        _detailView.getFamilyMember().getValue().getBirthdate()
                .set(Calendar.YEAR, year);
        _detailView.getFamilyMember().getValue().getBirthdate()
                .set(Calendar.MONTH, month);
        _detailView.getFamilyMember().getValue().getBirthdate()
                .set(Calendar.DAY_OF_MONTH, day);

        _textBirthdate.setText(localizedDateFormatter.format(
                _detailView.getFamilyMember().getValue().getBirthdate()
                        .getTime())
        );

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



    /*
        On Submit function
     */
    public void onFormSubmit(View v) {

        //What about empty name field?
        //Let's not save it.. Just finish() :D

        if (!_isInEdittingMode) {
            //TODO Make the Add/Save happen #Architecture Stuff...
            _detailView.saveNewFamilyMember(); //it has the new family member of itself.
        }

        //TODO Make the UPDATE happen as well #Architecture Stuff...
        //TODO Create a AsyncTask in the MainView or elsewhere to insert a FM.


        finish();
    }



}




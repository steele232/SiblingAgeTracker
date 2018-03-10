package android.steele.siblingagetracker.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.steele.siblingagetracker.R;
import android.steele.siblingagetracker.model.FamilyMember;
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

// TODO Introduce a KEY to FamilyMember when we implement DB. Then use that for all editing.
//    private int _keyToEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_family_member);

        collectReferences();

        //TODO Architecture stuff first.
        _detailView = ViewModelProviders.of(this).get(DetailView.class);
        _detailView.getFamilyMember().setValue(
                new FamilyMember(
                        "John", //TODO should never be shown.. Unless it's adding?
                        new GregorianCalendar()
                )
        );
        //TODO What is the difference between setValue and postValue on MutableLiveData<>?


        subscribeFamilyMember();
        subscribeIsEdittingMode();

        //TODO Figure out how to manage intents and editting mode
        // Does the intent last through recreation?
        // At what point do I give the ViewModel the mode boolean?
        // Should I have a local variable, then have a listener that
        // updates the local variable from the ViewModel?
        // And then should I have it get stuff from the intent only if the
        // ViewModel's data is null? If so, I should probably keep those as null or something, right?
        if (getIntent().hasExtra("key")) {
//            _isInEdittingMode = true;
            _detailView.getIsEdittingMode().postValue(true);

        }
        if (_isInEdittingMode) {
            setTitle(R.string.title_edit);
        } else {
            setTitle(R.string.title_add);
            //TODO is this next line kosher? If not, what is the best way to do this?

            //TODO I think that I have to do postValue or else the value won't be pushed to subscribers.
            // I guess from experience, I see that the field must be set.
            // I got a NullPointerException on this once or twice.
            FamilyMember fm = _detailView.getFamilyMember().getValue();
            fm.birthdate = new GregorianCalendar();
            _detailView.getFamilyMember().postValue(fm);
        }

        Gson gson = new Gson();

        //TODO do I need a Username?
        //username gets sent whether it's edit or add because it's
        //needed in both circumstances.
//        _username = getIntent().getStringExtra("username");

        //TODO keep these lines for when we have an ID for each FM in the DB.
//        if (getIntent().hasExtra("key")) {
//            _keyToEdit = getIntent().getIntExtra("key", 0);
//            Log.i(TAG, String.valueOf(_keyToEdit));
//        }
        if (getIntent().hasExtra("name")) {

            String name = getIntent().getStringExtra("name");
            FamilyMember fm = _detailView.getFamilyMember().getValue();
            fm.name = name;
            _detailView.getFamilyMember().postValue(fm);
            Log.i(TAG, "Name loaded from intent : " + _detailView.getFamilyMember().getValue().name);
        }
        if (getIntent().hasExtra("birthdate")) {
            String birthdateString = getIntent().getStringExtra("birthdate");

            _detailView.getFamilyMember().getValue().birthdate =
                    gson.fromJson(birthdateString, GregorianCalendar.class);
            Log.i(TAG, "Birthday loaded from intent : " +
                    localizedDateFormatter.format(
                            _detailView.getFamilyMember().getValue().birthdate.getTime()
                    )
            );
        }

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
                _isInEdittingMode = isInEdittingMode;
            }
        };
        _detailView.getIsEdittingMode().observe(this, isEdittingModeObserver);

    }

    private void collectReferences() {
        _editName = (EditText) findViewById(R.id.editName);
        _textBirthdate = (TextView) findViewById(R.id.textBirthdayDisplay);
    }

    private void setFormData(FamilyMember fm) {
        _editName.setText(fm.name);
        _textBirthdate.setText(localizedDateFormatter.format(fm.birthdate.getTime()));
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
                        _detailView.getFamilyMember().getValue().birthdate.getTime()
                )
        );
        newFragment = DatePickerFragment.newInstance(
                new Date(
                        _detailView.getFamilyMember().getValue().birthdate
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
        _detailView.getFamilyMember().getValue().birthdate
                .set(Calendar.YEAR, year);
        _detailView.getFamilyMember().getValue().birthdate
                .set(Calendar.MONTH, month);
        _detailView.getFamilyMember().getValue().birthdate
                .set(Calendar.DAY_OF_MONTH, day);

        _textBirthdate.setText(localizedDateFormatter.format(
                _detailView.getFamilyMember().getValue().birthdate
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

        //TODO Make the Save happen #Architecture Stuff...

        finish();
    }



}




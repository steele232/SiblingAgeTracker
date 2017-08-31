package android.steele.siblingagetracker;

import android.steele.siblingagetracker.android.steele.siblingagetracker.model.UserPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private String TAG = this.getClass().toString();
    private boolean notificationsOn;
    private UserPreferences mUserPreferences;

    private Switch activationSwitch;
    private CheckBox checkThatDay;
    private CheckBox checkDayBefore1;
    private CheckBox checkDayBefore2;
    private CheckBox checkDayBefore3;
    private CheckBox checkWkBefore1;
    private CheckBox checkWkBefore2;
    private CheckBox checkMonthBefore1;
    private CheckBox checkMonthBefore2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);

        this.setTitle("Notifications");

        mUserPreferences = new UserPreferences();

        //handle the activation switch first
        activationSwitch = (Switch) findViewById(R.id.switchBirthdayNotifications);
        activationSwitch.setOnCheckedChangeListener(this);

        //collect all the references.
        checkThatDay = (CheckBox) findViewById(R.id.thatDay);
        checkDayBefore1 = (CheckBox) findViewById(R.id.dayBefore1);
        checkDayBefore2 = (CheckBox) findViewById(R.id.dayBefore2);
        checkDayBefore3 = (CheckBox) findViewById(R.id.dayBefore3);
        checkWkBefore1 = (CheckBox) findViewById(R.id.wkBefore1);
        checkWkBefore2 = (CheckBox) findViewById(R.id.wkBefore2);
        checkMonthBefore1 = (CheckBox) findViewById(R.id.monthBefore1);
        checkMonthBefore2 = (CheckBox) findViewById(R.id.monthBefore2);

        // make the buttons disappear
        // DESIGN DECISION, so that they all start at an off position while the
        // asynchronous task of getting the user preferences is taking place.
        activationSwitch.setChecked(false);
        checkThatDay.setVisibility(View.GONE);
        checkDayBefore1.setVisibility(View.GONE);
        checkDayBefore2.setVisibility(View.GONE);
        checkDayBefore3.setVisibility(View.GONE);
        checkWkBefore1.setVisibility(View.GONE);
        checkWkBefore2.setVisibility(View.GONE);
        checkMonthBefore1.setVisibility(View.GONE);
        checkMonthBefore2.setVisibility(View.GONE);



        //SET THE SWITCHES TO THEIR PROPER SETTINGS @
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user1");
        Log.d("TAG", userRef.getKey().toString());
        DatabaseReference userPreferencesRef = userRef.child("userPreferences");
        Log.d("TAG", userPreferencesRef.getKey().toString());
        userPreferencesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //get the object and use the values to update the UI
                mUserPreferences = dataSnapshot.getValue(UserPreferences.class);
                Log.d("TAG", mUserPreferences.toString());

                onCheckedChanged(null, mUserPreferences.isActivated);
                activationSwitch.setChecked(mUserPreferences.isActivated);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FAILED POST", "Notifications onCreate couldn't get the notifications", databaseError.toException());
            }
        });



    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.d(TAG, "Made it to the onclick event");

        Log.d(TAG, "Click: Switch is: " + isChecked);
        mUserPreferences.isActivated = isChecked;


        // TODO: I'll want to make the other elements in the layout appear/disappear
        // depending on whether or not notifications is ON or OFF.
        if (mUserPreferences.isActivated) {
            //show the bottom buttons
            checkThatDay.setVisibility(View.VISIBLE);
            checkDayBefore1.setVisibility(View.VISIBLE);
            checkDayBefore2.setVisibility(View.VISIBLE);
            checkDayBefore3.setVisibility(View.VISIBLE);
            checkWkBefore1.setVisibility(View.VISIBLE);
            checkWkBefore2.setVisibility(View.VISIBLE);
            checkMonthBefore1.setVisibility(View.VISIBLE);
            checkMonthBefore2.setVisibility(View.VISIBLE);

        } else {
            // make the buttons disappear
            checkThatDay.setVisibility(View.GONE);
            checkDayBefore1.setVisibility(View.GONE);
            checkDayBefore2.setVisibility(View.GONE);
            checkDayBefore3.setVisibility(View.GONE);
            checkWkBefore1.setVisibility(View.GONE);
            checkWkBefore2.setVisibility(View.GONE);
            checkMonthBefore1.setVisibility(View.GONE);
            checkMonthBefore2.setVisibility(View.GONE);

        }

        // TODO: Make sure that the changes are reflected in FIREBASE
    }

}

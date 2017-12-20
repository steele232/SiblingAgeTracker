package android.steele.siblingagetracker;

import android.steele.siblingagetracker.model.UserPreferences;
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

import java.util.HashMap;

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
        DatabaseReference userRef = database.getReference("user2");
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


        /**
         * ADD ALL THE OTHER LISTENERS.
         * GAMEPLAN: ** HAVE THEM:
         *      UPDATE THE ACTIVITY'S USER PREFERENCES
         *      CALL THE UPDATE FIREBASE 'SERVICE' FUNCTION
         *      'update user preferences'
         * RIGHT NOW: Get the User Preferences and apply them to the
         *  initial interface.
         */
//        initiallyCheckBoxes();

         checkThatDay.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 mUserPreferences.thatDay = checkThatDay.isChecked();
                 updateUserSettingRemote();
             }
         });

         checkDayBefore1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 mUserPreferences.dayBefore1 = checkDayBefore1.isChecked();
                 updateUserSettingRemote();
             }
         });

         checkDayBefore2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 mUserPreferences.dayBefore2 = checkDayBefore2.isChecked();
                 updateUserSettingRemote();
             }
         });

         checkDayBefore3.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 mUserPreferences.dayBefore3 = checkDayBefore3.isChecked();
                 updateUserSettingRemote();
             }
         });

         checkWkBefore1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 mUserPreferences.wkBefore1 = checkWkBefore1.isChecked();
                 updateUserSettingRemote();
             }
         });

        checkWkBefore2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 mUserPreferences.wkBefore2 = checkWkBefore2.isChecked();
                 updateUserSettingRemote();
             }
         });

         checkMonthBefore1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 mUserPreferences.monthBefore1 = checkMonthBefore1.isChecked();
                 updateUserSettingRemote();
             }
         });

         checkMonthBefore2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 mUserPreferences.monthBefore2 = checkMonthBefore2.isChecked();
                 updateUserSettingRemote();
             }
         });

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.d(TAG, "Made it to the onclick event");

        Log.d(TAG, "Click: Switch is: " + isChecked);
        mUserPreferences.isActivated = isChecked;


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

            updateUserNotificationsActivationOnly();
            initiallyCheckBoxes();

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

            updateUserNotificationsActivationOnly();

        }

    }


    public void initiallyCheckBoxes() {
        checkThatDay.setChecked(mUserPreferences.thatDay);
        checkDayBefore1.setChecked(mUserPreferences.dayBefore1);
        checkDayBefore2.setChecked(mUserPreferences.dayBefore2);
        checkDayBefore3.setChecked(mUserPreferences.dayBefore3);
        checkWkBefore1.setChecked(mUserPreferences.wkBefore1);
        checkWkBefore2.setChecked(mUserPreferences.wkBefore2);
        checkMonthBefore1.setChecked(mUserPreferences.monthBefore1);
        checkMonthBefore2.setChecked(mUserPreferences.monthBefore2);
    }

    public void updateUserSettingRemote() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user2");
        DatabaseReference userPreferencesRef = userRef.child("userPreferences");
        HashMap <String, Object> preferencesMap = new HashMap<>();
        preferencesMap.put("isActivated", activationSwitch.isChecked());
        preferencesMap.put("thatDay", checkThatDay.isChecked());
        preferencesMap.put("dayBefore1", checkDayBefore1.isChecked());
        preferencesMap.put("dayBefore2", checkDayBefore2.isChecked());
        preferencesMap.put("dayBefore3", checkDayBefore3.isChecked());
        preferencesMap.put("wkBefore1", checkWkBefore1.isChecked());
        preferencesMap.put("wkBefore2", checkWkBefore2.isChecked());
        preferencesMap.put("monthBefore1", checkMonthBefore1.isChecked());
        preferencesMap.put("monthBefore2", checkMonthBefore2.isChecked());

        userPreferencesRef.updateChildren(preferencesMap);
    }

    public void updateUserNotificationsActivationOnly() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user2");
        DatabaseReference userPreferencesRef = userRef.child("userPreferences");
        DatabaseReference isActivatedRef = userPreferencesRef.child("isActivated");
        isActivatedRef.setValue(mUserPreferences.isActivated);


    }


}

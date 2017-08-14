package android.steele.siblingagetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private String TAG = this.getClass().toString();
    private boolean notificationsOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);

        Switch s = (Switch) findViewById(R.id.switchBirthdayNotifications);
        s.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.d(TAG, "Made it to the onclick event");

        Log.d(TAG, "Click: Switch is: " + isChecked);

        // TODO: I'll want to make the other elements in the layout appear/disappear
        // depending on whether or not notifications is ON or OFF.
    }

}

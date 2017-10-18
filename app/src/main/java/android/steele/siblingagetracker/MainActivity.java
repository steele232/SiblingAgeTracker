package android.steele.siblingagetracker;

import android.content.Intent;
import android.os.Bundle;
import android.steele.siblingagetracker.android.steele.siblingagetracker.model.FamilyMemberRow;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private String TAG = MainActivity.class.toString();
    private ListView mListView;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.US);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabEdit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, "Let's add a new Family Member!!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, AddFamilyMemberActivity.class);
                startActivity(intent);

            }
        });

        mListView = (ListView) findViewById(R.id.familyMembersList);
    }

    /**
     * onStart()
     *
     * Load the ListView on the OnStart lifecycle hook so that the
     * Listview can be reloaded when a new FamilyMember is added.
     */
    @Override
    protected void onStart() {

        super.onStart();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user2");
        DatabaseReference familyMembersRef = userRef.child("familyMembers");

        FirebaseListAdapter<FamilyMemberRow> mAdapter = new FirebaseListAdapter<FamilyMemberRow>(this, FamilyMemberRow.class, R.layout.row_family_member, familyMembersRef) {
            @Override
            protected void populateView(View view, FamilyMemberRow myFamilyMemberRow, int position) {
                //Set the value for the views

                /**
                 * SET THE NAME
                 */
                ((TextView)view.findViewById(R.id.name)).setText(myFamilyMemberRow.name);

                /**
                 * SET THE BIRTHDATE
                 */
                Gson gson = new Gson();
                GregorianCalendar calendar = gson.fromJson(myFamilyMemberRow.birthdate, GregorianCalendar.class);

                int birthMonth = calendar.get(GregorianCalendar.MONTH);
                int birthDay = calendar.get(GregorianCalendar.DAY_OF_MONTH);
                int birthYear = calendar.get(GregorianCalendar.YEAR);

                ((TextView)view.findViewById(R.id.birthdate)).setText(
                        dateFormat.format(calendar.getTime())
                );


                /**
                 * CALCULATE THE AGE OF THE FAMILY MEMBER
                 */
                final Calendar now = Calendar.getInstance();
                int currentYear = now.get(Calendar.YEAR);
                int currentMonth = now.get(Calendar.MONTH);
                int currentDay = now.get(Calendar.DAY_OF_MONTH);

                //calculating the differences
                int yearDifference = currentYear - birthYear;
                int monthDifference = currentMonth - birthMonth;
                int dayDifference = currentDay - birthDay;

                //whether the last year should be counted...
                //if we've past the birthday of the year, we count the last year.
                //if we've NOT past the birthday of the year, we do not count the last year.
                int offset = 1;
                //if
                if (
                    currentMonth > birthMonth ||
                        (
                            currentMonth == birthMonth &&
                            currentDay >= birthDay
                        )
                    )
                {
                    offset = 0;
                }
                int age = yearDifference - offset;

                TextView ageView = (TextView) view.findViewById(R.id.age);
                ageView.setText(Integer.toString(age));

            }

        };
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Log.d(TAG, "Settings selected!");
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);

        //no inspection
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.w("Testing", "You clicked Item: " + id + " at position:" + position);

        // get it pulling up a dialogFragment* ?

    }
}

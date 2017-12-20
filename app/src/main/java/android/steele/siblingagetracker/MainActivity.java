package android.steele.siblingagetracker;

import android.content.Intent;
import android.os.Bundle;
import android.steele.siblingagetracker.model.FamilyMemberRow;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ListView mListView;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.US);
    private String username = "user2";


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
                intent.putExtra("username", username);
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
        DatabaseReference userRef = database.getReference(username);
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
                int negativeOffset = 1;
                //we will subtract 1 from the yearDifference (to end up with the age in years) if we haven't come upon the birthday yet.
                if (
                    birthMonth < currentMonth ||
                        (
                            currentMonth == birthMonth &&
                            currentDay >= birthDay
                        )
                    )
                {
                    //we HAVE passed the birthDay this year, so we will NOT subtract 1 from the yearDifference.
                    //If so, the yearDifference IS the age.
                    negativeOffset = 0;
                }
                int age = yearDifference - negativeOffset;

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
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        Log.e("Testing", "You clicked Item: " + id + " at position:" + position);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference(username);
        DatabaseReference familyMembersRef = userRef.child("familyMembers");


        familyMembersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Gson gson = new Gson();

                String keyToKeep = "";
                String nameToKeep = "";
                GregorianCalendar birthdateToKeep = new GregorianCalendar();

//                int childrenCount = (int) dataSnapshot.getChildrenCount();
                Iterable<DataSnapshot> iter = dataSnapshot.getChildren();
                int i = 0;
                for (DataSnapshot snapshot : iter) {
                    //if it's the right position...
                    if (i == position) {
                        //get the data, for repopulation
                        String key = snapshot.getKey();
                        String name = (String) snapshot.child("name").getValue();
                        GregorianCalendar birthdate = gson.fromJson(snapshot.child("birthdate").getValue().toString(), GregorianCalendar.class);

                        keyToKeep = key;
                        nameToKeep = name;
                        birthdateToKeep = birthdate;

                        Log.e("DATA", key);
                        Log.e("DATA", name);
                        Log.e("DATA", birthdate.toString());
                        Log.e("DATA", Integer.toString(position));

                        Log.e("TESTING", snapshot.getKey());
                        Log.e("TESTING", snapshot.getValue().toString());
                    }
                    i++;
                }
                callbackGoToEdit(
                        keyToKeep,
                        nameToKeep,
                        birthdateToKeep);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void callbackGoToEdit(
            String key,
            String name,
            GregorianCalendar birthdate
    ) {

        Log.i(TAG, "Start of callback");
        Intent intent = new Intent(MainActivity.this , EditFamilyMemberActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("key", key);
        intent.putExtra("name", name);
        Gson gson = new Gson();

        intent.putExtra("birthdate", gson.toJson(birthdate));
//        startActivityForResult(intent,)
         startActivity(intent);
        Log.i(TAG, "End of callback");
    }


}

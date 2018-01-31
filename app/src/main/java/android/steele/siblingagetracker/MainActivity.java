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

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ListView _familyMemberListView;
    private static final DateFormat localizedDateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault());
    private String username = "user2";


    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_main);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabEdit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, DetailFamilyMemberActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);

            }
        });

        _familyMemberListView = (ListView) findViewById(R.id.familyMembersList);
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


        //TODO Load up the ListAdapter and link it to Data.












//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference userRef = database.getReference(username);
//        DatabaseReference familyMembersRef = userRef.child("familyMembers");
//
//        FirebaseListAdapter<FamilyMemberRow> mAdapter = new FirebaseListAdapter<FamilyMemberRow>(this, FamilyMemberRow.class, R.layout.row_family_member, familyMembersRef) {
//            @Override
//            protected void populateView(View view, FamilyMemberRow myFamilyMemberRow, int position) {
//
//                /**
//                 * SET THE NAME
//                 */
//                ((TextView)view.findViewById(R.id.name)).setText(myFamilyMemberRow.name);
//
//                /**
//                 * SET THE BIRTHDATE
//                 */
//                Gson gson = new Gson();
//                GregorianCalendar calendar = gson.fromJson(myFamilyMemberRow.birthdate, GregorianCalendar.class);
//
//                int birthMonth = calendar.get(GregorianCalendar.MONTH);
//                int birthDay = calendar.get(GregorianCalendar.DAY_OF_MONTH);
//                int birthYear = calendar.get(GregorianCalendar.YEAR);
//
//                ((TextView)view.findViewById(R.id.birthdate)).setText(
//                        localizedDateFormatter.format(calendar.getTime())
//                );
//
//
//                TextView ageView = (TextView) view.findViewById(R.id.age);
//                ageView.setText(Integer.toString(age));
//
//            }
//
//        };
//        _familyMemberListView.setAdapter(mAdapter);
//        _familyMemberListView.setOnItemClickListener(this);

    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
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

    /**
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        Log.e("Testing", "You clicked Item: " + id + " at position:" + position);

        //TODO Handle clicking on and editing an item/row













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

                Iterable<DataSnapshot> familyMemberCollection = dataSnapshot.getChildren();
                int i = 0;
                for (DataSnapshot snapshot : familyMemberCollection) {
                    //if it's the right position...
                    if (i == position) {
                        //get the data, for repopulation
                        keyToKeep = snapshot.getKey();
                        nameToKeep = (String) snapshot.child("name").getValue();
                        birthdateToKeep = gson.fromJson(snapshot.child("birthdate").getValue().toString(), GregorianCalendar.class);

                        Log.e("DATA", keyToKeep);
                        Log.e("DATA", nameToKeep);
                        Log.e("DATA", birthdateToKeep.toString());
                        Log.e("DATA", Integer.toString(position));

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
        Intent intent = new Intent(MainActivity.this , DetailFamilyMemberActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("key", key);
        intent.putExtra("name", name);
        Gson gson = new Gson();

        intent.putExtra("birthdate", gson.toJson(birthdate));
        startActivity(intent);
        Log.i(TAG, "End of callback");
    }


}

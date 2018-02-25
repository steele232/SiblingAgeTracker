package android.steele.siblingagetracker;

import android.content.Intent;
import android.os.Bundle;
import android.steele.siblingagetracker.adapters.FamilyMemberAdapter;
import android.steele.siblingagetracker.model.FamilyMember;
import android.steele.siblingagetracker.model.FamilyMemberRow;
import android.steele.siblingagetracker.service.Mockstore;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ListView _familyMemberListView;
    private String username = "user2";
    private ArrayList<FamilyMember> _familyMembers;


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
        _familyMembers = Mockstore.getList();
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


        //Load up the ListAdapter and link it to Data.
        FamilyMemberAdapter adapter = new FamilyMemberAdapter(MainActivity.this, R.layout.row_family_member, _familyMembers);
        _familyMemberListView.setAdapter(adapter);
        _familyMemberListView.setOnItemClickListener(this);

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
     * Handle clicking on and editing an item/row
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        Log.e("Testing", "You clicked Item: " + id + " at position:" + position);

        FamilyMember familyMember = _familyMembers.get(position);

        Log.i(TAG, "Start of callback");
        Intent intent = new Intent(MainActivity.this , DetailFamilyMemberActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("key", position);
        intent.putExtra("name", familyMember.name);
        Gson gson = new Gson();

        intent.putExtra("birthdate", gson.toJson(familyMember.birthdate));
        startActivity(intent);
        Log.i(TAG, "End of callback");

    }

}

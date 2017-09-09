package android.steele.siblingagetracker;

import android.content.Intent;
import android.os.Bundle;
import android.steele.siblingagetracker.android.steele.siblingagetracker.model.FamilyMember;
import android.steele.siblingagetracker.android.steele.siblingagetracker.model.FamilyMemberRow;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.toString();
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
                ((TextView)view.findViewById(R.id.name)).setText(myFamilyMemberRow.name);

                //...
            }
        };
        mListView.setAdapter(mAdapter);

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
}

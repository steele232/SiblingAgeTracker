package android.steele.siblingagetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.toString();

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

        //TEST THE FIREBASE DATABASE
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user2");
        Log.d("TAG", userRef.getKey().toString());

        // TODO: GET ALL THE OBJECTS AND SET UP THE LISTVIEW
        // (Don't worry about getting the things more than once.. ?


        //IDEA:
        // have the listview in the XML already
        // get the values in the OnCreate->ref.addValueListener--->>
        //      Set up the data and create the ListView from the data.

        DatabaseReference familyMembersRef = userRef.child("familyMembers");
        familyMembersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "ListView Loading");
                Toast.makeText(MainActivity.this, "ListView Loading", Toast.LENGTH_LONG);
                //make an arrayList or something
                //      of familyMembers

                // iterate through the dataSnapshot
                //      Make a FamilyMember out of each row and
                //      add them to the dataSnapShot

                //sort the list?? maybe an added feature for later
                //      sort by birthday first, for sure

                //add that ArrayList / adapter? to the ListView,
                //      (populate the listView)
                //**** ALL WITHIN THE VALUE EVENT LISTENER!!! ******

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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

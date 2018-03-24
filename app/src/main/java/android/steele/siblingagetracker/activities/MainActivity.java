package android.steele.siblingagetracker.activities;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.steele.siblingagetracker.R;
import android.steele.siblingagetracker.adapters.FamilyMemberRecyclerAdapter;
import android.steele.siblingagetracker.interfaces.FMOnClickListener;
import android.steele.siblingagetracker.db.FamilyMember;
import android.steele.siblingagetracker.viewmodels.MainView;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements LifecycleOwner, FMOnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private String username = "user2";

    private MainView _mainView;

    private RecyclerView _familyMemberRecyclerView;
    private RecyclerView.LayoutManager _layoutManager;

    private TextView _helperInstructions;
    private ImageView _helperArrow;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_main);
//        setSupportActionBar(toolbar);
        collectHelperViewReferences();

        _mainView = ViewModelProviders.of(this).get(MainView.class);

        /* Recycler View */
        _familyMemberRecyclerView = (RecyclerView) findViewById(R.id.familyMembersList);
        _familyMemberRecyclerView.setHasFixedSize(true);
        _layoutManager = new LinearLayoutManager(this);
        _familyMemberRecyclerView.setLayoutManager(_layoutManager);

        //Load up the ListAdapter and link it to Data and listeners.
        final FamilyMemberRecyclerAdapter adapter =
                new FamilyMemberRecyclerAdapter(new ArrayList<FamilyMember>(), this);
        _familyMemberRecyclerView.setAdapter(adapter);

        final Observer<List<FamilyMember>> familyMemberListObserver =
                new Observer<List<FamilyMember>>() {
                    @Override
                    public void onChanged(@Nullable List<FamilyMember> familyMembers) {
                        Gson gson = new Gson();
                        if (familyMembers != null) {
                            //I don't know that this would ever be null....

                            // the test is whether the list is empty or not..
                            if (familyMembers.isEmpty()) {
                                Log.i(TAG, "Main Activity family member list is now updated." + gson.toJson(familyMembers) );
                                showHelpers();
                            } else {
                                Log.i(TAG, "Main Activity family member list is now updated." + gson.toJson(familyMembers) );
                                hideHelpers();
                            }

                        } else {
                            Log.e(TAG, "Main Activity family member list is now being updated to null. That's a problem.");
                        }
                        adapter.setList(familyMembers);
                    }
                };
        _mainView.getFamilyMembers().observe(this, familyMemberListObserver);

        /* END ARCH */

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabEdit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i(TAG, "Start of FAB OnClick Event");
                Intent intent = new Intent(MainActivity.this, DetailFamilyMemberActivity.class);
                startActivity(intent);
                Log.i(TAG, "End of FAB OnClick Event");

            }
        });

    }

    public void collectHelperViewReferences() {
        _helperArrow = findViewById(R.id.helperArrow);
        _helperInstructions = findViewById(R.id.helperInstructions);
    }

    public boolean helpersAreHidden() {
        return !_helperArrow.isShown() || !_helperInstructions.isShown();
    }

    public void hideHelpers() {
        _helperArrow.setVisibility(View.INVISIBLE);
        _helperInstructions.setVisibility(View.INVISIBLE);
    }

    public void showHelpers() {
        _helperArrow.setVisibility(View.VISIBLE);
        _helperInstructions.setVisibility(View.VISIBLE);
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
    public void onItemClick(int uid) {
        //TODO This parameter int is determined by the listener we give to the adapter and what the adapter returns from that listener we've passed down.

        //SOLUTION. Stop using position. Starting using the UID. Saves two conversion steps and solves a problem as well.
        Log.i(TAG, "Position : " + uid);

        Log.i(TAG, "Start of MainActivity ListItem onClick");
        Intent intent = new Intent(MainActivity.this , DetailFamilyMemberActivity.class);
        intent.putExtra("key", uid);
        startActivity(intent);
        Log.i(TAG, "End of MainActivity ListItem onClick");

    }
}

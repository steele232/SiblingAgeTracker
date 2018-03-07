package android.steele.siblingagetracker.activities;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.steele.siblingagetracker.R;
import android.steele.siblingagetracker.adapters.FamilyMemberRecyclerAdapter;
import android.steele.siblingagetracker.interfaces.FMOnClickListener;
import android.steele.siblingagetracker.model.FamilyMember;
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


import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements LifecycleOwner, FMOnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private String username = "user2";

    private MainView _mainView;

    private RecyclerView _familyMemberRecyclerView;
    private RecyclerView.Adapter _adapter;
    private RecyclerView.LayoutManager _layoutManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_main);
//        setSupportActionBar(toolbar);

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

        final Observer<ArrayList<FamilyMember>> familyMemberListObserver =
                new Observer<ArrayList<FamilyMember>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<FamilyMember> familyMembers) {
                        Log.i(TAG, "Main Activity family member list is now updated." + familyMembers.toString());
                        adapter.setList(familyMembers);

                    }
                };
        _mainView.getFamilyMembers().observe(this, familyMemberListObserver);

        /* END ARCH */

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabEdit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, DetailFamilyMemberActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);

            }
        });

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
    public void onItemClick(int position) {

        FamilyMember familyMember = _mainView.getFamilyMembers().getValue().get(position);

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

package android.steele.siblingagetracker.activities;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.steele.siblingagetracker.R;
import android.steele.siblingagetracker.adapters.FamilyMemberAdapter;
import android.steele.siblingagetracker.model.FamilyMember;
import android.steele.siblingagetracker.service.Mockstore;
import android.steele.siblingagetracker.viewmodels.MainView;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener, LifecycleOwner {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ListView _familyMemberListView;
    private String username = "user2";
//    private ArrayList<FamilyMember> _familyMembers;

    private MainView _mainView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_main);
//        setSupportActionBar(toolbar);



        /* TODO ARCHITECTURE */
        _mainView = ViewModelProviders.of(this).get(MainView.class);

        _familyMemberListView = (ListView) findViewById(R.id.familyMembersList);

        //Load up the ListAdapter and link it to Data.
        final FamilyMemberAdapter adapter = new FamilyMemberAdapter(MainActivity.this, R.layout.row_family_member);
        _familyMemberListView.setAdapter(adapter);
        _familyMemberListView.setOnItemClickListener(this);



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


    private void onCreateSubscribe() {
//        _mainView.getFamilyMembers().

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

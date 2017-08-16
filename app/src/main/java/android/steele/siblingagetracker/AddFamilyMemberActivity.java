package android.steele.siblingagetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

public class AddFamilyMemberActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean inputIsValidated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family_member);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (inputIsValidated)
        {
            Intent intent = new Intent(AddFamilyMemberActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}

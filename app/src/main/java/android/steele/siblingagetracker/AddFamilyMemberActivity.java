package android.steele.siblingagetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

public class AddFamilyMemberActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean inputIsValidated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family_member);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        EditText dateEdit = (EditText) findViewById(R.id.editDate);

        /**
         * LISTENER FOR THE DATE TEXTBOX
         */
        //TODO: INPUT VALIDATION.
        //Try this editText inputWatcher... Sara's goal is just to type in the 09051963,
        // and the app fills in the separators, and
        // https://github.com/codepath/android_guides/wiki/Basic-Event-Listeners

        dateEdit.addTextChangedListener(new TextWatcher() {

            private boolean hasBeenAdded;
            private int myCount;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // IF THE TEXT CHANGES THEN CALL setChangeDue WHICH DISPLAYS CHANGE DUE
                // ON THE PAGE
                Log.d("editDate OnTextChanged", "Last Character: " + s.subSequence(count - 1, count));
//                Log.d("editDate OnTextChanged", "Hasbeenadded: " + hasBeenAdded);
//                Log.d("editDate OnTextChanged", "TEXT HAS CHANGED: " + s);
//                if (s.subSequence(count - 1, count).equals("/"))
//                {
//                    hasBeenAdded = true;
//                    Log.d("editDate OnTextChanged", "Hasbeenadded: " + hasBeenAdded);
//                    myCount++;
//                }

            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (!hasBeenAdded) {
//                    s.append('/');
//                    Log.d("editDate OnTextChanged", "Hasbeenadded: " + hasBeenAdded);
//                }
//                Log.d("editDate OnTextChanged", "TEXT HAS CHANGED: " + s);
            }
        });
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

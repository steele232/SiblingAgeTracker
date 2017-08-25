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
    private EditText editName;
    private EditText editDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family_member);

        Button button = (Button) findViewById(R.id.buttonSubmit);
        button.setOnClickListener(this);

        editName = (EditText) findViewById(R.id.editName);
//        editDate = (EditText) findViewById(R.id.editDate);

        /**
         * LISTENER FOR THE DATE TEXTBOX
         */
        //TODO: INPUT VALIDATION.
        //Try this editText inputWatcher... Sara's goal is just to type in the 09051963,
        // and the app fills in the separators, and
        // https://github.com/codepath/android_guides/wiki/Basic-Event-Listeners

//        editDate.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.d("editDate OnTextChanged", "BLAH BLAH1");
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // IF THE TEXT CHANGES THEN CALL setChangeDue WHICH DISPLAYS CHANGE DUE
//                // ON THE PAGE
//
//                if (s.subSequence(s.length() - 1, s.length()).toString().equals("-")) {
//                    augmentDateInput();
//                }
//                Log.d("editDate OnTextChanged", "Last Character: " + s.subSequence(s.length() - 1, s.length()));
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                Log.d("editDate OnTextChanged", "BLAH BLAH2");
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        if (inputIsValidated)
        {
            Intent intent = new Intent(AddFamilyMemberActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    /**
     *  SET THE $ CHANGE DUE TO THE CUSTOMER ($PAID - $PRICE = $CHANGEDUE)
     */
    public void augmentDateInput() {
        editDate.getText().append("/");
    }
}

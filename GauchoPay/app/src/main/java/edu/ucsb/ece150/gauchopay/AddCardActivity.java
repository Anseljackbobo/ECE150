package edu.ucsb.ece150.gauchopay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;


import edu.ucsb.ece150.gauchopay.CardListActivity;

import com.braintreepayments.cardform.view.CardForm;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add Card");
        setSupportActionBar(toolbar);

        // Note that the requirements here are just for creating the fields on the form. For
        // example, if the cvvRequired setting was set to "false", the form would not contain
        // a field for CVV. ("Requirement" DOES NOT mean "Valid".)
        final CardForm cardForm = findViewById(R.id.card_form);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .actionLabel("Add Card")
                .setup(this);

        // [TODO] Implement a method of getting card information and sending it to the main activity.
        // You will want to add a new component onto this activity's layout so you can perform this
        // task as a result of a button click.
        //
        //  Get card information from the CardForm view. Refer to the library website
        // https://github.com/braintree/android-card-form/blob/master/README.md.
        //
        // This information has to be sent back to the CardListActivity (to update the
        // list of cards).
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardForm cardForm = (CardForm) findViewById(R.id.card_form);
                String text = cardForm.getCardNumber();


                if(text.length() != 0) {
                    SharedPreferences sharedPreferences = getSharedPreferences(CardListActivity.APP_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                    sharedPreferencesEditor.putString(CardListActivity.CARD_INFO, text);
                    sharedPreferencesEditor.apply();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Please enter card info", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}

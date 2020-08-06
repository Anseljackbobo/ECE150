package edu.ucsb.ece150.gauchopay;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CardListActivity extends AppCompatActivity {

    private static final int RC_HANDLE_INTERNET_PERMISSION = 2;

    public static String APP_NAME = "Gauchopay";
    public static String CARD_INFO = "CardInfo";
    public static String ALL_CARDS = "AllCards";
    public static String IS_CARD_LIST = "CardList";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferenceEditor;

    private ArrayList<String> cardArray;
    private ArrayAdapter adapter;

    private ListView cardList;
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    public static String amount;
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    // Launch the asynchronous process to grab the web API
                    new ReadWebServer(getApplicationContext()).execute("");
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);

        // Ensure that we have Internet permissions
        int internetPermissionGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if(internetPermissionGranted != PackageManager.PERMISSION_GRANTED) {
            final String[] permission = new String[] {Manifest.permission.INTERNET};
            ActivityCompat.requestPermissions(this, permission, RC_HANDLE_INTERNET_PERMISSION);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState != null){
            //Previously saved from rotation
            cardArray = savedInstanceState.getStringArrayList(IS_CARD_LIST);
        }else{
            //no saved data
            sharedPreferences = getSharedPreferences(APP_NAME,MODE_PRIVATE);
            String json = sharedPreferences.getString(ALL_CARDS, "");
            if(json.length() != 0){
                //convert form json to card array
                Gson gson = new Gson();
                cardArray = gson.fromJson(json, new TypeToken<ArrayList<String>>() {}.getType());
            }else{
                //initialize cardarray
            }
            cardArray = new ArrayList<>();
        }

        cardList = findViewById(R.id.cardList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cardArray);
        cardList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toAddCardActivity = new Intent(getApplicationContext(), AddCardActivity.class);
                startActivity(toAddCardActivity);
            }
        });

        cardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int posID = (int) id;

                // If "lastAmount > 0" the last API call is a valid request (that the user must
                // respond to.
                if (ReadWebServer.getLastAmount() != 0) {
                    // [TODO] Send the card information back to the web API. Reference the
                    // WriteWebServer constructor to know what information must be passed.
                    // Get the card number from the cardArray based on the position in the array.

                    Log.v("success", String.valueOf(ReadWebServer.getLastAmount()));
                    String cardNumber = (String) (cardArray.get(posID)).substring(5);
                    amount = "\nCard: "+cardNumber+" \nHas paid: "+ "$" + String.valueOf(ReadWebServer.getLastAmount());
                    WriteWebServer writerWebServer = new WriteWebServer(getApplicationContext(), cardNumber);
                    writerWebServer.execute();
                    // Reset the stored information from the last API call
                    ReadWebServer.resetLastAmount();
                }
            }
        });

        // Start the timer to poll the webserver every 5000 ms
        timer.schedule(task, 0, 5000);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(IS_CARD_LIST,cardArray);
    }

    @Override
    protected void onResume() {
        super.onResume();


        sharedPreferences = getSharedPreferences(APP_NAME, MODE_PRIVATE);
        String cardNumber = sharedPreferences.getString(CARD_INFO,"");
        if(cardNumber.length() !=0){
            cardArray.add("Card " + cardNumber);
            adapter.notifyDataSetChanged();
            Toast.makeText(this,"Card has been added",Toast.LENGTH_SHORT).show();
        }
        sharedPreferenceEditor = sharedPreferences.edit();
        sharedPreferenceEditor.clear();
        sharedPreferenceEditor.commit();
        // [TODO] This is a placeholder. Modify the card information in the cardArray ArrayList
        // accordingly.
 //       cardArray.clear();

        // This is how you tell the adapter to update the ListView to reflect the current state
        // of your ArrayList (which holds all of the card information).
  //      adapter.notifyDataSetChanged();
   }

   @Override
    protected void onPause(){
        super.onPause();

        sharedPreferences = getSharedPreferences(APP_NAME, MODE_PRIVATE);
        sharedPreferenceEditor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(cardArray);

        sharedPreferenceEditor.putString(ALL_CARDS,json);
        sharedPreferenceEditor.apply();
   }
}

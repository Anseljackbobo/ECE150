package edu.ucsb.ece150.pickture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/*
 * This is the main activity of Pickture. It will should display the user's profile picture
 * and the user's first/last name. An example ImageView and example picture is given.
 *
 * Remember to read through all available documentation (there are so many Android development
 * guides that can be found) and read through your error logs.
 */
public class ProfileActivity extends AppCompatActivity {
    static final String PREFS_NAME = "prefile";
    int value;
    ImageView mainImage;
    String saveKey = "saveKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        mainImage = (ImageView) this.findViewById(R.id.imageView);
        mainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [TODO] Implement application behavior when the user clicks the profile picture
                Intent intent = new Intent(ProfileActivity.this , Gallery.class);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onPause() {

        super.onPause();
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(saveKey,value);
        editor.apply();

        // [TODO] Hint: You will need this for implementing graceful app shutdown
    }

    @Override
    protected void onResume() {
        super.onResume();

        // [TODO] Hint: You will need this for implementing graceful app shutdown
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        value = preferences.getInt(saveKey, R.drawable.im1);
        final Intent intent = getIntent();
        Bundle ImageID1 = getIntent().getExtras();
        if (ImageID1 != null){
            value = ImageID1.getInt("ImageID");
        }
        mainImage.setImageResource(value);
    }

    /*
     * You may or may not need this function depending on how you decide to pass messages
     * between your activities.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // [TODO] "I bring news from the nether!"
    }
    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }
}

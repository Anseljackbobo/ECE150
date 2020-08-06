package edu.ucsb.ece150.pickture;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;

public class Gallery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_gallery);

        final ImageView image1 = (ImageView) this.findViewById(R.id.imageView2);
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [TODO] Implement application behavior when the user clicks the profile picture

                Intent intent = new Intent(Gallery.this, ProfileActivity.class);
                intent.putExtra("ImageID", R.drawable.im2);
                startActivity(intent);
            }
        });
        final ImageView image2 = (ImageView) this.findViewById(R.id.imageView3);
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [TODO] Implement application behavior when the user clicks the profile picture
                Intent intent = new Intent(Gallery.this, ProfileActivity.class);
                int d;
                intent.putExtra( "ImageID" , R.drawable.im3);
                startActivity(intent);
            }
        });
        final ImageView image3 = (ImageView) this.findViewById(R.id.imageView4);
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [TODO] Implement application behavior when the user clicks the profile picture
                Intent intent = new Intent(Gallery.this, ProfileActivity.class);
                intent.putExtra("ImageID", R.drawable.im4);
                startActivity(intent);
            }
        });
        final ImageView image4 = (ImageView) this.findViewById(R.id.imageView5);
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [TODO] Implement application behavior when the user clicks the profile picture
                Intent intent = new Intent(Gallery.this, ProfileActivity.class);
                intent.putExtra("ImageID", R.drawable.im5);
                startActivity(intent);
            }
        });
        final ImageView image5 = (ImageView) this.findViewById(R.id.imageView6);
        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [TODO] Implement application behavior when the user clicks the profile picture
                Intent intent = new Intent(Gallery.this, ProfileActivity.class);
                intent.putExtra("ImageID", R.drawable.im6);
                startActivity(intent);
            }
        });
        final ImageView image6 = (ImageView) this.findViewById(R.id.imageView7);
        image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [TODO] Implement application behavior when the user clicks the profile picture
                Intent intent = new Intent(Gallery.this, ProfileActivity.class);
                intent.putExtra("ImageID", R.drawable.im8);
                startActivity(intent);
            }
        });
    }

}


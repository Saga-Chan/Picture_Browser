package com.example.picture_browser_imt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView appLogo = findViewById(R.id.logo);
        appLogo.setImageResource(R.drawable.logo);
        Button take_photo = findViewById(R.id.photoLayout);
        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera_intent = new Intent(MainActivity.this, TakePicture.class);
                startActivity(camera_intent);
            }
        });
        Button goToGallery = findViewById(R.id.galleryLayout);
        goToGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(MainActivity.this, Gallery.class);
                startActivity(galleryIntent);
            }
        });
    }

}

package com.example.picture_browser_imt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakePicture extends MainActivity {

    private static final int CAMERA_REQUEST=1888;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.logo);
        Button photoButton = this.findViewById(R.id.photoLayout);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureName = getPictureName();
                File imageFile = new File(pictureDirectory, pictureName);
                Uri pictureURI = Uri.fromFile(imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureURI);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
    }

    private String getPictureName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmSS");
        String timeStamp = sdf.format(new Date());
        return "Plane place image"+timeStamp+".jpg";
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_OK){
            if(requestCode == CAMERA_REQUEST){

            }
        }
    }
}

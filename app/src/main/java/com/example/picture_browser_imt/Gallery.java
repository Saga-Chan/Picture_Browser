package com.example.picture_browser_imt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class Gallery extends TakePicture {

    Button pickImage;
    Button analyze;
    ImageView imageGallery;
    Uri imageUri;
    private static final int PICK_IMAGE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        imageGallery = findViewById(R.id.imageGallery);
        pickImage = findViewById(R.id.pickImage);
        analyze = findViewById(R.id.analyze);

        pickImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               openGallery();
           }
        });

        analyze.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Bitmap bitmap = ((BitmapDrawable) imageGallery.getDrawable()).getBitmap();
                CharSequence base64String = ImageToBase64(bitmap);
                CharSequence newBase64 = "data:image/jpeg:base64,/9j/"+base64String;
               System.out.println(newBase64);
               Intent search_intent = new Intent(Gallery.this, SearchPage.class);

               ByteArrayOutputStream bStream = new ByteArrayOutputStream();
               Bitmap bitmap2 = ((BitmapDrawable) imageGallery.getDrawable()).getBitmap();
               bitmap2.compress(Bitmap.CompressFormat.PNG, 100, bStream);
               byte[] byteArray = bStream.toByteArray();
               System.out.println(byteArray);
               search_intent.putExtra("image", byteArray);



               startActivity(search_intent);
           }
        });
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imageGallery.setImageURI(imageUri);
        }
    }

    private CharSequence ImageToBase64 (Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        CharSequence encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encoded;
    }
}

package com.example.picture_browser_imt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;

public class SearchPage extends Gallery {

    TextView textView;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        textView = (TextView) findViewById(R.id.information);
        bitmap = (Bitmap) BitmapFactory.decodeResource(getResources(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... voids) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();

                String encodeImage = Base64.encodeToString(b, Base64.DEFAULT);

                return encodeImage;
            }

            @Override
            protected void onPostExecute(String s){
                textView.setError(s);
            }
        }.execute();
/**
        Intent i = getIntent();

        int position = i.getExtras().getInt("id");
        ImageAdapter adapter = new ImageAdapter(this);

        ImageView imageView = findViewById(R.id.answerLogo);
        imageView.setImageResource(adapter.images[position]);
 **/
    }

}

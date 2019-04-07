package com.example.picture_browser_imt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class SearchPage extends Gallery {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        Bitmap bmp;
        //TextView textView = findViewById(R.id.information);
        ImageView answerLogo = findViewById(R.id.answerLogo);
        ImageView proposal1 = findViewById(R.id.proposal1);
        ImageView proposal2 = findViewById(R.id.proposal2);
        ImageView proposal3 = findViewById(R.id.proposal3);
        Bundle extras = getIntent().getExtras();
        /**DownloadImageTask picture1 = new DownloadImageTask(proposal1).execute("<url>");
        DownloadImageTask picture2 = new DownloadImageTask(proposal2).execute("<url>");
        DownloadImageTask picture3 = new DownloadImageTask(proposal3).execute("<url>");**/
        // Ensuite, transformer en liste java pour avoir "resultat 1 ; score"

        /**String information="L'image correspond à : %s avec une probabilité de %i", logo, proba;
        textView.setText(information);**/

        byte[] byteArray = extras.getByteArray("image");
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        answerLogo.setImageBitmap(bmp);
    }
}

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmpImage;

    public DownloadImageTask(ImageView bmpImage) {
        this.bmpImage = bmpImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }
}

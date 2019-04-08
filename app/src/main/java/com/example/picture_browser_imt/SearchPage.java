package com.example.picture_browser_imt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class SearchPage extends Gallery {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);
        Bundle extras = getIntent().getExtras();
        String newString = extras.getString("response");
        JSONArray responseJson;
        ArrayList<String> responseList = new ArrayList<>();

        try{
            responseJson = new JSONObject(newString).getJSONArray("response");
            for(int i=0; i<responseJson.length(); i++){
                JSONObject image = responseJson.getJSONObject(i);
                String url = (String) image.get("result");
                responseList.add(url);
            }
            ImageView resultPic = findViewById(R.id.proposal1);
            ImageView resultPic2 = findViewById(R.id.proposal2);
            ImageView resultPic3 = findViewById(R.id.proposal3);
            ImageView initialPic = findViewById(R.id.answerLogo);


            Bitmap bmp;
            byte[] byteArray = extras.getByteArray("image");
            bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            initialPic.setImageBitmap(bmp);

            System.out.println(responseList);
            new DownloadImageTask(resultPic).execute(responseList.get(0));
            new DownloadImageTask(resultPic2).execute(responseList.get(1));
            new DownloadImageTask(resultPic3).execute(responseList.get(2));

        }
        catch (JSONException e){
            Log.d(e.toString(),"Error from catch");
        }

        Bitmap bmp;
        ImageView answerLogo = findViewById(R.id.answerLogo);
        ImageView proposal1 = findViewById(R.id.proposal1);
        ImageView proposal2 = findViewById(R.id.proposal2);
        ImageView proposal3 = findViewById(R.id.proposal3);


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

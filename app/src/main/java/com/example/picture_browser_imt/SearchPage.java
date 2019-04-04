package com.example.picture_browser_imt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchPage extends Gallery {

    String logo;
    int proba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        Bitmap bmp;
        TextView textView = findViewById(R.id.information);
        ImageView answerLogo = findViewById(R.id.answerLogo);
        ImageView proposal1 = findViewById(R.id.proposal1);
        ImageView proposal2 = findViewById(R.id.proposal2);
        ImageView proposal3 = findViewById(R.id.proposal3);
        Bundle extras = getIntent().getExtras();

        String information="L'image correspond à : %s avec une probabilité de %i", logo, proba;
        textView.setText(information);

        byte[] byteArray = extras.getByteArray("image");
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        answerLogo.setImageBitmap(bmp);
    }
}

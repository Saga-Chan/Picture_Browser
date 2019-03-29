package com.example.louis.picture_browser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class PictureBrowser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_page);

        Button scan = (Button) findViewById(R.id.button_scan);
        scan.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.i("premier_test", "Déplacement sur la page de Scan");
                Intent myIntent = new Intent(PictureBrowser.this, ScanningActivity.class);
                startActivity(myIntent);
            }
        });

        Button gallery = (Button) findViewById(R.id.button_gallery);
        gallery.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.i("deuxieme_test", "^o^ Hello World !");
            }
        });
        scan.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.i("troisième_test", "Déplacement sur la page de Gallery");
                Intent myIntent = new Intent(PictureBrowser.this, GalleryActivity.class);
                startActivity(myIntent);
            }
        });

        Button results = (Button) findViewById(R.id.button_results);
        scan.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.i("quatrieme_test", "Déplacement sur la page de Resultats / l'historique");
                Intent myIntent = new Intent(PictureBrowser.this, ResultsActivity.class);
                startActivity(myIntent);
            }
        });
    }
}

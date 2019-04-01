package com.example.picture_browser_imt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakePicture extends MainActivity implements SurfaceHolder.Callback{

    private Button capture;
    private SurfaceView surfViewCam;
    private Camera camera;
    private SurfaceHolder surfaceHolder;
    private Camera.PictureCallback pictureCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_picture);

        surfViewCam = findViewById(R.id.sViewCamera);
        capture = findViewById(R.id.capture);

        surfaceHolder = surfViewCam.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        capture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                camera.takePicture(null, null, pictureCallback);
            }
        });

        pictureCallback = new Camera.PictureCallback(){
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Bitmap cbmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), null, true);

                String pathFileName = currentDateFormat();
                storePhotoToStorage(cbmp, pathFileName);

                Toast.makeText(getApplicationContext(), "Done !!!", Toast.LENGTH_LONG).show();

                TakePicture.this.camera.startPreview();
            }
        };
    }

    private void storePhotoToStorage(Bitmap cbmp, String pathFileName) {
        File outputFile = new File(Environment.getExternalStorageDirectory(), "/DCIM/"+"photo_"+pathFileName+".jpg");
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            cbmp.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String currentDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String currentTime = dateFormat.format(new Date());
        return currentTime;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try{
            camera = Camera.open();
        } catch (Exception e){

        }

        Camera.Parameters parameters;
        parameters = camera.getParameters();
        parameters.setPreviewFrameRate(20);
        parameters.setPreviewSize(352, 288);
        camera.setParameters(parameters);
        camera.setDisplayOrientation(90);
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

}

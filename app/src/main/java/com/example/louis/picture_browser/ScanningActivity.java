package com.example.louis.picture_browser;

import android.graphics.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class VideoServer extends PictureBrowser implements SurfaceHolder.Callback {

    TextView testView;
    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    android.hardware.Camera.PictureCallback rawCallback;
    android.hardware.Camera.ShutterCallback shutterCallback;
    android.hardware.Camera.PictureCallback jpegCallback;

    private final String tag = "VideoServer";

    Button start, stop, capture;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scaning_page);

        start = (Button)findViewById(R.id.btn_scan_scan);
        start.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                start_camera();
            }
        });
        stop = (Button)findViewById(R.id.btn_scan_scan);
        capture = (Button) findViewById(R.id.btn_scan_scan);
        stop.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View arg0) {
                stop_camera();
            }
        });
        capture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                captureImage();
                }
        });

        surfaceView = (SurfaceView)findViewById(R.id.surfaceViewCamera);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        rawCallback = new android.hardware.Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, android.hardware.Camera camera) {
                Log.d("Log", "onPictureTaken - raw");
            }
        };

        /** Handles data for jpeg picture */
        shutterCallback = new android.hardware.Camera.ShutterCallback() {
            public void onShutter() {
                Log.i("Log", "onShutter'd");
            }
        };
        jpegCallback = new android.hardware.Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, android.hardware.Camera camera) {
                FileOutputStream outStream = null;
                try {
                    outStream = new FileOutputStream(String.format("/sdcard/%d.jpg", System.currentTimeMillis()));
                    outStream.write(data);
                    outStream.close();
                    Log.d("Log", "onPictureTaken - wrote bytes: " + data.length);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
                Log.d("Log", "onPictureTaken - jpeg");
            }
        };
    }

    private void captureImage() {
        // TODO Auto-generated method stub
        camera.takePicture(shutterCallback, rawCallback, jpegCallback);
    }

    private void start_camera() {
        try{
            camera = Camera.open();
        }catch(RuntimeException e){
            Log.e(tag, "init_camera: " + e);
            return;
        }
        Camera.Parameters param;
        param = camera.getParameters();
        //modify parameter
        param.setPreviewFrameRate(20);
        param.setPreviewSize(176, 144);
        camera.setParameters(param);
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            //camera.takePicture(shutter, raw, jpeg)
        } catch (Exception e) {
            Log.e(tag, "init_camera: " + e);
            return;
        }
    }

    private void stop_camera(){
        camera.stopPreview();
        camera.release();
    }

    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
    }
}

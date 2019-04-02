package com.example.picture_browser_imt;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Gallery extends TakePicture {

    ImageView imageGallery;
    Uri imageUri;
    private static final int PICK_IMAGE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        this.openGallery();
/**
        galleryGrid.setAdapter(new ImageAdapter(this));

        galleryGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(Gallery.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
 **/
    }
/**
    public void pickImage(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data!=null){
            Uri uri = data.getData();

            String path = getRealPathFromUri(this, uri);
            String name = getFileName(uri);

            try {
                insertInPrivateStorage(name, path);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertInPrivateStorage(String name, String path) throws IOException {
        FileOutputStream fos = openFileOutput(name, MODE_APPEND);
        File file = new File(path);
        byte[] bytes = getBytesFromFile(file);
        fos.write(bytes);
        fos.close();
        Toast.makeText(getApplicationContext(), "File saved in : "+getFilesDir() + "/"+name, Toast.LENGTH_SHORT).show();
    }

    private byte[] getBytesFromFile(File file) throws IOException {
        byte[] data = FileUtils.ReadFileToByteArray(file);
        return data;
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")){
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()){
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null){
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1){
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private String getRealPathFromUri(Context context, Uri uri) {
        String proj = (MediaStore.Images.Media.DATA);
        Cursor cursor = context.getContentResolver().query(uri, new String[]{proj}, null, null, null);
        if (cursor != null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return null;
    }

    private static class FileUtils {
        public static byte[] ReadFileToByteArray(File file) {
            return null;
        }
    }
    **/

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imageGallery.setImageURI(imageUri);
        }
    }
}

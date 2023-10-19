package com.project.identranaccess.database;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapToUriConverter {


    public static Uri convertBitmapToUri(Context context, Bitmap bitmap) {
        Uri imageUri = null;
        try {
            // Create a file to save the bitmap

            ContextWrapper cw = new ContextWrapper(context);
            File directory = cw.getDir("images", Context.MODE_PRIVATE);
            File imageFile = new File(directory, "image.jpg");

            // Write the bitmap data to the file
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            // Use FileProvider to get a content URI
           // imageUri = FileProvider.getUriForFile(context, "com.example.identranaccess.database", imageFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageUri;
    }
}

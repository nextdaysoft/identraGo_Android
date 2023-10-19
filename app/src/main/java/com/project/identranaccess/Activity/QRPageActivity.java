package com.project.identranaccess.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.navigation.ui.AppBarConfiguration;

import com.project.identranaccess.R;
import com.project.identranaccess.databinding.ActivityQrpageBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;


import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRPageActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityQrpageBinding binding;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    Uri uri;

    private static final int REQUEST_EXTERNAL_STORAGe = 1;
    private static String[] permissionstorage = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityQrpageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);

       final Bundle bundle = getIntent().getExtras();
        Intent i= getIntent();
        String code =   i.getStringExtra("indexno");
        Log.e("data", "onCreate: "+code );
        String QRcode =   i.getStringExtra("QRCODE");
        Log.e("data", "onCreate: "+QRcode );

       if (code!=null) {
           qrGenerateMethod(code);
       }else{
           qrGenerateMethod(QRcode);
       }

        verifyStoragePermission(QRPageActivity.this);
       //  screenshot(getWindow().getDecorView().getRootView(), "result");

    /*    Call<ArrayList<QRClass>> call =RetrofitClient.getInstance().getMyApi().getAllData();
        call.enqueue(new Callback<ArrayList<QRClass>>() {
            @Override
            public void onResponse(Call<ArrayList<QRClass>> call, Response<ArrayList<QRClass>> response) {
                ArrayList<QRClass> myheroList = response.body();
                String[] qr_codes = new String[myheroList.size()];

                for ( int i = 0; i < myheroList.size();i++ ) {

                    qr_codes[i] = String.valueOf(myheroList.get(j).getCode());

                    Random random = new Random();
                    int randomIndex = random.nextInt(myheroList.size());
                    int randomNumber = Integer.parseInt(myheroList.get(randomIndex).getCode());

                    qrGenerateMethod(qr_codes[i]);

                   // Toast.makeText(QRPageActivity.this, qr_codes[j], Toast.LENGTH_SHORT).show();
                    Log.v("userList", qr_codes[i]);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<QRClass>> call, Throwable t) {
                Toast.makeText(QRPageActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
        binding.BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getScreenShot(getWindow().getDecorView().getRootView());
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    public  Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();

        Bitmap bitmap = Bitmap.createBitmap(binding.llLayout.getWidth(), binding.llLayout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        //  Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        screenView.setDrawingCacheEnabled(false);
        sendDrawble(bitmap);
        return bitmap;
    }
/*
    protected  File screenshot(View view, String result) {
        Date date = new Date();
        // Here we are initialising the format of our image name
        CharSequence format = android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);
        try {
            String dirpath = Environment.getExternalStorageDirectory() + "";
            File file = new File(dirpath);
            if (!file.exists()) {
                boolean mkdir = file.mkdir();
            }
            // File name
            String path = dirpath + "/" + result + "-" + format + ".jpeg";
            view.setDrawingCacheEnabled(true);
          //  Bitmap bitmap = Bitmap.createBitmap(binding.llLayout.getWidth(), binding.llLayout.getHeight(), Bitmap.Config.ARGB_4444);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            view.setDrawingCacheEnabled(false);
            File imageurl = new File(path);
            FileOutputStream outputStream = new FileOutputStream(imageurl);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
            outputStream.flush();
            outputStream.close();

            Toast.makeText(QRPageActivity.this, "method", Toast.LENGTH_SHORT).show();

            return imageurl;

        } catch (FileNotFoundException io) {
            io.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
*/

    private  void sendDrawble(Bitmap bitmap) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(),bitmap, "Title", null);
        Uri imageUri =  Uri.parse(path);
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(share, "Select"));
    }

    public void qrGenerateMethod(String qr_code) {
        Log.d("arraylist", "qrGenerateMethod: "+qr_code);
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        // generating dimension from width and height.
        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;
        qrgEncoder = new QRGEncoder(qr_code, null, QRGContents.Type.TEXT, dimen);
        bitmap=qrgEncoder.getBitmap();
        binding.QrImage.setImageBitmap(bitmap);
    }

    public static void verifyStoragePermission(Activity activity) {
        int permissions = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // If storage permission is not given then request for External Storage Permission
        if (permissions != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, permissionstorage, REQUEST_EXTERNAL_STORAGe);
        }
    }

}



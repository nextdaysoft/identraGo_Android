package com.project.identranaccess.Activity;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.identranaccess.database.MyLocalDatabase;
import com.project.identranaccess.database.Utility;
import com.project.identranaccess.databinding.ActivityEditProfileBinding;
import com.project.identranaccess.model.ProfileData;
import com.project.identranaccess.model.RegisterDataModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {
    private static final int SELECT_FILE = 1;
    private static final int REQUEST_CAMERA = 2;
    private AppBarConfiguration appBarConfiguration;
    private ActivityEditProfileBinding binding;
    MyLocalDatabase myLocalDatabase;
    Bitmap photo;
    Uri selectedImage;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageReference;
    Bitmap imageBitmap;
    DatabaseReference databaseReference;
    Uri taskResult;
    RegisterDataModel profileModel;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myLocalDatabase = new MyLocalDatabase(this);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        profileModel=new RegisterDataModel();
        //   binding.etAdminName.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        binding.etUserLocationId.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        binding.etUserName.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        db = FirebaseFirestore.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference();

        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
        checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);

        binding.BtnSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etUserName.getText().toString().trim().isEmpty()) {
                    binding.etUserName.setError("Por favor ingresa tu Usuario");
                    binding.etUserName.requestFocus();
                } else if (binding.etUserEmailId.getText().toString().trim().isEmpty()) {
                    binding.etUserEmailId.setError("Campos requeridos");
                    binding.etUserEmailId.requestFocus();
                } else if (binding.etMob.getText().toString().trim().isEmpty()) {
                    binding.etMob.setError("Campos requeridos");
                    binding.etMob.requestFocus();
                } else if (binding.etUserLocationId.getText().toString().trim().isEmpty()) {
                    binding.etUserLocationId.setError("Campos requeridos");
                    binding.etUserLocationId.requestFocus();
                } else {
                    profile_Data(taskResult,binding.etUserName.getText().toString().trim(),
                            binding.etUserEmailId.getText().toString().trim(),
                            binding.etMob.getText().toString().trim(),
                            binding.etUserLocationId.getText().toString().trim());
                }
            }
        });

        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {"TOMAR FOTO", "ELIGE DE LA BIBLIOTECA",
                        "CANCELAR"};
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                builder.setTitle("¡AÑADIR FOTO!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("TOMAR FOTO")) {
                            cameraIntent();
                        } else if (items[item].equals("ELIGE DE LA BIBLIOTECA")) {
                            galleryIntent();
                        } else if (items[item].equals("CANCELAR")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
    }

  private void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(EditProfileActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(EditProfileActivity.this, new String[] { permission }, requestCode);
        }
        else {
         //   Toast.makeText(EditProfileActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void profile_Data(Uri downloadUrl, String name, String email, String mobileno, String pos) {

        CollectionReference dbCourses = db.collection("userprofileDetails");
        profileModel.setName(Objects.requireNonNull(binding.etUserName.getText()).toString().trim());
        profileModel.setEmail(Objects.requireNonNull(binding.etUserEmailId.getText()).toString().trim());
        profileModel.setMobile(Objects.requireNonNull(binding.etMob.getText()).toString().trim());
        profileModel.setImage(Objects.requireNonNull(String.valueOf(downloadUrl)));
        profileModel.setLocation(Objects.requireNonNull(binding.etUserLocationId.getText()).toString().trim());
        profileModel.setUserId(Objects.requireNonNull(Utility.getUserId(this,"userId")));

      //  = new ProfileData(downloadUrl, name, email, mobileno, pos);
        db.collection("RegisterUser").document(Utility.getUserId(this,"userId"))
                .update("mobile", binding.etMob.getText().toString().trim(),
                        "email",binding.etUserEmailId.getText().toString().trim(),
                        "name",binding.etUserName.getText().toString().trim(),
                        "image",String.valueOf(downloadUrl),
                        "loc",binding.etUserLocationId.getText().toString().trim());

     /*   dbCourses.add(profileModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                //  dialogFragment.hideDialog();
                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
            }
        })*/;
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);

    }

    private void cameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_CAMERA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECT_FILE) {
            try {
                selectedImage = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                binding.profileImage.setImageBitmap(BitmapFactory.decodeStream(imageStream));
                Log.e(TAG, "onActivityResult: " + selectedImage);
                uploadImage(selectedImage);

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            binding.profileImage.setImageBitmap(imageBitmap);
            uploadImagebitmap(imageBitmap);
        }
    }

    private void uploadImagebitmap(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
        Uri.parse(path);
        uploadImage(Uri.parse(path));
    }

    private void uploadImage(Uri imageBitmap) {

        final StorageReference imgReference  = storageReference.child(Utility.getUserId(this,"userId")).child("userImage.png");
        UploadTask uploadTask = imgReference.putFile(imageBitmap);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return imgReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                     taskResult = task.getResult();
                    Log.e(TAG, "onComplete: "+taskResult );
                  //  FriendlyMessage message = new FriendlyMessage(null, mUsername, taskResult.toString());
                    //   mMessagesDatabaseReference.push().setValue(message);
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(EditProfileActivity.this, "Camera Permission Granted", Toast.LENGTH_SHORT) .show();
            }
            else {
                Toast.makeText(EditProfileActivity.this, "Camera Permission Denied", Toast.LENGTH_SHORT) .show();
            }
        }
              else if (requestCode == STORAGE_PERMISSION_CODE) {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(EditProfileActivity.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditProfileActivity.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
}

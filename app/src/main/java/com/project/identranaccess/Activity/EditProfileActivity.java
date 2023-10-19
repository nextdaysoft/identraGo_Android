package com.project.identranaccess.Activity;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import com.project.identranaccess.databinding.ActivityEditProfileBinding;
import com.project.identranaccess.model.ProfileData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myLocalDatabase = new MyLocalDatabase(this);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        //   binding.etAdminName.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        binding.etUserpos.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        binding.etUserName.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        db = FirebaseFirestore.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference();

        binding.BtnSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    getimage();

                /*Bitmap image = ((BitmapDrawable)binding.profileImage.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                image.recycle();*/
                if (binding.etUserName.getText().toString().trim().isEmpty()) {
                    binding.etUserName.setError("Por favor ingresa tu Usuario");
                    binding.etUserName.requestFocus();
                } else if (binding.etAdminName.getText().toString().trim().isEmpty()) {
                    binding.etAdminName.setError("Campos requeridos");
                    binding.etAdminName.requestFocus();
                } else if (binding.etMob.getText().toString().trim().isEmpty()) {
                    binding.etMob.setError("Campos requeridos");
                    binding.etMob.requestFocus();
                } else if (binding.etUserpos.getText().toString().trim().isEmpty()) {
                    binding.etUserpos.setError("Campos requeridos");
                    binding.etUserpos.requestFocus();
                } else {
                    profile_Data(taskResult,binding.etUserName.getText().toString().trim(),
                            binding.etAdminName.getText().toString().trim(),
                            binding.etMob.getText().toString().trim(),
                            binding.etUserpos.getText().toString().trim());
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
                        //   boolean result=Utility.checkPermission(MainActivity.this);
                        if (items[item].equals("TOMAR FOTO")) {
                            // userChoosenTask="Take Photo";
                            //      if(result)
                            cameraIntent();
                        } else if (items[item].equals("ELIGE DE LA BIBLIOTECA")) {
                            //userChoosenTask="Choose from Library";
                            // if(result)
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

    private void getimage() {
        DatabaseReference getImage = databaseReference.child("images");
        getImage.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String link = dataSnapshot.getValue(String.class);
                        Log.e(TAG, "onDataChange: " + link.toString());

                        //     Picasso.get().load(link).into(rImage);
                    }

                    // this will called when any problem
                    // occurs in getting data
                    @Override
                    public void onCancelled(
                            @NonNull DatabaseError databaseError) {
                        // we are showing that error message in toast
                        Toast.makeText(EditProfileActivity.this, "Error Loading Image", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void profile_Data(Uri downloadUrl, String name, String email, String mobileno, String pos) {

        CollectionReference dbCourses = db.collection("userprofileDetails");

        // adding our data to our courses object class.
        ProfileData profileModel = new ProfileData(downloadUrl, name, email, mobileno, pos);

        dbCourses.add(profileModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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
        });
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
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

    private void uploadImagebitmap(Bitmap imageBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap, "Title", null);
        Uri.parse(path);
        uploadImage(Uri.parse(path));
    }

    private void uploadImage(Uri imageBitmap) {

        final StorageReference imgReference  = storageReference.child("2gjfdhdhdhg").child("userImage.png");
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

}

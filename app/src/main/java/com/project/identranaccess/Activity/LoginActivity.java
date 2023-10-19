package com.project.identranaccess.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.project.identranaccess.Fragment.LottieDialogFragment;
import com.project.identranaccess.R;
import com.project.identranaccess.database.Utility;
import com.project.identranaccess.databinding.ActivityLoginBinding;
import com.project.identranaccess.model.RegisterDataModel;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    LottieDialogFragment loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db =  FirebaseFirestore.getInstance();

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loader = new LottieDialogFragment(this);

        // setSupportActionBar(binding.toolbar);
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etMob.getText().toString().trim().isEmpty()) {
                    binding.etMob.setError("Requerido este campo");
                    binding.etMob.requestFocus();
                }
               else if (binding.editPassword.getText().toString().trim().isEmpty()) {
                    binding.editPassword.setError("Por favor, ingrese contraseña");
                    binding.editPassword.requestFocus();
                }
                else {
                  //  binding.etMob.setText(null);
                //    binding.editPassword.setText(null);
                    signInUser();
                  /*  addDataFireStore(binding.etMob.getText().toString().trim(),
                            binding.editPassword.getText().toString().trim());*/

                }
            }
        });


        binding.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.etMob.setEnabled(true);
                binding.etMob.setInputType(InputType.TYPE_CLASS_NUMBER);
                binding.codeNo.setVisibility(View.VISIBLE);
                binding.etMob.setHint("NÚMERO TELEFÓNICO");
                binding.etMob.setText("");

            }
        });

        binding.mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.etMob.setEnabled(true);
                binding.etMob.setInputType(InputType.TYPE_CLASS_TEXT);
                binding.codeNo.setVisibility(View.GONE);
                binding.etMob.setHint("CORREO ELECTRÓNICO");
            }
        });

        binding.registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RegistrationPage.class);
                startActivity(i);
            }
        });
    }

    private void signInUser() {

        mAuth.signInWithEmailAndPassword(Objects.requireNonNull(binding.etMob.getText()).toString(), Objects.requireNonNull(binding.editPassword.getText()).toString()).addOnCompleteListener(task -> {
           loader.show();
            if (task.isSuccessful()) {
                loader.cancel();
                getCurrentUserData(Objects.requireNonNull(task.getResult().getUser()).getUid());
                Utility.addUserId(getApplicationContext(),"userId",task.getResult().getUser().getUid());


            } else {
               // hideProgress();
                Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                loader.cancel();

            }


        });

    }
    private void getCurrentUserData(String uid) {

        db.collection("RegisterUser")
                .document(uid)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        RegisterDataModel userModalClass1 = task.getResult().toObject(RegisterDataModel.class);
                        if (userModalClass1 != null) {
                      //      Utility.addPreferences(LogInSignupActivity.this, Constants.userData, new Gson().toJson(userModalClass1));
                           // navigateHome();
                            Intent i = new Intent(getApplicationContext(), HomePageActivity.class);
                            startActivity(i);
                        } else {
                          //  hideProgress();
                        }
                    } else {
                     //   hideProgress();
                    }


                });
    }


    private void addDataFireStore(String trim, String trim1) {

    }

    @Override
    public void onClick(View view) {
        ShowHidePass(view);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        binding.codeNo.setVisibility(View.VISIBLE);
    }

    public void ShowHidePass(View view) {

        if (view.getId() == R.id.show_pass_btn) {
            if (binding.editPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.eye);
                //Show Password
                binding.editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.hide);
                //Hide Password
                binding.editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }

}
package com.project.identranaccess.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.identranaccess.Fragment.LottieDialogFragment;
import com.project.identranaccess.R;
import com.project.identranaccess.databinding.ActivityRegistrationPageBinding;
import com.project.identranaccess.model.RegisterDataModel;

import java.util.Objects;

public class RegistrationPage extends AppCompatActivity {


    ActivityRegistrationPageBinding binding;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String email;
    private FirebaseAuth mAuth;

    LottieDialogFragment dialogFragment;
    RegisterDataModel registerDataModel;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        binding = ActivityRegistrationPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        registerDataModel = new RegisterDataModel();

        mAuth = FirebaseAuth.getInstance();
        //     firebaseDatabase = FirebaseDatabase.getInstance();
        db = FirebaseFirestore.getInstance();
        dialogFragment = new LottieDialogFragment();
       /* binding.emailReg.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (email.matches(emailPattern) && s.length() > 0) {
                    Toast.makeText(getApplicationContext(), "valid email address", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });*/

        binding.BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()) {
                    mAuth.createUserWithEmailAndPassword(binding.emailReg.getText().toString().trim(), binding.passwordReg.getText().toString().trim()).addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            if (task.getResult() != null && task.getResult().getUser() != null) {
                                registerDataModel.setEmail(task.getResult().getUser().getEmail());
                                registerDataModel.setMobile(Objects.requireNonNull(binding.mobileNoEt.getText()).toString());
                                registerDataModel.setPassword(Objects.requireNonNull(binding.passwordReg.getText()).toString());

                                //   userModalClass.setUserToken(Utility.getPreferences(RegistrationPage.this, Constants.deviceToken, ""));
                                //  registerDataModel.setLoginType("Email");

                                registerDataModel.setUserId(task.getResult().getUser().getUid());
                                createUser(task.getResult().getUser().getUid());

                            }

                        } else {
                            // hideProgress();
                            Toast.makeText(RegistrationPage.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    });
                }
            }
        });

        binding.showPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.passwordReg.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    ((ImageView) (view)).setImageResource(R.drawable.eye);
                    //Show Password
                    binding.passwordReg.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    ((ImageView) (view)).setImageResource(R.drawable.hide);
                    //Hide Password
                    binding.passwordReg.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        binding.showConpassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.editConfirmPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    ((ImageView) (view)).setImageResource(R.drawable.eye);
                    //Show Password
                    binding.editConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    ((ImageView) (view)).setImageResource(R.drawable.hide);
                    //Hide Password
                    binding.editConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    private void createUser(String uid) {
        db.collection("RegisterUser")
                .document(uid)
                .set(registerDataModel)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Register Successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegistrationPage.this, HomePageActivity.class);
                        startActivity(i);
                    } else {
                        //  hideProgress();
                        Toast.makeText(RegistrationPage.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    }


                });
    }


    private boolean validation() {
        if (binding.emailReg.getText().toString().trim().isEmpty()) {
            binding.emailReg.setError("required this field");
            binding.emailReg.requestFocus();
            return false;
        } else if (binding.mobileNoEt.getText().toString().trim().isEmpty()) {
            binding.mobileNoEt.setError("required this field");
            binding.mobileNoEt.requestFocus();
            return false;
        } else if (binding.passwordReg.getText().toString().trim().isEmpty()) {
            binding.passwordReg.setError("required this field");
            binding.passwordReg.requestFocus();
            return false;
        } else if (binding.editConfirmPassword.getText().toString().trim().isEmpty()) {
            binding.editConfirmPassword.setError("required this field");
            binding.editConfirmPassword.requestFocus();
            return false;
        } else if (!binding.editConfirmPassword.getText().toString().trim().matches(binding.passwordReg.getText().toString().trim())) {
            binding.editConfirmPassword.setError("Please Enter same password");
            binding.editConfirmPassword.requestFocus();
            return false;
        }
        return true;
    }

/*
    private void ShowHidePass(View view) {

        if(view.getId()==R.id.show_pass_btn){

            if(binding.password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.eye);

                //Show Password
                binding.password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.hide);

                //Hide Password
                binding.password.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }

    }
    }
*/


}
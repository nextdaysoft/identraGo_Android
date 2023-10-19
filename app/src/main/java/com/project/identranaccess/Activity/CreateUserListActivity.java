package com.project.identranaccess.Activity;

import android.os.Bundle;

import com.project.identranaccess.database.MyLocalDatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import com.project.identranaccess.databinding.ActivityCreateUserListBinding;

public class CreateUserListActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityCreateUserListBinding binding;

  //  MyLocalDatabase dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateUserListBinding.inflate(getLayoutInflater());
       // dbHelper=new MyLocalDatabase(this);
        setContentView(binding.getRoot());

      String Name=  binding.nameRegData.getText().toString();
      String UserName=  binding.userRegData.getText().toString();
      String Email=  binding.emailRegData.getText().toString();
      String password=  binding.passwordRegData.getText().toString();

      binding.UserListsaveData.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if (binding.nameRegData.getText().toString().isEmpty()) {
                  binding.nameRegData.setError("por favor ingrese el nombre");
                  binding.nameRegData.requestFocus();
              } else if (binding.userRegData.getText().toString().isEmpty()) {
                  binding.userRegData.setError("por favor ingresa usuario sin espacio");
                  binding.userRegData.requestFocus();
              } else if (binding.emailRegData.getText().toString().isEmpty()) {
                  binding.emailRegData.setError("por favor ingrese el correo electrónico");
                  binding.emailRegData.requestFocus();
              } else if (binding.passwordRegData.getText().toString().isEmpty()) {
                  binding.passwordRegData.setError("Por favor, ingrese contraseña");
                  binding.passwordRegData.requestFocus();
              } else {
                 // dbHelper.addUserListData(binding.nameRegData.getText().toString().trim(), binding.userRegData.getText().toString().trim()
                       //   , binding.emailRegData.getText().toString().trim(), binding.passwordRegData.getText().toString().trim());

              binding.nameRegData.setText(null);
              binding.userRegData.setText(null);
              binding.emailRegData.setText(null);
              binding.passwordRegData.setText(null);
                  finish();
            /*  Intent i = new Intent(getApplicationContext(), HomePageActivity.class);
              startActivity(i);*/
              }
          }
      });

      binding.swDataSaveBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              binding.swDataSaveBtn.isChecked();
              if (binding.swDataSaveBtn.isChecked()){
                  binding.nameRegData.setText(Name);
                  binding.userRegData.setText(UserName);
                  binding.emailRegData.setText(Email);
                  binding.passwordRegData.setText(password);
              }else{
                  binding.nameRegData.setText(null);
                  binding.userRegData.setText(null);
                  binding.emailRegData.setText(null);
                  binding.passwordRegData.setText(null);
              }
          }
      });
      binding.backBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              finish();
          }
      });

    //    setSupportActionBar(binding.toolbar);

    }

}
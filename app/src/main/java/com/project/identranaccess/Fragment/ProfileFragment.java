package com.project.identranaccess.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.project.identranaccess.Activity.EditProfileActivity;
import com.project.identranaccess.database.MyLocalDatabase;
import com.project.identranaccess.databinding.FragmentProfileBinding;
import com.project.identranaccess.model.ProfileData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //private ArrayList<ProfileData> profileDataArraylist;
    private MyLocalDatabase dbHandler;
    Context mContext;
    String name ;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        mContext = getActivity();
        dbHandler = new MyLocalDatabase(mContext);
      //  if(dbHandler.profileData()!=null) {
        //    profileDataArraylist = dbHandler.profileData();

           // if(!profileDataArraylist.isEmpty()){
              //  for(int i = 0;i<profileDataArraylist.size();i++) {
                   // binding.userNameTV.setText(profileDataArraylist.get(i).getName());
               //     binding.Admin.setText(profileDataArraylist.get(i).getAdministration());
                   // binding.pos.setText(profileDataArraylist.get(i).getPos());

              //      byte[] byteArray = profileDataArraylist.get(i).getImage();
                 //   Log.e("bitmap", "onCreateView: "+profileDataArraylist.get(i).getImage());
              //      Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
               //     binding.profileImage.setImageBitmap(bmp);
             //   }
    //   }
          //  bitmap = dbHandler.getImage(name);
          //  binding.profileImage.setImageBitmap(profileDataArraylist.get(0).getImage());
         //   Log.d("details", "onCreateView: "+bitmap);

       /**/

        binding.privacyPolicyLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                //alert.setTitle("POLÍTICA DE PRIVACIDAD");

                WebView wv = new WebView(mContext);
                wv.loadUrl("https://identran.com/politicas-identran#privacidad-y-proteccion-identran-go");
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {

                        view.loadUrl(url);

                        return true;
                    }
                });

                alert.setView(wv);
                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });

        binding.editProfileLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), EditProfileActivity.class);
                startActivity(i);
            }
        });
        binding.logoutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

     //   viewImage();
        return binding.getRoot();
    }

    private void viewImage() {

    }
}
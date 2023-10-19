package com.project.identranaccess.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.identranaccess.Activity.EditProfileActivity;
import com.project.identranaccess.database.MyLocalDatabase;
import com.project.identranaccess.database.Utility;
import com.project.identranaccess.databinding.FragmentProfileBinding;
import com.project.identranaccess.model.ProfileData;

import java.util.ArrayList;
import java.util.List;

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
    public List<ProfileData> profileDataArraylist;
    Context mContext;
    String name ;
    FirebaseFirestore db;
    ProgressBar progressBar;

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
        db= FirebaseFirestore.getInstance();
        progressBar= new ProgressBar(mContext);

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

        db.collection("RegisterUser").whereEqualTo("userId",Utility.getUserId(mContext,"userId"))
                .whereEqualTo("userId",Utility.getUserId(mContext,"userId"))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        profileDataArraylist = task.getResult().toObjects(ProfileData.class);
                        for(int i =0;i<profileDataArraylist.size();i++){
                            Glide.with(mContext).load(profileDataArraylist.get(i).getImage()).into(binding.profileImage);

                          //  Glide.get().load(profileDataArraylist.get(i).getImage()).into(binding.profileImage);
                             binding.locationId.setText(profileDataArraylist.get(i).getLocation());
                             binding.EmailId.setText(profileDataArraylist.get(i).getEmail());
                             binding.userNameTV.setText(profileDataArraylist.get(i).getName());
                        }
                    }
                });
     //   viewImage();
        return binding.getRoot();
    }

    private void viewImage() {

    }
}
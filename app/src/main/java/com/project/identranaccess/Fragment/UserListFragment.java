package com.project.identranaccess.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.identranaccess.Adapter.UserListDisplayDataAdapter;
import com.project.identranaccess.Activity.CreateUserListActivity;
import com.project.identranaccess.database.MyLocalDatabase;
import com.project.identranaccess.databinding.FragmentUserListBinding;
import com.project.identranaccess.model.UserListModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserListFragment extends Fragment {

    TextView create_userBtn;
    FragmentUserListBinding binding;
    UserListDisplayDataAdapter userListDisplayDataAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
  Context context;
  ArrayList<UserListModel> userListModels;
    MyLocalDatabase dbHelper;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserListFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static UserListFragment newInstance(String param1, String param2) {
        UserListFragment fragment = new UserListFragment();
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
        // Inflate the layout for this fragment

        binding = FragmentUserListBinding.inflate(inflater, container, false);
        context = getContext();
        dbHelper=new MyLocalDatabase(context);
   //     addDatainList(userListModels);


        binding.createUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), CreateUserListActivity.class);
                startActivity(i);
            }
        });

       /* binding.profileLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), CreateUserListActivity.class);
                startActivity(i);
            }
        });*/

        binding.backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        return binding.getRoot();


    }

    private void addDatainList(ArrayList<UserListModel> userListModels) {
       // userListModels = dbHelper.UserModelData();
        userListDisplayDataAdapter = new UserListDisplayDataAdapter(userListModels,context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        // setting our adapter to recycler view.
        binding.recyclerView.setAdapter(userListDisplayDataAdapter);
    }


    @Override
    public void onStart() {
        super.onStart();
      //  userListModels.notify();
        //= dbHelper.UserModelData();
    }

    @Override
    public void onResume() {
        super.onResume();
       // addDatainList(userListModels);
      //  getFragmentManager().beginTransaction().detach(this).attach(this).commit();
// userListModels = dbHelper.UserModelData();
    }
}